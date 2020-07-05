package com.transaction;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


import org.junit.Test;

import com.util.JDBCUtils;

public class TransactionTest {

	@Test
	public void testTransactionSelect() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		conn.setAutoCommit(false);
		//设置隔离级别
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		String sql="select user,password,balance from user_table where user =?";
		User user = getInstance(conn,User.class,sql,"CC");
		System.out.println(user);
	}
	
	@Test
	public void testTransactionUpdate() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		conn.setAutoCommit(false);
		String sql="update user_table set balance =? where user = ?";
		update (conn,sql,5000,"CC");
	}
	
	
	//通用的查询操作 用于返回一条记录  考虑上事务
	public <T> T getInstance(Connection conn,Class<T> clazz, String sql, Object... args) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				T t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					// String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				return t;
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
	}

	// 考虑事务操作.................................................................
	@Test
	public void testUpdateWithTx() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();

			conn.setAutoCommit(false);
			String sql1 = "update user_table set balance =balance -100 where user =?";
			String sql2 = "update user_table set balance=balance +100 where user =?";
			update(conn, sql1, "AA");
			update(conn, sql2, "BB");
			System.out.println("操作成功");

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {

				e.printStackTrace();
			}
			JDBCUtils.closeResource(conn, null);
		}
	}

	public int update(Connection conn, String sql, Object... args) {
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			// 填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps.executeUpdate();
			/**
			 * ps.execute() 如果执行的是查询操作，有返回结果，则返回的是true; 如果执行的是增删改查，没有返回结果，返回的是false；
			 */
		} catch (Exception e) {

			e.printStackTrace();
		}
		JDBCUtils.closeResource(null, ps);
		return 0;
	}

	@Test // 未考虑事务的操作****************************************************************************
	public void testUpdate() {
		String sql1 = "update user_table set balance =balance -100 where user =?";
		String sql2 = "update user_table set balance=balance +100 where user =?";
		update(sql1, "AA");
		update(sql2, "BB");
		System.out.println("操作成功");
	}

	// 通用的增删改操作
	public int update(String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			// 填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps.executeUpdate();
			/**
			 * ps.execute() 如果执行的是查询操作，有返回结果，则返回的是true; 如果执行的是增删改查，没有返回结果，返回的是false；
			 */
		} catch (Exception e) {

			e.printStackTrace();
		}
		JDBCUtils.closeResource(conn, ps);
		return 0;
	}
}
