package com.xiaoweick;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

public class MailUtil {
	protected final Logger logger = Logger.getLogger(getClass());  
	  
    public boolean send(Mail mail) {  
        // 发送email  
        Email email = new SimpleEmail();  
        try {  
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
            email.setHostName(mail.getHost());  
            // 字符编码集的设置  
            email.setCharset(Mail.ENCODEING);  
            // 收件人的邮箱  
            email.addTo(mail.getReceiver());  
            // 发送人的邮箱  
            email.setFrom(mail.getSender(), mail.getName());  
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication(mail.getUsername(), mail.getPassword());  
            // 要发送的邮件主题  
            email.setSubject(mail.getSubject());  
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg(mail.getMessage());  
           email.setSmtpPort(465);
           email.setSSLOnConnect(true);
            
            // 发送  
            email.send();  
            if (logger.isDebugEnabled()) {  
                logger.debug(mail.getSender() + " 发送邮件到 " + mail.getReceiver());  
            }  
            return true;  
        } catch (EmailException e) {  
            e.printStackTrace();  
            logger.info(mail.getSender() + " 发送邮件到 " + mail.getReceiver()  
                    + " 失败");  
            return false;  
        }  
    }  
   /* public static void main(String[] args) {  
        Mail mail = new Mail();  
        mail.setHost("smtp.mxhichina.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
        mail.setSender("sign_up@xiaoweick.com");  //发件人邮箱
        mail.setReceiver("276495166@qq.com"); // 接收人  
        mail.setUsername("sign_up@xiaoweick.com"); // 登录账号,一般都是和邮箱名一样吧  
        mail.setPassword("Idsbg1230"); // 发件人邮箱的登录密码  
        mail.setSubject("小微创客注册账号验证码");  
        mail.setMessage("你注册的小微创客账号为：276495166@qq.com,验证码为：8888 验证码五分钟后失效！");  
        new MailUtil().send(mail);  
    }  */
}
