package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
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

    /*
     * 根据id 批量删除菜品
     */
    // 判断是否能删除 启售中不能删除。先遍历数组 取id 查status
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    // 删除菜品数据 在for循环内 被优化了
    // @Delete("delete from dish where id = #{id}")
    // void deleteById(Long id);

    // 根据菜品id集合批量删除菜品
    void deleteByIds(List<Long> ids);

    // 修改Dish表基本信息
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 启用禁用 修改分类
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

}
// sql批量删除语句：delete from dish where id in (?,?,?) delete后面没有型号
