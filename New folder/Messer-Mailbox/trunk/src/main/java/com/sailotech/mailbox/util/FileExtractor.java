/**
 * 
 */
package com.sailotech.mailbox.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.internet.MimeBodyPart;

import org.springframework.stereotype.Component;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;
import com.sailotech.mailbox.config.Props;
import com.sailotech.mailbox.serviceimpl.MailServiceImpl;

/**
 * @author dhanunjaya.potteti
 *
 */
@Component
public class FileExtractor {
	private static final Logger logger = Logger.getLogger(MailServiceImpl.class.getName());

	public void extractFromZIP(String filePath, MimeBodyPart part, String fileName, String destFileDir)
			throws IOException {
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(filePath + File.separator + fileName));
		ZipEntry entry;
		while ((entry = zipIn.getNextEntry()) != null) {
			String extensionZip = entry.getName().split("\\.")[1];
			String[] zipFileName = entry.getName().split("/");
			if (Props.extensions.contains(extensionZip.toLowerCase())) {
				String zipFilePath = destFileDir + File.separator + zipFileName[zipFileName.length - 1];
				extractFile(zipIn, zipFilePath, destFileDir);
			}
			zipIn.closeEntry();
		}
		zipIn.close();
		File file = new File(filePath + File.separator + fileName);
		file.delete();
	}

	public void extractFromRAR(String filePath, MimeBodyPart part, String fileName, String destFileDir)
			throws IOException {
		File tempFile= new File(filePath + "/" + fileName);
		Archive a = null;
		if (!Files.exists(Paths.get(destFileDir + "/" + fileName.split("\\.")[0]))) {
			Files.createDirectories(Paths.get(destFileDir + "/" + fileName.split("\\.")[0]));
		}

		try {
			a = new Archive(new FileVolumeManager(tempFile));
		} catch (RarException e) {
			logger.info("RarException# " + e.getMessage());
		} catch (IOException e) {
			logger.info("IOException# " + e.getMessage());
		}
		if (a != null) {
			a.getMainHeader().print();
			FileHeader fh = a.nextFileHeader();

			while (fh != null) {
				try {

					if (fh.getFileNameString().contains("."))
						if (Props.extensions.contains(fh.getFileNameString().toLowerCase().split("\\.")[1])) {

							{
								File out = new File(destFileDir + "/" + fh.getFileNameString().trim());
								System.out.println(out.getAbsolutePath());
								FileOutputStream os = new FileOutputStream(out);
								a.extractFile(fh, os);

								os.close();
							}
						}
				} catch (FileNotFoundException e) {
					logger.info("FileNotFoundException# " + e.getMessage());
				} catch (RarException e) {
					logger.info("RarException# " + e.getMessage());
				} catch (IOException e) {
					logger.info("IOException# " + e.getMessage());
				}
				fh = a.nextFileHeader();
			}
		}
		 tempFile.delete();
	}

	// Zip File Extraction
	private void extractFile(ZipInputStream zipIn, String destFilesPath, String destFilesDir) throws IOException {
		logger.info("downloadMailConfigService extractFile");
		if (!Files.exists(Paths.get(destFilesDir))) {
			Files.createDirectories(Paths.get(destFilesDir));
		}
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFilesPath))) {
			byte[] bytesIn = new byte[Props.BUFFER_SIZE];
			int read = 0;
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
