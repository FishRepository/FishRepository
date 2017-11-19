package com.yy.util;

import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	@SuppressWarnings("static-access")
	
	public static void sendMessage(Map<String, Object> parm) throws MessagingException{
		String smtpHost  = "smtp.163.com";
		String from = "13260509294@163.com";
		String fromUserPassword = "huqi7758258";
		String title = "YOYO科技";
		String messageText = "您帐号为："+parm.get("User")+"的密码为："+parm.get("Psw")+",请牢记您的登录密码！";
		String messageType = "text/html;charset=utf-8";
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		Session mailSession = Session.getInstance(props,new MyAuthenticator(from,fromUserPassword));
		InternetAddress fromAddress = new InternetAddress(from);  
        InternetAddress toAddress = new InternetAddress((String) parm.get("To"));  
        MimeMessage message = new MimeMessage(mailSession);  
        message.setFrom(fromAddress);  
        message.addRecipient(RecipientType.TO, toAddress);  
        message.setSentDate(Calendar.getInstance().getTime());  
        message.setSubject(title);
        message.setContent(messageText, messageType); 
        Transport transport = mailSession.getTransport("smtp");  
        transport.connect(smtpHost,"chaofeng19861126", fromUserPassword);  
        transport.send(message, message.getRecipients(RecipientType.TO));
	}
}
class MyAuthenticator extends Authenticator{  
    String userName="";  
    String password="";  
    public MyAuthenticator(){  
          
    }  
    public MyAuthenticator(String userName,String password){  
        this.userName=userName;  
        this.password=password;  
    }  
     protected PasswordAuthentication getPasswordAuthentication(){     
        return new PasswordAuthentication(userName, password);     
     }   
} 