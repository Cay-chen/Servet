package com.xiaoweick;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

public class MailUtil {
	protected final Logger logger = Logger.getLogger(getClass());  
	  
    public boolean send(Mail mail) {  
        // ����email  
        Email email = new SimpleEmail();  
        try {  
            // ������SMTP���ͷ����������֣�163�����£�"smtp.163.com"  
            email.setHostName(mail.getHost());  
            // �ַ����뼯������  
            email.setCharset(Mail.ENCODEING);  
            // �ռ��˵�����  
            email.addTo(mail.getReceiver());  
            // �����˵�����  
            email.setFrom(mail.getSender(), mail.getName());  
            // �����Ҫ��֤��Ϣ�Ļ���������֤���û���-���롣�ֱ�Ϊ���������ʼ��������ϵ�ע�����ƺ�����  
            email.setAuthentication(mail.getUsername(), mail.getPassword());  
            // Ҫ���͵��ʼ�����  
            email.setSubject(mail.getSubject());  
            // Ҫ���͵���Ϣ������ʹ����HtmlEmail���������ʼ�������ʹ��HTML��ǩ  
            email.setMsg(mail.getMessage());  
           email.setSmtpPort(465);
           email.setSSLOnConnect(true);
            
            // ����  
            email.send();  
            if (logger.isDebugEnabled()) {  
                logger.debug(mail.getSender() + " �����ʼ��� " + mail.getReceiver());  
            }  
            return true;  
        } catch (EmailException e) {  
            e.printStackTrace();  
            logger.info(mail.getSender() + " �����ʼ��� " + mail.getReceiver()  
                    + " ʧ��");  
            return false;  
        }  
    }  
   /* public static void main(String[] args) {  
        Mail mail = new Mail();  
        mail.setHost("smtp.mxhichina.com"); // �����ʼ�������,�������163��,�Լ����ҿ���ص�  
        mail.setSender("sign_up@xiaoweick.com");  //����������
        mail.setReceiver("276495166@qq.com"); // ������  
        mail.setUsername("sign_up@xiaoweick.com"); // ��¼�˺�,һ�㶼�Ǻ�������һ����  
        mail.setPassword("Idsbg1230"); // ����������ĵ�¼����  
        mail.setSubject("С΢����ע���˺���֤��");  
        mail.setMessage("��ע���С΢�����˺�Ϊ��276495166@qq.com,��֤��Ϊ��8888 ��֤������Ӻ�ʧЧ��");  
        new MailUtil().send(mail);  
    }  */
}
