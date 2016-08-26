package com.pptv.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/** 数据库连接池druid的配置 */  
@Configuration  
public class DruidConfiguration {  
  
	@Autowired
	private Environment env;
	
    @Bean  
    public ServletRegistrationBean druidServlet(){  
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");  
    }  
    @Bean   
    public FilterRegistrationBean filterRegistrationBean(){  
        FilterRegistrationBean fb = new FilterRegistrationBean();  
        fb.setFilter(new WebStatFilter());  
        fb.addUrlPatterns("/*");  
        fb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");  
        return fb;  
    }  
    
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
        try {  
        	dataSource.setFilters("stat,wall");  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    
    // 创建事务管理器
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}  
