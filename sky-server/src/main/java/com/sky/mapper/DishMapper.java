package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * 
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    // 菜品表插入1条数据
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询方法 主要查dish和category表
     *
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 语句： select d.*,c.name from dish d left outer join category c on d.category_id
     * = c.id
     * 查出来的结构如下：
     * # id name category_id price image description status create_time update_time
     * create_user update_user name
     * 这里面两个name，一个是dish表的 老坛酸菜鱼；另一个category表的 水煮鱼
     * 
     * 方法：给字段起别名
     * select d.*,c.name as categoryName from dish d left outer join category c on
     * d.category_id = c.id
     */

}
