package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    /*
     * 根据Id查询菜品
     */
    public DishVO getByIdWithFlavor(Long id);

    /*
     * 修改菜品 基本信息 口味信息
     */
    public void updateWithFlavor(DishDTO dishDTO);

    /*
     * 启用禁用分类
     */
    public void statusSetting(Integer status, Long id);
}
