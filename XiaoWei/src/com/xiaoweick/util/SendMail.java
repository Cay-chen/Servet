package com.xiaoweick.util;

import com.xiaoweick.Mail;
import com.xiaoweick.MailUtil;




public class SendMail {
	private String yMail;
	private int yCoad;

	public SendMail(String mail,int YCoad) {
		super();
		this.yMail = mail;
		this.yCoad = YCoad;
	}
	
	public void send(){
	Mail mail = new Mail();
	mail.setHost("smtp.mxhichina.com"); // �����ʼ�������,�������163��,�Լ����ҿ���ص�
	mail.setSender("sign_up@xiaoweick.com"); // ����������
	mail.setReceiver(yMail); // ������
	mail.setUsername("sign_up@xiaoweick.com"); // ��¼�˺�,һ�㶼�Ǻ�������һ����
	mail.setPassword("Idsbg1230"); // ����������ĵ�¼����
	mail.setSubject("С΢����ע���˺���֤��");
	mail.setMessage("��ע���С΢�����˺�Ϊ��" + yMail + ",��֤��Ϊ��" + yCoad
			+ " ��֤������Ӻ�ʧЧ��");
	new MailUtil().send(mail);
	}
}
