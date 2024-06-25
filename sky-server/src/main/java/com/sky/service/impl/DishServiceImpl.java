package com.sky.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

@Service

public class DishServiceImpl implements DishService {
    @Autowired // 这里的每条都注入，否则会有注入不到的mapper
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

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

    /*
     * 分页查询
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(),
                dishPageQueryDTO.getPageSize()); // 调页码 每页记录数
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO); // 返回值用DishVO，categoryName
        // 封装total records
        return new PageResult(page.getTotal(), page.getResult()); // 获得total，records

    }

    /*
     * 根据id 批量删除菜品
     * 业务规则。一次删1个或多个菜；起售中，被套餐关联的不能删除；删除后关联的口味也要被删除
     */
    // @Transactional // 事务注解 里面的两个方法 原子性，要么全成功要么全失败
    public void deleteDishBatch(List<Long> ids) {
        // 先判断是否能删除 启售中不能删除。先遍历数组 取id 查status
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) { // == 1 也行 但建议使用常量
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 再判断 若被套餐关联 也不能删除
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        /*
         * 判断是否能删除 可删除 即删除菜品数据ok
         * for (Long id : ids) {
         * dishMapper.deleteById(id);
         * 删除菜关联的口味数据ok
         * dishFlavorMapper.deleteByDishId(id);
         * }
         */

        // 优化上面的for循环 ids批量删除
        // 根据菜品id集合批量删除菜品
        dishMapper.deleteByIds(ids);

        // 根据菜品id集合批量 删除dishFlavorMapper内的口味数据
        dishFlavorMapper.deleteByDishIds(ids);
    }
}

/**
 * sql批量删除语句： delete from dish where id in (?,?,?)
 * 
 */