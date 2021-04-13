package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@Api(tags = "医院信息管理 HospitalSetController")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //1.查询医院设置表中所有的信息
    @ApiOperation("查询所有医院信息")
    @GetMapping("findAll")
    @ApiIgnore
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //2.逻辑删除医院信息
    @GetMapping("delete/{id}")
    public Result removeHospitalSetById(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        return flag ? Result.ok(flag) : Result.fail(flag);
    }

    @GetMapping("getHospSet/{id}")
    public Result getHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     *
     * @param hospitalSet
     * @return
     */
    @ApiOperation("新增医院信息")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        hospitalSet.setStatus(1);
        //签名密钥
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + new Random().nextInt(1000)));
        boolean result = hospitalSetService.save(hospitalSet);
        return result ? Result.ok(result) : Result.fail(result);
    }

//    批量删除
    @ApiOperation("批量删除医院信息")
    @PostMapping("batchDel")
    public Result batchDelHospitalSet(@RequestBody List<Long> ids) {
        boolean result = hospitalSetService.removeByIds(ids);
        return result ? Result.ok(result) : Result.fail(result);
    }

//    根据条件分页查询
    @ApiOperation("根据条件分页查询医院")
    @PostMapping("selectPage/{currentPage}/{pageSize}")
    public Result selectPageHospitalSet(@PathVariable Integer currentPage,
                                        @PathVariable Integer pageSize,
                                        @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if(!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        int count = hospitalSetService.count(wrapper);
        Page<HospitalSet> page = new Page<>(currentPage, pageSize, count);
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        return Result.ok(pageHospitalSet);
    }


    @ApiOperation("更新医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean result = hospitalSetService.updateById(hospitalSet);
        return result ? Result.ok(result) : Result.fail(result);
    }

    @ApiOperation("医院设置锁定和解锁接口")
    @GetMapping("lock/{id}/{status}")
    public Result lock(@PathVariable Long id,@PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    @ApiOperation("发送签名密钥SignKey")
    @PostMapping("sendKey")
    public Result sendSignKey(Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //todo 发送短信
        String[] strings = {signKey,hoscode};
        return Result.ok(strings);
    }

}
