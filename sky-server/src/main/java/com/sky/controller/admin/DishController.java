package com.sky.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

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
     * 菜品分页查询 请求参数DishPageQueryDTO
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询参数为:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     * 根据id 批量删除菜品
     * 
     */
    @DeleteMapping()
    @ApiOperation("批量删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids) {
        log.info("批量删除菜品:{}", ids);
        dishService.deleteDishBatch(ids);
        return Result.success();
    }

    /*
     * 根据Id查询菜品
     */
    @GetMapping("/{id}")
    @ApiOperation("根据Id查询菜品")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("根据Id查询菜品:{}", id);
        // F
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /*
     * 修改菜品
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO) { // 和新增菜品差不多
        log.info("修改菜品:{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /*
     * 启用禁用分类
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用菜品分类")
    public Result<String> statusSetting(@PathVariable Integer status, Long id) {
        dishService.statusSetting(status, id);
        return Result.success();
    }

}
