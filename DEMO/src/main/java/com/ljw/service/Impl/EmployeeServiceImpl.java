package com.ljw.service.Impl;

import com.ljw.bean.Employee;
import com.ljw.bean.EmployeeExample;
import com.ljw.dao.EmployeeMapper;
import com.ljw.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: EmployeeServiceImpl
 * Description:
 * date: 2021/7/21 19:41
 *
 * @author: 19228
 * @version:
 * @since JDK 1.8
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getEmployeesList(){
        return employeeMapper.selectByExampleWithDept(null);
    }

    public void saveEmp(Employee employee) { employeeMapper.insertSelective(employee); }

    public boolean checkUser(String empName){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        return employeeMapper.countByExample(example) == 0;
    }

    public Employee getSingleEmp(Integer id){
        return employeeMapper.selectByPrimaryKeyWithDept(id);
    }

    public void updateByPrimaryKeySelective(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public void delteSingleEmp(Integer id){
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
}
