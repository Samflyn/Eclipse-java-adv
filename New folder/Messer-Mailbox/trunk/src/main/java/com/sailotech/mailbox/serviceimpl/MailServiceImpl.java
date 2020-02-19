package com.sailotech.mailbox.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sailotech.mailbox.config.Props;
import com.sailotech.mailbox.util.FileExtractor;
import com.sailotech.mailbox.util.MailConnectionUtils;
import com.sailotech.mailbox.util.MailConstants;

@Service
public class MailServiceImpl {

	private static final Logger log = Logger.getLogger(MailServiceImpl.class.getName());

	@Autowired
	private MailConnectionUtils mailConnectionUtils;

	@Autowired
	private FileExtractor fileExtractor;
	
	@Autowired
	private MailConstants mailConstants;

	Folder emailFolder = null;

	String response = "";

	@Autowired
	Props props;

	/**
	 * It will open new connection to mail if not exist and access to the mail
	 * folder and read all unread messages if any unread message is having
	 * attachments(include zip), downloads the attachments with configured
	 * extensions
	 * 
	 */
	@Scheduled(fixedDelayString = "${scheduler.fixed.delay}")
	public void downloadMailConfigService() throws MessagingException {
		log.info("Entered into MailConfigServiceImpl downloadMailConfigService");
		try {
			// mail configuration
			long time = System.currentTimeMillis();
			log.info("open new/open connection ");
			mailConnectionUtils.openMailStore().connect();
			log.info("open new/open connection completed" + (System.currentTimeMillis() - time));

			MimeMessage[] messages = mailConnectionUtils.getUnreadMessages(MailConstants.folder);

			if (messages.length > 0) {
				response = readMailFromMailbox(messages);
			} else {
				response = "No recent email with Attachment";
				log.info(response);
			}
			// close the store and folder objects
			// emailFolder.close(false);

			// mailConnectionUtils.CloseMailStore();
		} catch (FolderClosedException ex) {
			ex.printStackTrace();

			log.info("downloadMailConfigService FolderClosedException" + ex.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			log.info("downloadMailConfigService MessagingException" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("downloadMailConfigService Exception" + e.getMessage());
		} finally {
//			mailConnectionUtils.CloseMailStore();
			mailConnectionUtils.closeEmailFolder();
		}
		log.info("scheduler response ::" + response);
	}

	/**
	 * 
	 * 
	 * @param messages
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private String readMailFromMailbox(MimeMessage[] messages)
			throws MessagingException, IOException, FileNotFoundException {

		for (int i = 0; i < messages.length; i++) {
			log.info("messages ::" + messages[i]);
			MimeMessage message = new MimeMessage(messages[i]);
			Address fromAddress = message.getFrom()[0];
			String contentType = message.getContentType();
			if (contentType.contains("multipart")) {

				long startTime = System.currentTimeMillis();

				String domainName = MailConnectionUtils.getDomainName(fromAddress);
				
				String tempDir = props.getMailBoxTempDir();
				
				Multipart multiPart = (Multipart) message.getContent();
				for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
					MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
					log.info(" size " + part.getSize());
					// content may contain attachments
					if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
						long sizeInBytes = part.getSize();
						long sizeInMb = sizeInBytes / (1024 * 1024);
						if (sizeInMb <= 10) {
							response = downloadAttachments(tempDir, part);
						} else {
							response = "Attachment size is exceeded the limit";
						}
					}
				}
				log.info("message download time " + (System.currentTimeMillis() - startTime));
			}
		}
		return response;
	}

	// Download Attachments from mails
	private String downloadAttachments(String tempFilePath, MimeBodyPart part) {
		String destDir = props.getMailBoxDestDir();
		LocalDate date = LocalDate.now();
		destDir = destDir + "/" + date.getYear() + "/" + date.getMonth() + "/" + date.getDayOfMonth();

		try {
			String fileName = part.getFileName();
			if(fileName.contains(".")) {
			String extension = fileName.split("\\.")[1].toLowerCase();
			
			switch (extension) {
			case MailConstants.FILE_TYPE_ZIP:
				saveFile(tempFilePath, part, fileName);
				fileExtractor.extractFromZIP(tempFilePath, part, fileName,destDir);
				
				break;
			case MailConstants.FILE_TYPE_RAR:
				saveFile(tempFilePath, part, fileName);
				fileExtractor.extractFromRAR(tempFilePath, part, fileName,destDir);
				
				break;
			default:
				if(Props.extensions.contains(extension)) {
					saveFile(destDir, part, fileName);
				}else
				{
					return response = "un supported file";
				}
				
			}
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private void saveFile(String filePath, MimeBodyPart part, String fileName) throws IOException, MessagingException {
		if (!Files.exists(Paths.get(filePath))) {
			Files.createDirectories(Paths.get(filePath));
		}
		part.saveFile(filePath + File.separator + fileName);
		
		response = "Attachment downloaded successfully";
	}

}
