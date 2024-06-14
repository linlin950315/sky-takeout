package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sky.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper {
    // 口味表批量插入多条数据
    void insertBatch(List<DishFlavor> flavors);

}
