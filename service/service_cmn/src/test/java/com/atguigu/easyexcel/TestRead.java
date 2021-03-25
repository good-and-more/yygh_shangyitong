package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;

public class TestRead {
    public static void main(String[] args) {
//        获取要读取的文件的路径
        String fileName = "F:\\java_practise\\yygh_shangyitong\\excel\\01.xlsx";
        EasyExcel.read(fileName,UserData.class, new ExcelListener()).sheet().doRead();
    }

}
