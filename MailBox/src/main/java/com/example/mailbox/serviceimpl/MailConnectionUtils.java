package com.example.mailbox.serviceimpl;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.springframework.stereotype.Component;

@Component
public class MailConnectionUtils {

	private static final Logger log = Logger.getLogger(MailConnectionUtils.class.getName());

	private static Store store;
	private static Session emailSession;
	private static Folder emailFolder;

	/**
	 * Open session with properties
	 * 
	 * @return this
	 */
	public MailConnectionUtils openMailStore() {
		Properties properties = getProperties();
		emailSession = Session.getDefaultInstance(properties);
		return this;
	}

	/**
	 * If store !=null and connected then respond true otherwise false
	 * 
	 * @return
	 */
	public boolean isStoreConnected() {
		return store != null && store.isConnected();
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.store.protocol", Props.protocol);
		properties.put("mail.smtp.host", Props.hostname);
		properties.put("mail.smtp.port", Props.port);
		properties.put("mail.smtp.starttls.enable", "true");
		if ("imaps".equalsIgnoreCase(Props.protocol)) {
			properties.put("mail.imap.partialfetch", false);
			properties.put("mail.imap.fetchsize", "1048576");
		}
		return properties;
	}

	/**
	 * Open new Connection Store with given properties with emailSession new session
	 * Store will not create If store is already exist and connected.
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public MailConnectionUtils connect() throws MessagingException {
		if (!isStoreConnected()) {
			long time = System.currentTimeMillis();
			log.info("open new connection ");
			store = emailSession.getStore(Props.protocol);
			store.connect(Props.hostname, Props.username, Props.password);
			log.info("connection opend " + (System.currentTimeMillis() - time));
		}
		return this;
	}

	/**
	 * return existing store in globe variable
	 * 
	 * @return
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * Close the Store if exist and connected. If the connection is closed return
	 * <code>true</code> if already closed return <code>false</code>
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public boolean CloseMailStore() throws MessagingException {
		if (isStoreConnected()) {
			store.close();
			return true;
		}
		return false;
	}

	/**
	 * Open the given folder with existing {@link Store} <br>
	 * It will respond with unread Mails only
	 * 
	 * @param folder
	 * @return
	 * @throws MessagingException
	 */
	public MimeMessage[] getUnreadMessages(String folder) throws MessagingException {
		emailFolder = store.getFolder(folder);
		if (!emailFolder.isOpen()) {
			emailFolder.open(Folder.READ_WRITE);
			return (MimeMessage[]) emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
		}
		return null;
	}

	public boolean closeEmailFolder() throws MessagingException {
		if (emailFolder != null && emailFolder.isOpen()) {
			emailFolder.close(false);
			return true;
		}
		return false;
	}

	/**
	 * return domain name from the given Address
	 * 
	 * @param adrs
	 * @return
	 */
	public static String getDomainName(Address adrs) {
		String[] domain = ((InternetAddress) adrs).getAddress().split("@");
		String[] domainName = domain[1].split("\\.");
		return domainName[0];
	}

}
