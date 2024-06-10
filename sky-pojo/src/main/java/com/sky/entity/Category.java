package com.sky.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 类型: 1菜品分类 2套餐分类
    private Integer type;

    // 分类名称
    private String name;

    // 顺序
    private Integer sort;

    // 专属属性
    // 分类状态 0标识禁用 1表示启用
    private Integer status;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 创建人
    private Long createUser;

    // 修改人
    private Long updateUser;
}
