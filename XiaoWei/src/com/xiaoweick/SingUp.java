package com.xiaoweick;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.xiaoweick.util.DBHelper;

public class SingUp extends HttpServlet {
	private String deleteMySQL = "delete from users_code where time < (CURRENT_TIMESTAMP() + INTERVAL -5 MINUTE)";
	private String resCoad;
	private String resMsg;

	/**
	 * Constructor of the object.
	 */
	public SingUp() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String nikeName = request.getParameter("nikeName");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String yzCode =request.getParameter("yCode");
		if(nikeName!=null&&mail!=null&&password!=null&&yzCode!=null){
		System.out.println("name"+nikeName+"mail"+mail+"pass"+password+"ycode"+yzCode);
		int yCode = Integer.parseInt(yzCode);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet mresult = null;
		String selectSQLCode = "select code from users_code WHERE mail='"
				+ mail + "'";// 查询验证码信息
		String selectYanZ = "select mail from users WHERE mail ='" + mail + "'";
		connection = DBHelper.getConnection();
		try {
			statement = connection.prepareStatement(selectYanZ);
			mresult = statement.executeQuery();
			if (mresult.next()) {
				resCoad = "30004";
				resMsg="账号已注册成功" ;// 账号已注册成功
			} else {
				statement = connection.prepareStatement(deleteMySQL);
				statement.executeUpdate();
				statement = connection.prepareStatement(selectSQLCode);
				mresult = statement.executeQuery();
				if (mresult.next()) {
					String sCode = mresult.getString("code");
					if (sCode != null) {
						int intCode = Integer.parseInt(sCode);
							if (intCode == yCode) {
							String insertSql = "insert into users (mail,nikename,sex,head_url,city,phone_num,password,registration_time) values('"
									+ mail
									+ "','"
									+ nikeName
									+ "',"
									+ 3
									+ ",'"
									+ null
									+ "','"
									+ null
									+ "',"
									+ null
									+ ",'"
									+ password + "',now())";
							statement = connection.prepareStatement(insertSql);
							statement.executeUpdate();

							resCoad = "30001";// 注册成功
							resMsg = "注册成功";
						} else {
							resCoad = "30002";// 验证码错误
							resMsg = "验证码错误";
						}

					}

				} else {
					resCoad = "30003";// 验证码过期
					resMsg = "验证码过去";

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (mresult != null) {
				try {
					mresult.close();
					mresult = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}else{
			resCoad = "3005";// 信息为空
			resMsg = "信息为空";
	
		}
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PrintWriter out = response.getWriter();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("resCode", resCoad);  
		map.put("resMsg", resMsg);  
		JSONObject json = JSONObject.fromObject(map);
		String sendMsg=json.toString();			
		System.out.println("注册账号为："+mail+"||状态为："+resCoad+"||时间："+sm.format(new Date()));
		out.print(sendMsg);
		out.flush();
		out.close();

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
