package com.xiaoweick;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class YunDeleteFile extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public YunDeleteFile() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		boolean isYDelete =false;
		boolean isNdelete =false;
		String originalPath = "E:\\Photo\\original\\";
		String thumbnailPath = "E:\\Photo\\thumbnail\\";
		String resCoad=null;
		String resMsg=null;
		String path = request.getParameter("deletePath");
		String name = request.getParameter("imagename");
		String yPath = originalPath+path+"\\"+name;
		String nPath = thumbnailPath+path+"\\"+name;
		File fileY = new File(yPath);
		if(fileY.exists()){
			fileY.delete();	
			isYDelete = true;
		}
		File fileN= new File(nPath);
		if(fileN.exists()){
			fileN.delete();	
			isNdelete = true;
		}
	if(isYDelete&&isNdelete){
		resCoad = "40001";// 信息为空
		resMsg = "删除成功";		
	}else{
		resCoad = "40002";// 信息为空
		resMsg = "删除失败";		
		
	}
	
	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	PrintWriter out = response.getWriter();
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("resCode", resCoad);
	map.put("resMsg", resMsg);
	JSONObject json = JSONObject.fromObject(map);
	String sendMsg = json.toString();
	System.out.println("删除：" + yPath + "||状态为：" + resCoad + "||时间："
			+ sm.format(new Date()));
	out.print(sendMsg);
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
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
