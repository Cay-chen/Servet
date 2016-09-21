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



public class Login extends HttpServlet {
	private String loginResuet;
	private String resMsg;
	/**
	 * Constructor of the object.
	 */
	public Login() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		System.out.println("222222");

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
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Connection connection1 = null;
		PreparedStatement statement = null;
		ResultSet mresult1 = null;
		String psd = request.getParameter("password");
		String usn = request.getParameter("username");
		if (usn != null) {
			String selectPssword = "select password from users WHERE mail="
					+ "'" + usn + "'";
			connection1 = DBHelper.getConnection();
			try {
				statement = connection1.prepareStatement(selectPssword);
				mresult1 = statement.executeQuery();

				if (mresult1.next()) {
					String passwordA = mresult1.getString("password");
					if (passwordA.equals(psd)) {
						loginResuet = "20001"; // 20001验证正确登录成功
						resMsg = "登录成功";
					} else {
						loginResuet = "20002";// 20002密码错误
						resMsg = "密码错误";

					}

				} else {
					loginResuet = "20003";// 20003无此账号
					resMsg = "无此账号";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			loginResuet="20004";//账号为空
			resMsg = "账号为空";

		}
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PrintWriter out = response.getWriter();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("resCode", loginResuet);  
		map.put("resMsg", resMsg);  
		JSONObject json = JSONObject.fromObject(map);
		String sendMsg=json.toString();	
		System.out.println("登录账号为："+usn+"||状态为："+loginResuet+"||时间："+sm.format(new Date()));
		out.println(sendMsg);

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
