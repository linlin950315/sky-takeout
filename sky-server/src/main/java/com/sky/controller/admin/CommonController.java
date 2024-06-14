package com.sky.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

//通用接口
@RestController
@RequestMapping("/admin/common")
@Api(tags = "comment interface")
public class CommonController {

    // /**
    // * 上传文件
    // *
    // * @param file
    // * @return
    // */
    // @ApiOperation("upload file")
    // @PostMapping("/upload")
    // public Result<String> upload(MultipartFile file) { // 接口表中 前端提交的参数名就叫file
    // 这里不能改
    // log.info("文件上传:{}", file);
    // // 传入阿里云

    // return null;
    // }
}
