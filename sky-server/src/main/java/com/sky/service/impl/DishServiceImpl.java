package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;

@Service

public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    /*
     * 新增菜品+flavor
     */
    @Transactional // 事务注解 里面的两个方法 原子性，要么全成功要么全失败
    public void insertDishAndFlavor(DishDTO dishDTO) {
        // 属性拷贝 DTO给dish赋值
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // 菜品表插入1条数据 用Dish对象就可以 flavor后面再处理
        dishMapper.insert(dish); // 此时每道菜都有数据库自增的dishId
        // 获取insert语句生成的主键值
        Long dishId = dish.getId(); // 获取的是DishMapper.xml中 useGeneratedKeys="true" keyProperty="id"生成的主键值

        // 口味表插入多条数据
        List<DishFlavor> flavors = dishDTO.getFlavors(); // DishFlavor也要单独创建mapper类,并注入这个service
        if (flavors != null && flavors.size() > 0) {
            // 遍历集合
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            // 批量插入口味表数据
            dishFlavorMapper.insertBatch(flavors);

        }

    }

}
