package com.bbkj;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@EnableTransactionManagement
public class App {
    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(App.class,args);
        DruidDataSource dataSource= (DruidDataSource) context.getBean(DataSource.class);
//        dataSource.init();//初始化DruidDataSource
//        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
//        System.out.println(context.getBean(DataSourceTransactionManager.class));
    }
}
