package com.ljw.test;

import java.util.UUID;
import com.ljw.bean.Employee;
import com.ljw.dao.DepartmentMapper;
import com.ljw.dao.EmployeeMapper;
import com.ljw.service.EmployeeService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName: Mapper
 * Description:
 * date: 2021/7/18 15:40
 *
 * @author: 19228
 * @version:
 * @since JDK 1.8
 */

public class Mapper {


    DepartmentMapper departmentMapper;


    EmployeeMapper employeeMapper;


    SqlSession sqlSession;

    @Test
    public void test() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        sqlSession = ioc.getBean(SqlSession.class);

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for(int i = 0;i<1000;i++){
            String uid = UUID.randomUUID().toString().substring(0,5)+i;
            mapper.insertSelective(new Employee(null,uid, "M", uid+"@atguigu.com", (int)(Math.random()*10+1)));
        }
        System.out.println("批量完成");

    }

}
