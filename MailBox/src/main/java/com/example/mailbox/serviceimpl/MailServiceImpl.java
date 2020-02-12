package com.example.mailbox.serviceimpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.mailbox.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger log = Logger.getLogger(MailServiceImpl.class.getName());

	@Autowired
	private MailConnectionUtils mailConnectionUtils;

	@Autowired
	private EmailFileUtils emailFileUtils;

	Folder emailFolder = null;

	String response = "";

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

			MimeMessage[] messages = mailConnectionUtils.getUnreadMessages(Props.folder);

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

		List<String> validFromAddresses = emailFileUtils.getEmails();

		for (int i = 0; i < messages.length; i++) {
			log.info("messages ::" + messages[i]);
			MimeMessage message = new MimeMessage(messages[i]);
			Address fromAddress = message.getFrom()[0];
			String contentType = message.getContentType();
			if (validFromAddresses.contains(((InternetAddress) fromAddress).getAddress())
					&& contentType.contains("multipart")) {
				long startTime = System.currentTimeMillis();

				String domainName = MailConnectionUtils.getDomainName(fromAddress);
				String filePath = Props.saveDirectory;// + File.separator + domainName + File.separator +
														// LocalDate.now();
				Multipart multiPart = (Multipart) message.getContent();
				for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
					MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
					log.info(" size " + part.getSize());
					// content may contain attachments
					if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
						long sizeInBytes = part.getSize();
						long sizeInMb = sizeInBytes / (1024 * 1024);
						if (sizeInMb <= 10) {
							response = downloadAttachments(filePath, part);
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
	private String downloadAttachments(String filePath, MimeBodyPart part) {
		// this part is attachment
		try {
			String fileName = part.getFileName();
			String extension = fileName.split("\\.")[1];
			if (Props.FILE_TYPE_ZIP.equalsIgnoreCase(extension)) {
				saveFile(filePath, part, fileName);
				extractFromZIP(filePath, part, fileName);
			} else if (Props.extensions.contains(extension)) {
				saveFile(filePath, part, fileName);

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

	private void extractFromZIP(String filePath, MimeBodyPart part, String fileName) throws IOException {
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(filePath + File.separator + fileName));
		ZipEntry entry;
		while ((entry = zipIn.getNextEntry()) != null) {
			String extensionZip = entry.getName().split("\\.")[1];
			String[] zipFileName = entry.getName().split("/");
			if (Props.extensions.contains(extensionZip)) {
				String zipFilePath = filePath + File.separator + zipFileName[zipFileName.length - 1];
				extractFile(zipIn, zipFilePath);
			}
			zipIn.closeEntry();
		}
		zipIn.close();
		File file = new File(filePath + File.separator + fileName);
		file.delete();
	}

	// Zip File Extraction
	private void extractFile(ZipInputStream zipIn, String saveDirectory) throws IOException {
		log.info("downloadMailConfigService extractFile");
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveDirectory));
		byte[] bytesIn = new byte[Props.BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

}
