package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    /*
     * 新增菜品+flavor
     */
    public void insertDishAndFlavor(DishDTO dishDTO);

    /*
     * 分页查询
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 根据id 批量删除菜品
     * 
     */
    public void deleteDishBatch(List<Long> ids);
}
