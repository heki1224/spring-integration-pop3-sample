package com.heki1224.spring.integration.pop3.sample.bootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;

import com.heki1224.spring.integration.pop3.sample.handler.MailMessageHandler;

/**
 * Bootstrap Class
 * 
 * @author heki1224
 * 
 */
public class Batch {

	private static final Log LOG = LogFactory.getLog(Batch.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final ClassPathXmlApplicationContext context =
			new ClassPathXmlApplicationContext("spring/app-config.xml");

		final DirectChannel inputChannel = context.getBean("receiveChannel", DirectChannel.class);
		final MailMessageHandler handler = context.getBean(MailMessageHandler.class);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					inputChannel.unsubscribe(handler);
					context.close();
					LOG.info("batch stop.");
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		});

		LOG.info("batch start.");
		inputChannel.subscribe(handler);
	}
}
