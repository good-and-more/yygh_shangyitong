package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
@Api(tags = "医院管理 HospitalController")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    //条件查询带分页的医院列表方法
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        //pageModel里面有什么
        //每页数据集合
        List<Hospital> content = pageModel.getContent();
        //总记录数
        long totalElements = pageModel.getTotalElements();

        return Result.ok(pageModel);
    }
}
