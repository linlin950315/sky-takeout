package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

public interface CategoryService {
    /**
     * 新增分类
     *
     */
    void insertCategory(CategoryDTO categoryDTO);

    /**
     * 菜品分类分页查询
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 启用禁用 修改分类
     */
    void statusSetting(Integer status, Long id);

    /**
     * 根据 类型type字段 查询分类查菜品信息
     */
    Category getByType(int type);
}
