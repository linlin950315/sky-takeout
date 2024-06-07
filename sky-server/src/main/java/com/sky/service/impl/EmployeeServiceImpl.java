package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        // 2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码比对
        // TODO 后期需要进行md5加密，然后再进行比对 DigestUtis包 容易死锁
        if (!password.equals(employee.getPassword())) {
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            // 账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * DTO转成employee实体
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // employee.setName(employeeDTO.getName()); 不用这样一个一个设
        employee.setName(employeeDTO.getName());
        // copy properties from Employee.java
        BeanUtils.copyProperties(employeeDTO, employee);

        // EmployeeDTO是前端接收 以下是另外几个生成的属性
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword("123456");
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // 动态获取登陆人信息 这个是基于JWT令牌的认证流程
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        // 上面有@Autowiredprivate EmployeeMapper employeeMapper;
        // 这里直接调用mapper的insert（）
        // 封装
        employeeMapper.insert(employee);
    }

    /**
     * 分页查询
     *
     * @param employeeQueryDTO
     * @return
     *         public static <E> Page<E> startPage(int pageNum, int pageSize) {
     *         return startPage(pageNum, pageSize, DEFAULT_COUNT);
     *         }
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),
                employeePageQueryDTO.getPageSize()); // 调页码 每页记录数
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO); // 返回的page对象
        // page对象 变成 PageResult对象
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        // 封装total records
        return new PageResult(total, records);

    }

    /**
     * 根据id查员工信息
     */
    public Employee getById(long id) {
        Employee employeeInfo = employeeMapper.getById(id);
        return employeeInfo;
    }

    /**
     * TODO 查
     * Employee employee = Employee.builder()
     * .status(status)
     * .id(id)
     * .build();
     * 启用禁用 修改员工账号状态
     */
    public void statusSetting(Integer status, Long id) {
        // mapper 动态更新 传入Employee实体对象 设置属性
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 编辑员工信息
     */
    public void updateEmployeeInfo(EmployeeDTO employeeDTO) {
    }

}
