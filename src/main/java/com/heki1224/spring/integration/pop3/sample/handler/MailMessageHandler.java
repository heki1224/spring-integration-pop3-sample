package com.heki1224.spring.integration.pop3.sample.handler;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.stereotype.Component;

/**
 * E-Mail Handler
 * 
 * @author heki1224
 * 
 */
@Component
public class MailMessageHandler implements MessageHandler {

	private static final Log LOG = LogFactory.getLog(MailMessageHandler.class);

	/**
	 * 
	 */
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		MimeMessage mimeMessage = (MimeMessage) message.getPayload();
		try {
			// from mailaddress
			Address[] fromAddresses = mimeMessage.getFrom();
			InternetAddress fromAddress = (InternetAddress) fromAddresses[0];
			String fromAddressString = fromAddress.getAddress();
			LOG.info("fromAddress=" + fromAddressString);

			// to mailaddress
			Address[] toAddresses = mimeMessage.getRecipients(RecipientType.TO);
			InternetAddress toAddress = (InternetAddress) toAddresses[0];
			String toAddressString = toAddress.getAddress();
			LOG.info("toAddress=" + toAddressString);

			// subject
			String subject = mimeMessage.getSubject();
			LOG.info("subject=" + subject);

			// body
			String body = (String) mimeMessage.getContent();
			LOG.info("body=" + body);

		} catch (MessagingException e) {
			LOG.error(e);
		} catch (Exception e) {
			LOG.error(e);
		}
	}
}
