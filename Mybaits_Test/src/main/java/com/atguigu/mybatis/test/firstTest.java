package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName firstTest
 * @Description TODO
 * @DATE 2021/9/4 20:04
 */
public class firstTest {

    public static void main(String args[]) throws IOException{
        String resource = "mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        try{
            SqlSession sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper employeeMapperImpl = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapperImpl.getEmpById(1);
            System.out.println(employee);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
