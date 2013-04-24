package com.heki1224.spring.mail.send.service;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.heki1224.spring.mail.send.helper.MultibyteMimeMessageHelper;

/**
 * Send E-mail Service Class
 * 
 * 
 * @author heki1224
 * 
 */
@Service
public class SendMailService {

	private static final Log LOG = LogFactory.getLog(SendMailService.class);

	private static final String ENCODING = "ISO-2022-JP";

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * メール送信をする
	 * 
	 * @param fromAddress
	 *            送信元メールアドレス
	 * @param toAddress
	 *            宛先メールアドレス
	 * @param title
	 *            タイトル
	 * @param plainText
	 *            平文
	 */
	public void send(String fromAddress, String toAddress, String title, String plainText) {
		send(fromAddress, toAddress, title, plainText, null);
	}

	/**
	 * メール送信をする
	 * 
	 * @param fromAddress
	 *            送信元メールアドレス
	 * @param toAddress
	 *            宛先メールアドレス
	 * @param title
	 *            タイトル
	 * @param plainText
	 *            平文
	 * @param htmlText
	 *            HTML文
	 */
	public void send(String fromAddress, String toAddress, String title, String plainText,
			String htmlText) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MultibyteMimeMessageHelper helper = null;
			if (StringUtils.isNotBlank(htmlText)) {
				helper =
					new MultibyteMimeMessageHelper(
						message,
						MimeMessageHelper.MULTIPART_MODE_MIXED,
						ENCODING);
			} else {
				helper =
					new MultibyteMimeMessageHelper(
						message,
						MimeMessageHelper.MULTIPART_MODE_NO,
						ENCODING);
			}
			// Content-Transfer-Encodingを設定する
			helper.setHeader(new Header("Content-Transfer-Encoding", "7bit"));
			// 送信元メールアドレス
			helper.setFrom(fromAddress);
			// 宛先メールアドレス
			helper.setTo(toAddress);
			// 件名取得
			helper.setSubject(title);
			// 本文設定
			if (StringUtils.isNotBlank(htmlText)) {
				helper.setText(plainText, htmlText);
			} else {
				helper.setText(plainText, false);
			}
			// メール送信
			mailSender.send(message);
		} catch (MailException e) {
			LOG.warn(e);
		} catch (MessagingException e) {
			LOG.warn(e);
		}
	}
}
