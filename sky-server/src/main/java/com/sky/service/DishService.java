package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /*
     * 新增菜品+flavor
     */
    public void insertDishAndFlavor(DishDTO dishDTO);
}
