package com.atguigu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<UserData> {
//    逐行读取excel内容，从第二行开始读取
    @Override
    public void invoke(UserData data, AnalysisContext context) {
        System.out.println(data);
    }
//    读取之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("打印完毕");
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息:"+headMap);
    }
}
