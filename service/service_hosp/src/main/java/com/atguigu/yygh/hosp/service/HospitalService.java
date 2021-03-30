package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {

//    上传医院接口
    void save(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);
}
