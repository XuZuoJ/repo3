package com.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

public class DBCPTest {
	/**
	 * 测试DBCP连接技术
	 * @throws Exception 
	 */
	@Test//方式一
	public void testGetConnection() throws Exception {
		//创建了一个dbcp连接池
		BasicDataSource source =new BasicDataSource();
		//设置基本信息
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl("jdbc:mysql://localhost:3306/test");
		source.setUsername("root");
		source.setPassword("xz2356");
		
		//还可以设置数据库连接池管理的相关属性
		source.setInitialSize(10);
		source.setMaxActive(10);
		//..
		
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
	
	
	//方式二  使用配置文件的方式
	@Test
	public void testGetConnection2() throws Exception {
		Properties pros = new Properties();
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
		pros.load(is);
		DataSource source = BasicDataSourceFactory.createDataSource(pros);
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
}
