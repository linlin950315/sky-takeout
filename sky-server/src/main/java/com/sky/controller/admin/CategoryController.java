package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.CategoryDTO;
import com.sky.result.Result;
import com.sky.service.CategoryService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     */
    @PostMapping()
    public Result insertCategory(@RequestBody CategoryDTO categoryDTO) { // 注解@RequestBody用于接收前端传递给后端的、JSON对象的字符串
        log.info("新增分类:{}", categoryDTO);
        categoryService.insertCategory(categoryDTO);
        return Result.success();
    }

}
