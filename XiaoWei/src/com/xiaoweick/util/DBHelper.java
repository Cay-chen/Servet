package com.xiaoweick.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBHelper {
	private static final String driver ="com.mysql.jdbc.Driver";//���ݿ�����
	//�������ݿ��ַ
	private static final String mysqlUrl = "jdbc:mysql://localhost:3306/username?useUnicode=true&characterEncoding=UTF-8";
	private static final String username="root";//���ݿ��û���
	private static final String password ="0510016";//���ݿ� ����
	private static Connection conn = null;
	
	//��̬���븺���������
	static{
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			}
	public static Connection getConnection(){
		if (conn==null) {
			try {
				conn = DriverManager.getConnection(mysqlUrl, username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return conn;
		}
		
		return conn;
	}
public static void main(String[] args){
	
	Connection connection = DBHelper.getConnection();
	if(connection==null){
		System.out.println("�����������쳣");
		
		
	}else {
		System.out.println("��������������");
	}
	
	
}

}
