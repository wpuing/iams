package com.iams.core.service;


/**
 *  @author: Wei yz
 *  @Date: 2021/2/5 15:48
 *  @Description:  发送邮件相关实现业务类
 */
public interface MailService {

	/**
	 * @Title: sendSimpleMail
	 * @Description: 发送消息，文字
	 * @param to 接收方
	 * @param subject 邮件标题
	 * @param content 文件内容
	 * @return
	 */
	int sendSimpleMail(String to, String subject, String content);
	
	/**
	 * @Title: sendAttachmentsMail
	 * @Description: 
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePath
	 * @return
	 */
	int sendAttachmentsMail(String to, String subject, String content, String filePath);
	
}
