package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/admin/cmn/dict")
@Api(tags = "数据字典")
public class DictController {

    @Autowired
    private DictService dictService;

    //根据数据id查询子数据列表
    @ApiOperation("根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }

//    导出数据字典接口
    @ApiOperation("导出数据字典到excel")
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDictData(response);
    }

//    导入数据字典接口
    @ApiOperation("通过excel导入数据字典")
    @PostMapping("importData")
    public Result importDict(MultipartFile file) {
        dictService.importDictData(file);
        return Result.ok();
    }

    //根据dictcode和value值查询，返回名称
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode, value);
        return dictName;
    }

    //根据value值查询，返回名称
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("", value);
        return dictName;
    }

    //根据dictCode查询下级节点
    @ApiOperation("根据dictCode查询下级节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }

    //根据parentId查询字典
    @ApiOperation("根据parentId查询字典")
    @GetMapping("findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findChildData(parentId);
        return Result.ok(list);
    }
}
