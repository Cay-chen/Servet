package com.xiaoweick;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile extends HttpServlet {
	// 字符编码

	private final String ENCODING = "GB2312";

	// 内容类型

	private final String CONTENT_TYPE = "textml;charset=gb2312";

	// 要下载的文件存放的路径

	private String downloadOriginalFiledir = "E:\\Photo\\original\\";
	private String downloadThumbnailFiledir = "E:\\Photo\\thumbnail\\";

	/**
	 * Constructor of the object.
	 */
	public DownloadFile() {
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
		// 设置request对象的字符编码

		response.setContentType("image/jpeg");
		String fullfilename = null;

		// 从request中取出要下载文件的名字

		String username = request.getParameter("username"); // 用户名称
		String imagename = request.getParameter("imagename");// 图片名称
		String checkMsg = request.getParameter("check");
		if (username == null || username.trim().equals("")) {

			// 设置response对象的ContentType

			response.setContentType(CONTENT_TYPE);

			// 输出错误信息

			PrintWriter out = response.getWriter();

			out.println("<font color=red>输入的文件名无效！</font>");

			out.close();

		} else {

			// 下载文件的完整路径名

			// String fullfilename=aaa;
			if (checkMsg.equals("1")) {
				fullfilename = downloadOriginalFiledir + username + "\\"
						+ imagename;
			} else {
				fullfilename = downloadThumbnailFiledir + username + "\\"
						+ imagename;

			}

			// 根据文件的类型设置response对象的ContentType
System.out.println(fullfilename);
			String contentType = getServletContext().getMimeType(fullfilename);

			if (contentType == null)

				contentType = "application/octet-stream";

			response.setContentType(contentType);

			// 设置response的头信息
			File file = new File(fullfilename);
			response.setHeader("Content-disposition", "attachment;filename=\""
					+ imagename + "\"");
			response.setHeader("Content-Length",
					file.length()+"");

			InputStream is = null;

			OutputStream os = null;

			try {

				is = new BufferedInputStream(new FileInputStream(fullfilename));

				// 定义输出字节流

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				// 定义response的输出流

				os = new BufferedOutputStream(response.getOutputStream());

				// 定义buffer

				byte[] buffer = new byte[4 * 1024]; // 4k Buffer

				int read = 0;

				// 从文件中读入数据并写到输出字节流中

				while ((read = is.read(buffer)) != -1) {

					baos.write(buffer, 0, read);

				}

				// 将输出字节流写到response的输出流中

				os.write(baos.toByteArray());
				System.out.println("下载文件：" + fullfilename);
			} catch (IOException e) {

				System.out.println(fullfilename+"：下载失败");
			} finally {

				// 关闭输出字节流和response输出流

				os.close();

				is.close();

			}

		}

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
