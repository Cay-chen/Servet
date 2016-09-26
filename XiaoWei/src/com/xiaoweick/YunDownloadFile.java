package com.xiaoweick;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YunDownloadFile extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	// 要下载的文件存放的路径

	private String downloadOriginalFiledir = "E:/Photo/original/";
	private String downloadThumbnailFiledir = "E:/Photo/thumbnail/";

	public YunDownloadFile() {
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
		downlodeImage(request,response);
	//	showImage(request,response);
	}

	public void showImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); // 用户名称
		String imagename = request.getParameter("imagename");// 图片名称
		String fullfilename = null;
		String checkMsg = request.getParameter("check");

		// 下载文件的完整路径名

		// String fullfilename=aaa;
		if (checkMsg.equals("1")) {
			fullfilename = downloadOriginalFiledir + username + "/"
					+ imagename;
		} else {
			fullfilename = downloadThumbnailFiledir + username + "/"
					+ imagename;

		}

		// 读取方式
		// response.setContentType("application/zip");
		response.setContentType("image/jpeg");
		// 获取图片绝对路径
		//String path = this.getServletContext().getRealPath("/");
		File file = new File(fullfilename);
		// 创建文件输入流
		FileInputStream is = new FileInputStream(file);
		// 响应输出流
		ServletOutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		System.out.println("显示图片："+file.getName());

		is.close();
		out.flush();
		out.close();

	}

	public void downlodeImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); // 用户名称
		String imagename = request.getParameter("imagename");// 图片名称
		String fullfilename = null;
		String checkMsg = request.getParameter("check");

		// 下载文件的完整路径名

		// String fullfilename=aaa;
		if (checkMsg.equals("1")) {
			fullfilename = downloadOriginalFiledir + username + "/"
					+ imagename;
		} else {
			fullfilename = downloadThumbnailFiledir + username + "/"
					+ imagename;

		}

		// 获取图片绝对路径
		//String path = this.getServletContext().getRealPath("/");
		File file = new File(fullfilename);
		// 设置头信息,内容处理的方式,attachment以附件的形式打开,就是进行下载,并设置下载文件的命名
		response.setHeader("Content-Disposition",
				"attachment;filename=" + file.getName());
		response.setHeader("Content-Length",
				file.length()+"");
		// 创建文件输入流
		FileInputStream is = new FileInputStream(file);
		// 响应输出流
		ServletOutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		System.out.println("下载文件："+file.getName());
		is.close();
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
