package com.bizi.report.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class MailSender {

    public static void sendMail(String toMails, String title, String content, String fileName, InputStream inputStream) {
        String host = "smtp.exmail.qq.com";
        String from = "guofangbi@***.com";
        String pass = "****";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
//        props.put("mail.debug", "true"); //  调试使用

        Session session = Session.getDefaultInstance(props, new MyAuthenticator(from,pass));
        MimeMessage message = new MimeMessage(session);
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setSubject(title);
            messageHelper.setText(content);
            messageHelper.setFrom("guofangbi@***.com");
            messageHelper.setTo(InternetAddress.parse(toMails));
            messageHelper.addAttachment(fileName,new ByteArrayResource(IOUtils.toByteArray(inputStream)));
            Transport.send(message);
        } catch (Exception e) {
            log.error("邮件发送失败.",e);
        }
    }
}

class MyAuthenticator extends Authenticator {
    String user;
    String pw;
    public MyAuthenticator (String username, String password)
    {
        super();
        this.user = username;
        this.pw = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, pw);
    }
}
