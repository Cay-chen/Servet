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
import com.xiaoweick.util.SendMail;

public class Ycode extends HttpServlet {
	private String sendMsg;
private String sendCode;
	/**
	 * Constructor of the object.
	 */
	public Ycode() {
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
		out.println(", using the GET ");
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
		String mail = request.getParameter("mail");
		response.setContentType("text/html; charset=utf-8");
		if(mail!=null){
		int YCode = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;// 产生100000-999999的随机数整数验证码
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet mresult = null;
		String selectNikeName = "select mail from users WHERE mail='" + mail+ "'";// 查询账号是否已经注册
		System.out.println("mail:"+mail+" ---SQL:"+selectNikeName);
		String deleteSql ="delete from users_code where mail='"+mail+"'";		//删除已存在的信息
		connection = DBHelper.getConnection();
		try {
			statement = connection.prepareStatement(selectNikeName);
			mresult = statement.executeQuery();
			if (mresult.next()) {
				String Omail = mresult.getString("mail");
				if (Omail != null) {
					sendMsg = "账号已经被注册";// 账号已经被注册
					sendCode = "10002";

				} else{
					sendMsg = "其他错误";// 其他错误呀
					sendCode ="10003";
				}

			}else {
				statement = connection.prepareStatement(deleteSql);
				statement.executeUpdate();
				String insertCodeSQL = "insert into users_code (mail,code,time) values('"
						+ mail + "'," + YCode + ",now() )";
				statement = connection.prepareStatement(insertCodeSQL);
				int asc = statement.executeUpdate();
				SendMail sendMail = new SendMail(mail, YCode);// 注册发送邮件客服端
				sendMail.send();// 发送邮件
				sendMsg = "验证信息已发送";// 验证信息已发送
				sendCode ="10001";
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
			
		}}else{
			
			sendMsg = "账号为空";// 账号为空
			sendCode="10004";
		}
			SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PrintWriter out = response.getWriter();
			System.out.println("发送验证码账号为："+mail+"||状态为："+sendMsg+"||时间："+sm.format(new Date()));
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("resCode", sendCode);  
			map.put("resMsg", sendMsg);  
			JSONObject json = JSONObject.fromObject(map);
			String sendMsg=json.toString();			
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
