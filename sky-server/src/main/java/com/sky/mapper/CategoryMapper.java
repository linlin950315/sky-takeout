package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;

@Mapper
public interface CategoryMapper {

    /**
     * 插入菜品分类数据
     */
    @Insert("insert into category(id,type,name,sort,status,create_time,update_time,create_user,update_user)"
            +
            "values" +
            "(#{id},#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * 菜品分类分页查询方法
     * 是动态sql，不用注解
     * sql写映射文件里去 resources-CategoryMapper.xml
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 启用禁用 修改分类
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * 根据 类型type字段 查询分类查菜品信息
     */
    @Select("select * from category where type= #{type}")
    Category getByType(int type);

    /**
     * 根据id删除分类
     */
    @Delete("delete from category where id= #{id}")
    void deleteById(Long id);

}
