package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper {
    // 口味表批量插入多条数据
    void insertBatch(List<DishFlavor> flavors);

    // 删除菜关联的口味数据 在for循环内 在service的批量删除内这个被优化了
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    // 根据菜品id集合批量删除口味数据
    void deleteByDishIds(List<Long> ids);

    // 根据id查菜品口味
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);

}
