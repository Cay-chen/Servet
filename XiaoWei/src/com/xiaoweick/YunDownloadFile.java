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
	// Ҫ���ص��ļ���ŵ�·��

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
		String username = request.getParameter("username"); // �û�����
		String imagename = request.getParameter("imagename");// ͼƬ����
		String fullfilename = null;
		String checkMsg = request.getParameter("check");

		// �����ļ�������·����

		// String fullfilename=aaa;
		if (checkMsg.equals("1")) {
			fullfilename = downloadOriginalFiledir + username + "/"
					+ imagename;
		} else {
			fullfilename = downloadThumbnailFiledir + username + "/"
					+ imagename;

		}

		// ��ȡ��ʽ
		// response.setContentType("application/zip");
		response.setContentType("image/jpeg");
		// ��ȡͼƬ����·��
		//String path = this.getServletContext().getRealPath("/");
		File file = new File(fullfilename);
		// �����ļ�������
		FileInputStream is = new FileInputStream(file);
		// ��Ӧ�����
		ServletOutputStream out = response.getOutputStream();
		// ����������
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		System.out.println("��ʾͼƬ��"+file.getName());

		is.close();
		out.flush();
		out.close();

	}

	public void downlodeImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); // �û�����
		String imagename = request.getParameter("imagename");// ͼƬ����
		String fullfilename = null;
		String checkMsg = request.getParameter("check");

		// �����ļ�������·����

		// String fullfilename=aaa;
		if (checkMsg.equals("1")) {
			fullfilename = downloadOriginalFiledir + username + "/"
					+ imagename;
		} else {
			fullfilename = downloadThumbnailFiledir + username + "/"
					+ imagename;

		}

		// ��ȡͼƬ����·��
		//String path = this.getServletContext().getRealPath("/");
		File file = new File(fullfilename);
		// ����ͷ��Ϣ,���ݴ���ķ�ʽ,attachment�Ը�������ʽ��,���ǽ�������,�����������ļ�������
		response.setHeader("Content-Disposition",
				"attachment;filename=" + file.getName());
		response.setHeader("Content-Length",
				file.length()+"");
		// �����ļ�������
		FileInputStream is = new FileInputStream(file);
		// ��Ӧ�����
		ServletOutputStream out = response.getOutputStream();
		// ����������
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		System.out.println("�����ļ���"+file.getName());
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
