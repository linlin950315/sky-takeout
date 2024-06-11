package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 菜品分类分页查询
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {// CategoryPageQueryDTO封装的是page pageSize
                                                                               // name type
        log.info("分类分页查询参数为:{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用 修改分类
     */
    @PostMapping("/status/{status}")
    public Result statusSetting(@PathVariable Integer status, Long id) { // @PathVariable 是 Spring MVC 中用于将 URL
                                                                         // 路径中的变量绑定到控制器方法参数的注解。这里将 URL 路径中的 status
                                                                         // 部分的值绑定到方法参数 status 上
        log.info("启用禁用分类:{},{}", status, id);
        categoryService.statusSetting(status, id);
        return Result.success();
    }

    // 修改信息功能 先查 再编辑 两个功能两个接口
    /**
     * 根据 类型type字段 查询分类查菜品信息
     */
    @GetMapping("/list/{type}")
    public Result<Category> getByType(@PathVariable("type") int type) { // Long id; int type?
        Category categoryInfo = categoryService.getByType(type);
        return Result.success(categoryInfo);
    }

}
