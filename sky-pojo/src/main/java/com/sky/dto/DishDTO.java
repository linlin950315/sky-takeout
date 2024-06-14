package com.sky.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sky.entity.DishFlavor;

import lombok.Data;

@Data
public class DishDTO implements Serializable {

    private Long id;
    // 菜品名称
    private String name;
    // 菜品分类id
    private Long categoryId;
    // 菜品价格
    private BigDecimal price;
    // 图片
    private String image;
    // 描述信息
    private String description;
    // 0 停售 1 起售
    private Integer status;
    // 口味
    private List<DishFlavor> flavors = new ArrayList<>(); // 里面有4个字段 也是一个封装的对象 用集合接收

}
