package com.heki1224.spring.mail.send.helper;

import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

/**
 * Multibyte Mime Message Helper Class.
 * 
 * @author heki1224
 * 
 */
public class MultibyteMimeMessageHelper extends MimeMessageHelper {

	private static final String MULTIPART_SUBTYPE_ALTERNATIVE = "alternative";

	private static final String CONTENT_TYPE_ALTERNATIVE = "text/alternative";

	private static final String CONTENT_TYPE_HTML = "text/html";

	private static final String CONTENT_TYPE_CHARSET_SUFFIX = ";charset=";

	private Header header = null;

	public MultibyteMimeMessageHelper(MimeMessage mimeMessage) {
		super(mimeMessage);
	}

	public MultibyteMimeMessageHelper(MimeMessage mimeMessage, int multipartMode, String encoding)
			throws MessagingException {
		super(mimeMessage, multipartMode, encoding);
	}

	private void setHtmlTextToMimePart(MimePart mimePart, String text) throws MessagingException {
		if (getEncoding() != null) {
			mimePart.setContent(text, CONTENT_TYPE_HTML
				+ CONTENT_TYPE_CHARSET_SUFFIX
				+ getEncoding());
		} else {
			mimePart.setContent(text, CONTENT_TYPE_HTML);
		}
		if (header != null) {
			mimePart.setHeader(header.getName(), header.getValue());
		}
	}

	private void setPlainTextToMimePart(MimePart mimePart, String text) throws MessagingException {
		if (getEncoding() != null) {
			mimePart.setText(text, getEncoding());
		} else {
			mimePart.setText(text);
		}
		if (header != null) {
			mimePart.setHeader(header.getName(), header.getValue());
		}

	}

	private MimeBodyPart getMainPart() throws MessagingException {
		MimeMultipart mimeMultipart = getMimeMultipart();
		MimeBodyPart bodyPart = null;
		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bp = mimeMultipart.getBodyPart(i);
			if (bp.getFileName() == null) {
				bodyPart = (MimeBodyPart) bp;
			}
		}
		if (bodyPart == null) {
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeMultipart.addBodyPart(mimeBodyPart);
			bodyPart = mimeBodyPart;
		}
		return bodyPart;
	}

	@Override
	public void setText(String text, boolean html) throws MessagingException {
		Assert.notNull(text, "Text must not be null");
		MimePart partToUse;
		if (isMultipart()) {
			partToUse = getMainPart();
		} else {
			partToUse = getMimeMessage();
		}
		if (html) {
			setHtmlTextToMimePart(partToUse, text);
		} else {
			setPlainTextToMimePart(partToUse, text);
		}
	}

	@Override
	public void setText(String plainText, String htmlText) throws MessagingException {
		Assert.notNull(plainText, "Plain text must not be null");
		Assert.notNull(htmlText, "HTML text must not be null");

		MimeMultipart messageBody = new MimeMultipart(MULTIPART_SUBTYPE_ALTERNATIVE);
		getMainPart().setContent(messageBody, CONTENT_TYPE_ALTERNATIVE);
		if (header != null) {
			getMainPart().setHeader(header.getName(), header.getValue());
		}

		// Create the plain text part of the message.
		MimeBodyPart plainTextPart = new MimeBodyPart();
		setPlainTextToMimePart(plainTextPart, plainText);
		messageBody.addBodyPart(plainTextPart);

		// Create the HTML text part of the message.
		MimeBodyPart htmlTextPart = new MimeBodyPart();
		setHtmlTextToMimePart(htmlTextPart, htmlText);
		messageBody.addBodyPart(htmlTextPart);
	}

	public void setHeader(Header header) {
		this.header = header;
	}
}
