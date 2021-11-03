package com.ljw.controller;

import com.ljw.bean.Department;
import com.ljw.bean.Msg;
import com.ljw.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ClassName: DepartmentController
 * Description:
 * date: 2021/8/2 22:49
 *
 * @author: 19228
 * @version:
 * @since JDK 1.8
 */

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 返回所有的部门信息
     */

    @ResponseBody
    @RequestMapping("/depts")
    public Msg getDepts(){
        //查出的所有部门信息
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }

}
