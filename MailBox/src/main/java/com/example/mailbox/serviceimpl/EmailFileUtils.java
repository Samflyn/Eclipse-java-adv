package com.example.mailbox.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmailFileUtils {

	private static long lastModifed;
	
	private static List<String> emails;

	public List<String> getEmails() {

		return textToList(Props.emailFile);
	}
	
	
	private static List<String> textToList(String path) {

		try {
			Path p = Paths.get(path);
			if (Files.getLastModifiedTime(p, LinkOption.NOFOLLOW_LINKS).toMillis() > lastModifed) {
				String line = "";
				try(LineIterator it = FileUtils.lineIterator(p.toFile(), "UTF-8");) {
				    while (it.hasNext()) {
				        line += it.nextLine();
				    }
				}
				if(!StringUtils.isEmpty(line)) {
					line = line.trim();
					emails=  Arrays.asList(line.split(";"));
					emails.removeAll(Arrays.asList("", null));
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			emails = null;
		}
		return emails;
	}
}
