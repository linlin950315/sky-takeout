package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
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
        category.setStatus(StatusConstant.DISABLE);
        // day3 自动填充@AutoFill(value = OperationType.UPDATE) 以下就不需要了

        // category.setCreateTime(LocalDateTime.now());
        // category.setUpdateTime(LocalDateTime.now());

        // // 动态获取登陆人信息 这个是基于JWT令牌的认证流程
        // category.setCreateUser(BaseContext.getCurrentId());
        // category.setUpdateUser(BaseContext.getCurrentId());

        // 封装
        categoryMapper.insert(category);
    }

    /**
     * 菜品分类分页查询
     * public static <E> Page<E> startPage(int pageNum, int pageSize) {
     * return startPage(pageNum, pageSize, DEFAULT_COUNT);
     * }
     */
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),
                categoryPageQueryDTO.getPageSize()); // 调页码 每页记录数
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO); // 返回的page对象
        // page对象 变成 PageResult对象
        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 启用禁用
     */
    public void statusSetting(Integer status, Long id) {

        // mapper 动态更新 传入Category实体对象 设置属性
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.update(category);
    }

    /**
     * 根据 类型type字段 查询分类查菜品信息
     */
    public Category getByType(int type) {
        Category categoryInfo = categoryMapper.getByType(type);
        return categoryInfo;
    }

    /**
     * 修改分类
     */
    public void updateCategoryInfo(CategoryDTO categoryDTO) {

        Category category = new Category();
        // copy properties from Category.java
        BeanUtils.copyProperties(categoryDTO, category);
        // 设置修改时间、修改人
        // category.setUpdateTime(LocalDateTime.now());
        // category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    /**
     * 根据id删除分类
     */
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    public void deleteById(Long id) {
        // 查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            // 当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        // 查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setmealMapper.countByCategoryId(id);
        if (count > 0) {
            // 当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        // 删除分类数据
        categoryMapper.deleteById(id);
    }
}
