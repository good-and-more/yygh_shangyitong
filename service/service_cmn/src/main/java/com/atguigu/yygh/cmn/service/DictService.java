package com.atguigu.yygh.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface DictService extends IService<Dict> {

//    根据id查询子数据
    List<Dict> findChildData(Long id);

}
