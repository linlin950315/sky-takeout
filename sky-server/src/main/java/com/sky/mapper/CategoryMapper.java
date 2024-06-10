package com.sky.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.sky.entity.Category;

@Mapper
public interface CategoryMapper {

    /**
     * 插入菜品分类数据
     */
    @Insert("insert into category(id,type,name,sort,status,create_time,update_time,create_user,update_user)"
            +
            "values" +
            "(#{id},#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);
}
