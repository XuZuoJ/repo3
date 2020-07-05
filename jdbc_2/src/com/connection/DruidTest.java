package com.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidTest {
	
	@Test
	public void getConnection() throws Exception {
		Properties pro = new Properties();
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
		pro.load(is);
		DataSource source = DruidDataSourceFactory.createDataSource(pro);
		Connection conn = source.getConnection();
		System.out.println(conn);
	}
}
