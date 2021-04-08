package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //判断id下是否有子节点的私有方法
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    @Override
    //第一次查存到缓存中，第二次查就直接访问缓存
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
//      往list集合中每个dict对象设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean hasChildren = hasChildren(dictId);
            dict.setHasChildren(hasChildren);
        }
        return dictList;
    }

    //导出数据字典接口
    @Override
    public void exportDictData(HttpServletResponse response) {
        try {
    //            设置下载相关信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("为什么我格式不对", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //todo 这里如果用火狐浏览器，不会识别filename=多少，下载的全是xls，不知道为什么，换回谷歌浏览器即可正常下载xlsx

    //            查询数据
            List<Dict> dicts = baseMapper.selectList(null);
    //将dict对象转变成dictEeVo对象
            List<DictEeVo> dictEeVos = new ArrayList<>();
            for (Dict dict : dicts) {
                DictEeVo dictEeVo = new DictEeVo();
                BeanUtils.copyProperties(dict,dictEeVo);
                dictEeVos.add(dictEeVo);
            }
    //            调用方法进行写入
            EasyExcel.write(response.getOutputStream(), DictEeVo.class)
                    .sheet("数据字典").doWrite(dictEeVos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导入数据字典接口
    @Override
    //表示执行该方法后把value值下的缓存全部清空，allEntries = true代表全部清空
    @CacheEvict(value = "dict", allEntries = true)
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据dictdoe和value查询字典名称
     * @param dictCode
     * @param value
     * @return
     */
    @Override
    public String getDictName(String dictCode, String value) {
        if(StringUtils.isEmpty(dictCode)) {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        } else {
            Dict codeDict = getDictByDictCode(dictCode);
            Long parentId = codeDict.getId();
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", parentId).eq("value", value));
            return finalDict.getName();
        }
    }

    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }

    /**
     * 根据dictCode获取下级节点
     * @param dictCode
     * @return
     */
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        Dict dictByDictCode = this.getDictByDictCode(dictCode);
        //根据id获取子节点，可以直接用前面写好的方法
        List<Dict> childData = this.findChildData(dictByDictCode.getId());
        return childData;
    }

}
