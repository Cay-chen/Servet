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
	// �ַ�����

	private final String ENCODING = "GB2312";

	// ��������

	private final String CONTENT_TYPE = "textml;charset=gb2312";

	// Ҫ���ص��ļ���ŵ�·��

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
		// ����request������ַ�����

		response.setContentType("image/jpeg");
		String fullfilename = null;

		// ��request��ȡ��Ҫ�����ļ�������

		String username = request.getParameter("username"); // �û�����
		String imagename = request.getParameter("imagename");// ͼƬ����
		String checkMsg = request.getParameter("check");
		if (username == null || username.trim().equals("")) {

			// ����response�����ContentType

			response.setContentType(CONTENT_TYPE);

			// ���������Ϣ

			PrintWriter out = response.getWriter();

			out.println("<font color=red>������ļ�����Ч��</font>");

			out.close();

		} else {

			// �����ļ�������·����

			// String fullfilename=aaa;
			if (checkMsg.equals("1")) {
				fullfilename = downloadOriginalFiledir + username + "\\"
						+ imagename;
			} else {
				fullfilename = downloadThumbnailFiledir + username + "\\"
						+ imagename;

			}

			// �����ļ�����������response�����ContentType
System.out.println(fullfilename);
			String contentType = getServletContext().getMimeType(fullfilename);

			if (contentType == null)

				contentType = "application/octet-stream";

			response.setContentType(contentType);

			// ����response��ͷ��Ϣ
			File file = new File(fullfilename);
			response.setHeader("Content-disposition", "attachment;filename=\""
					+ imagename + "\"");
			response.setHeader("Content-Length",
					file.length()+"");

			InputStream is = null;

			OutputStream os = null;

			try {

				is = new BufferedInputStream(new FileInputStream(fullfilename));

				// ��������ֽ���

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				// ����response�������

				os = new BufferedOutputStream(response.getOutputStream());

				// ����buffer

				byte[] buffer = new byte[4 * 1024]; // 4k Buffer

				int read = 0;

				// ���ļ��ж������ݲ�д������ֽ�����

				while ((read = is.read(buffer)) != -1) {

					baos.write(buffer, 0, read);

				}

				// ������ֽ���д��response���������

				os.write(baos.toByteArray());
				System.out.println("�����ļ���" + fullfilename);
			} catch (IOException e) {

				System.out.println(fullfilename+"������ʧ��");
			} finally {

				// �ر�����ֽ�����response�����

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
