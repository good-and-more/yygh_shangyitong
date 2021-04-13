package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(tags = "API接口")
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //验证传来的sign和本地通过hoscode查询的是否一样
    private void validateSignKey(String sign, String hoscode) {
        String signKey = hospitalSetService.getSignKey(hoscode);
        if(!signKey.equals(sign)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }

    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode");
        if(StringUtils.isEmpty(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String)paramMap.get("logoData");
        if(!StringUtils.isEmpty(logoDataString)) {
            String logoData = logoDataString.replaceAll(" ", "+");
            paramMap.put("logoData", logoData);
        }


        String signKey = hospitalSetService.getSignKey(hoscode);
        if(!signKey.equals(sign)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        hospitalService.save(paramMap);
        return Result.ok();
    }

    //查询医院接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取传递过来的医院的编号
        String hoscode = (String) paramMap.get("hoscode");

        //校验签名是否一样
        String sign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        if(!signKey.equals(sign)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

//    上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        this.validateSignKey((String) paramMap.get("sign"),(String) paramMap.get("hoscode"));
        departmentService.save(paramMap);
        return Result.ok();
    }

    //分页查询科室
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

//        医院编号
        String hoscode = (String) paramMap.get("hoscode");
//        签名校验
        this.validateSignKey((String) paramMap.get("sign"),hoscode);

//        当前页和每页值
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    //删除科室
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

//        医院编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
//        签名校验
        this.validateSignKey((String) paramMap.get("sign"),hoscode);
        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

//    上传排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hoscode = (String) paramMap.get("hoscode");
        //签名校验
        this.validateSignKey((String) paramMap.get("sign"),hoscode);
        scheduleService.save(paramMap);
        return Result.ok();
    }

    //分页查询排班
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
//        医院编号
        String hoscode = (String) paramMap.get("hoscode");
//        签名校验
        this.validateSignKey((String) paramMap.get("sign"),hoscode);

//        当前页和每页值
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);

        Page<Schedule> pageModel = scheduleService.findPageSchedule(page, limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //删除排班
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

//        医院编号
        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
//        签名校验
        this.validateSignKey((String) paramMap.get("sign"),hoscode);
        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }
}
