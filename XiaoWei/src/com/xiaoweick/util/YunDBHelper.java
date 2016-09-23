package com.xiaoweick.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class YunDBHelper {
	private static final String driver ="com.mysql.jdbc.Driver";//数据库驱动
	//链接数据库地址
	private static final String mysqlUrl = "jdbc:mysql://localhost:3306/xiaoweiyun?useUnicode=true&characterEncoding=UTF-8";
	private static final String username="root";//数据库用户名
	private static final String password ="0510016";//数据库 密码
	private static Connection conn = null;
	
	//静态代码负责加载驱动
	static{
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
System.out.println("数据库没连接上");}
				
			}
	public  Connection getConnection(){
		if (conn==null) {
			try {
				conn = DriverManager.getConnection(mysqlUrl, username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("数据库没连接上");			}
			return conn;
		}
		
		return conn;
	}
public static void main(String[] args){
	
	Connection connection = new YunDBHelper().getConnection();
	if(connection==null){
		System.out.println("数据了链接异常");
		
		
	}else {
		System.out.println("数据了链接正常");
	}
	
	
}

}
