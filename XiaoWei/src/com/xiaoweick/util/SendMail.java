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
	mail.setHost("smtp.mxhichina.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的
	mail.setSender("sign_up@xiaoweick.com"); // 发件人邮箱
	mail.setReceiver(yMail); // 接收人
	mail.setUsername("sign_up@xiaoweick.com"); // 登录账号,一般都是和邮箱名一样吧
	mail.setPassword("Idsbg1230"); // 发件人邮箱的登录密码
	mail.setSubject("小微创客注册账号验证码");
	mail.setMessage("你注册的小微创客账号为：" + yMail + ",验证码为：" + yCoad
			+ " 验证码五分钟后失效！");
	new MailUtil().send(mail);
	}
}
