package com.sky.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;

@Service
public class CatergoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新catgeory
     * DTO转成catgeory实体
     */
    public void insertCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        // copy properties from Category.java
        BeanUtils.copyProperties(categoryDTO, category);
        // categoryDTO是前端接收id type name sort
        // 以下是status属性
        category.setStatus(StatusConstant.ENABLE); // TODO 改成disable

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        // 动态获取登陆人信息 这个是基于JWT令牌的认证流程
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        // 封装
        categoryMapper.insert(category);
    }
}
