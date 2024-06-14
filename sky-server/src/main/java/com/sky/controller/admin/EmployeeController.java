package com.sky.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {
    // autowired依赖注入
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        // 拦截器
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder().id(employee.getId())
                .userName(employee.getUsername()).name(employee.getName()).token(token).build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工数据
     */
    @PostMapping
    public Result insertEmployee(@RequestBody EmployeeDTO employeeDTO) { // 注解@RequestBody用于接收前端传递给后端的、JSON对象的字符串
        log.info("新增员工1:{}", employeeDTO);
        employeeService.insertEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page") // 上面已经有大路径了@RequestMapping("/admin/employee")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {// EmployeePageQueryDTO封装的是name page
        // 和pageSize
        log.info("员工分页查询参数为:{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用 修改员工账号状态
     */
    @PostMapping("/status/{status}")
    public Result statusSetting(@PathVariable Integer status, Long id) { // @PathVariable 是 Spring MVC 中用于将 URL
                                                                         // 路径中的变量绑定到控制器方法参数的注解。这里将 URL 路径中的 status
                                                                         // 部分的值绑定到方法参数 status 上
        log.info("启用禁用员工账号:{},{}", status, id);
        employeeService.statusSetting(status, id);
        return Result.success();
    }

    // 修改信息功能 先查 再编辑 两个功能两个接口
    /**
     * 根据id查员工信息
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employeeInfo = employeeService.getById(id);
        return Result.success(employeeInfo);
    }

    /**
     * 编辑员工信息
     */
    @PutMapping
    public Result updateEmployeeInfo(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息:{}", employeeDTO);
        employeeService.updateEmployeeInfo(employeeDTO);
        return Result.success();
    }
}