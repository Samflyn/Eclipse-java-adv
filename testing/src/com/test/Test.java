package com.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Test {
	public static void main(String[] args) throws InterruptedException, ZipException, IOException {
		extractFolder("E:\\MailBox.zip");
	}

	public static void extractFolder(String zipFile) throws ZipException, IOException {
		int BUFFER = 4096;
		ZipFile zip = new ZipFile(new File(zipFile));
		String newPath = zipFile.substring(0, zipFile.length() - 4);
		new File(newPath).mkdir();
		Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();
		while (zipFileEntries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File destFile = new File(newPath, currentEntry);
			File destinationParent = destFile.getParentFile();
			destinationParent.mkdirs();
			if (!entry.isDirectory()) {
				BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
				int currentByte;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
				while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
			}
			if (currentEntry.endsWith(".zip")) {
				extractFolder(destFile.getAbsolutePath());
			}
		}
		zip.close();
//		File deleteFile = new File(zipFile);
//		deleteFile.delete();
	}

}