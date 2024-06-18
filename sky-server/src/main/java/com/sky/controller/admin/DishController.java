package com.sky.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "dish")
@Slf4j
public class DishController {
    // autowired 依赖注入
    @Autowired
    private DishService dishService;

    /*
     * 新增菜品
     */
    @PostMapping()
    @ApiOperation("新增菜品")

    public Result insertDish(@RequestBody DishDTO dishDTO) { // Result的返回值
        log.info("新增菜品:{}", dishDTO);
        dishService.insertDishAndFlavor(dishDTO);
        return Result.success();
    }
    /*
     * 菜品分页查询
     */

}
