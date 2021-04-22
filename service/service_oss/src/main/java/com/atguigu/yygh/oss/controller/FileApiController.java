package com.atguigu.yygh.oss.controller;

import com.atguigu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile multipartFile) {

    }
}
