package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealDishMapper {
    // TODO
    /*
     * 根据菜品Id查套餐Id
     */
    // select setmeal id from setmeal dish where dish id in(1,2,3,4,5)
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
