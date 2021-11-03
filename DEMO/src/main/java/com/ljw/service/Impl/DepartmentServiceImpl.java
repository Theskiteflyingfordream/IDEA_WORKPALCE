package com.ljw.service.Impl;

import com.ljw.bean.Department;
import com.ljw.dao.DepartmentMapper;
import com.ljw.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: DepartmentServiceImpl
 * Description:
 * date: 2021/8/2 22:51
 *
 * @author: 19228
 * @version:
 * @since JDK 1.8
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepts(){
        List<Department> list = departmentMapper.selectByExample(null);
        return list;
    }

}
