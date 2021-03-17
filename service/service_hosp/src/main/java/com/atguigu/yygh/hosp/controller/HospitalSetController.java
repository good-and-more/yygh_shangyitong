package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@Api(tags = "医院信息管理 HospitalSetController")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //1.查询医院设置表中所有的信息
    @ApiOperation("查询所有医院信息")
    @GetMapping("findAll")
    public List<HospitalSet> findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    //2.逻辑删除医院信息
    @ApiOperation("根据id逻辑删除医院")
    @GetMapping("delete/{id}")
    public boolean removeHospitalSetById(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        return flag;
    }


}
