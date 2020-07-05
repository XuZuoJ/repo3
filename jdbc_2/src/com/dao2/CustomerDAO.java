package com.dao2;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.bean.Customer;

/**
 * 
 *此接口用于规范customer表的常用操作
 *
 */
public interface CustomerDAO {
	//将 Cust对象添加到数据库当中
	void insert(Connection conn,Customer cust);
	//针对指定的Id,删除表中的一条记录
	void deleteById(Connection conn,int id);
	//修改数据表中指定的记录
	void update(Connection conn,Customer cust);
	//根据指定的ID查询指定的customer
	Customer getCustomerById(Connection conn,int id);
	//查询表中的所有记录构成的集合
	List<Customer>   getAll(Connection conn);
	//返回数据表中数据的条目数
	Long getCount(Connection conn);
	//获取最大的生日
	Date getMaxBirth(Connection conn);
}
