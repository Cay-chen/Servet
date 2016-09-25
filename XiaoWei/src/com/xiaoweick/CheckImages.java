package com.xiaoweick;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

public class CheckImages extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checkpath = "E:\\Photo\\thumbnail\\";
	private HashMap<String, String[]> map = new HashMap<String, String[]>();

	/**
	 * Constructor of the object.
	 */
	public CheckImages() {
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
		response.setContentType("text/html; charset=utf-8");
		String username = request.getParameter("username");
		String path = checkpath + username;
		File parentDirectory = new File(path);
		ArrayList<String> file = new ArrayList<String>();// ��ŵ�ǰĿ¼�����е��ļ�
		ArrayList<String> fileTime = new ArrayList<String>();// ��ŵ�ǰĿ¼�����е��ļ� ��ʱ��
		ArrayList<String> directory = new ArrayList<String>();// ��ŵ�ǰĿ¼�����е�Ŀ¼
		ArrayList<String> directoryTime = new ArrayList<String>();
		String[] filename = null;// ��ʱ���� ��ŵ�ǰĿ¼�����е��ļ���Ŀ¼�ľ���·�����������ֵ��ַ���

		long aaaaaa = parentDirectory.lastModified();
		System.out.println(aaaaaa);
		filename = parentDirectory.list();// ��ȡԴĿ¼���ļ�����
		File[] files = parentDirectory.listFiles();// ��ȡԴĿ¼�µ��ļ�����
		for (int i = 0; i < files.length; i++) {

			// �����ж� ��Ŀ¼�ķ���directory������ �ļ��ķ���file������
			if (files[i].isDirectory()) {
				directory.add(filename[i]);
				directoryTime.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date(files[i].lastModified())));
			} else {
				file.add(filename[i]);
				fileTime.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date(files[i].lastModified())));

			}
		}
		String[] folders = directory.toArray(new String[directory.size()]); // ��arrylistת��������ô���Map
		String[] images = file.toArray(new String[file.size()]);
		String[] foldersTime = directoryTime.toArray(new String[directoryTime
				.size()]);
		String[] imagessTime = fileTime.toArray(new String[fileTime.size()]);

		map.put("folders", folders);
		map.put("images", images);
		map.put("foldersTime", foldersTime);
		map.put("imagesTime", imagessTime);
		JSONObject json = JSONObject.fromObject(map);
		String sendMsg = json.toString();
		PrintWriter out = response.getWriter();
		System.out.println("��ѯ��"+path+"�����ļ�");
		out.println(sendMsg);
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
