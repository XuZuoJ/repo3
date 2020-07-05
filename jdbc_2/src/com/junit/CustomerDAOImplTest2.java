package com.junit;


import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import org.junit.Test;

import com.bean.Customer;
import com.dao2.CustomerDAOImpl;
import com.util.JDBCUtils;

public class CustomerDAOImplTest2 {
	
	private CustomerDAOImpl dao =new CustomerDAOImpl();
	@Test
	public void testInsert() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			Customer cust = new Customer(1, "姜小白","123@126.com", new Date(44321313L));
			dao.insert(conn, cust);
			System.out.println("添加成功");
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testDeleteById() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			dao.deleteById(conn, 13);
			System.out.println("删除成功");
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testUpdateConnectionCustomer() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			Customer cust=new Customer(18,"贝多芬","beiduofen@123.com",new Date(564641333L));   
			dao.update(conn, cust);
			System.out.println("修改成功");
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetCustomerById() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			Customer cust = dao.getCustomerById(conn, 19);			
			System.out.println(cust);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetAll() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			List<Customer> list =dao.getAll(conn);
			list.forEach(System.out::println);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetCount() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			Long count = dao.getCount(conn);
			System.out.println(count);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetMaxBirth() {
		Connection conn=null;
		try {
			conn = JDBCUtils.getConnection();
			Date maxBirth = dao.getMaxBirth(conn);
			System.out.println(maxBirth);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}

}
