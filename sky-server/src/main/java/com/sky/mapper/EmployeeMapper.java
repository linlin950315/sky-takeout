
package com.sky.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;

@Mapper
public interface EmployeeMapper {

        /**
         * 根据用户名查询员工
         *
         * @param username
         * @return
         */
        @Select("select * from employee where username = #{username}")
        Employee getByUsername(String username);

        /**
         * 插入员工数据
         */
        @Insert("insert into employee(name,username,password,phone,sex,id_number,create_time,update_time,create_user,update_user)"
                        +
                        "values" +
                        "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
        void insert(Employee employee);

        /**
         * 员工分页查询方法
         * 是动态sql，不用注解
         * sql写映射文件里去 resources-EmplpyeeMapper.xml
         */
        Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

        /**
         * 根据id查员工信息
         */
        @Select("select * from employee where id= #{id}")
        Employee getById(Long id);

        /**
         * 启用禁用 修改员工账号状态
         */
        // Result startOrStop(Integer status, Long id);
        void update(Employee employee);

        /**
         * 编辑员工信息
         */

}
