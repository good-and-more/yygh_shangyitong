package com.atguigu.yygh.hosp;

import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ServiceHospApplicationTests {

    @Autowired
    HospitalSetService hospitalSetService;

    @Test
    public void testPage(){
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("hosname", "同济");
        Page<HospitalSet> page = new Page<>(1, 3);
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        System.out.println(hospitalSetPage);
    }

    //某小伙子不知道这个代码有什么问题
    public void line(int i) {
        if (i<10) {
            line(i+1);
            System.out.println(String.format("%3d",i));
        }
    }

    @Test
    public void testLine() {
        line(8);
    }
}
