package com.ljw.service;

import com.ljw.bean.Employee;
import com.ljw.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: EmployeeService
 * Description:
 * date: 2021/7/19 23:25
 *
 * @author: 19228
 * @version:
 * @since JDK 1.8
 */


public interface EmployeeService {
    List<Employee> getEmployeesList();
    void saveEmp(Employee employee);
    boolean checkUser(String empName);
    Employee getSingleEmp(Integer id);
    void updateByPrimaryKeySelective(Employee employee);
    void delteSingleEmp(Integer id);
    void deleteBatch(List<Integer> ids);
}
