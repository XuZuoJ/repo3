package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {

	public static Connection getConnection() throws Exception {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		Properties pros = new Properties();
		pros.load(is);
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");

		Class.forName(driverClass);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static void closeResource(Connection conn, Statement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// 使用C3P0数据库连接技术
	private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");// 数据库连接池只需提供一个

	public static Connection getConnection1() throws Exception {
		Connection conn = cpds.getConnection();
		return conn;
	}

	// 使用DBCP数据库连接技术
	private static DataSource source;
	static {
		try {
			Properties pros = new Properties();
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
			pros.load(is);
			source = BasicDataSourceFactory.createDataSource(pros);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	
	public static Connection getConnection2() throws Exception {
		Connection conn = source.getConnection();
		return conn;
	}
	
	//使用druid数据库连接池技术
	private static DataSource source1 =null;
	static {
		try {
			Properties pro = new Properties();
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
			pro.load(is);
			source1= DruidDataSourceFactory.createDataSource(pro);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public static Connection getConnection3() throws Exception {
		Connection conn = source1.getConnection();
		return conn;
	}
	
	
	public void closeResource1(Connection conn,Statement ps,ResultSet rs) throws Exception {
		DbUtils.close(conn);
		DbUtils.close(ps);
		DbUtils.close(rs);
	}
}
