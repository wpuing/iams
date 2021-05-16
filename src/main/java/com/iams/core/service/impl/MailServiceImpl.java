package com.iams.core.service.impl;


import com.iams.core.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/5 15:47
 *  @Description:  邮箱验证实现类
 */
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${mail.fromMail.addr}")
	private String from;

	@Override
	public int sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
		try {
			javaMailSender.send(simpleMailMessage);
			System.out.println("发送正常");
			return 1;
		} catch (MailException e) {
			e.printStackTrace();
			System.out.println("发送异常");
			return 0;
		}
	}

	@Override
	public int sendAttachmentsMail(String to, String subject, String content, String filePath) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        FileSystemResource file = new FileSystemResource(new File(filePath));
	        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
	        helper.addAttachment(fileName, file);
	        javaMailSender.send(message);
	        System.out.println("带附件的邮件已经发送。");
	    } catch (MessagingException e) {
	    	System.out.println("发送带附件的邮件时发生异常！");
	    }
		return 0;
	}

}
