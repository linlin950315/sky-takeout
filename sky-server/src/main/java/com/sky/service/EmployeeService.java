package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * 
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void insertEmployee(EmployeeDTO employeeDTO);

    /*
     * 分页查询
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用 修改员工账号状态
     */
    void statusSetting(Integer status, Long id);

    /**
     * 根据id查员工信息
     */
    Employee getById(long id);

    /**
     * 编辑员工信息
     */
    void updateEmployeeInfo(EmployeeDTO employeeDTO);

}
