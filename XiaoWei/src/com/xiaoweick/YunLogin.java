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

import com.xiaoweick.util.YunDBHelper;

public class YunLogin extends HttpServlet {
	
	/**
	 * Constructor of the object.
	 */
	public YunLogin() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String loginResuet = null;
		String resMsg= null;
		String resHeadUrl =null;
		String resNikeName = null;
		Connection connection1 = null;
		PreparedStatement statement = null;
		ResultSet mresult = null;
		HashMap<String, String> map = new HashMap<String, String>();
		String psd = request.getParameter("password");
		String usn = request.getParameter("username");
		if (usn != null) {
			String selectPssword = "select * from users WHERE mail="
					+ "'" + usn + "'";
			connection1 = YunDBHelper.getConnection();
			try {
				statement = connection1.prepareStatement(selectPssword);
				mresult = statement.executeQuery();

				if (mresult.next()) {
					String passwordA = mresult.getString("password");
					if (passwordA.equals(psd)) {
						loginResuet = "20001"; // 20001验证正确登录成功
						resMsg = "登录成功";
						resHeadUrl =mresult.getString("head_url");
						resNikeName = mresult.getString("nikename");
						map.put("resCode", loginResuet);  
						map.put("resMsg", resMsg);
						map.put("resHeadUrl", resHeadUrl);
						map.put("resNikeName", resNikeName);

					} else {
						loginResuet = "20002";// 20002密码错误
						resMsg = "密码错误";
						map.put("resCode", loginResuet);  
						map.put("resMsg", resMsg);
						
					}

				} else {
					loginResuet = "20003";// 20003无此账号
					resMsg = "无此账号";
					map.put("resCode", loginResuet);  
					map.put("resMsg", resMsg);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			loginResuet="20004";//账号为空
			resMsg = "账号为空";
			map.put("resCode", loginResuet);  
			map.put("resMsg", resMsg);
			
		}
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PrintWriter out = response.getWriter();
		
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
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
