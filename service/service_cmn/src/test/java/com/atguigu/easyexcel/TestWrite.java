package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;
import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {
        //设置excel文件路径和名称
        String fileName = "F:\\java_practise\\yygh_shangyitong\\excel\\01.xlsx";

        //构建一个数据list集合
        List<UserData> arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("lucy"+i);
            arrayList.add(data);
        }
        //调用方法实现写操作
        EasyExcel.write(fileName, UserData.class).sheet("用户信息").doWrite(arrayList);
    }
}
