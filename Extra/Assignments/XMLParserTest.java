package com.stech.mnrao.xml;

import java.io.IOException;

import javax.sound.midi.Synthesizer;

public class XMLParserTest {
	
	public static void main(String[] args) throws IOException {
		
		String xmlFilePath = args[0];
		
		String serachKey = args[1];
		
		XMLParser parser = new XMLParser();
		
		String empData = parser.parseXMLData(xmlFilePath, serachKey);
		
		String[] empRecords = empData.split("\n");
		
		for(String record:empRecords)
		{
			System.out.println(record);
		}
		
		LoadRecordsIntoDatabase  dataBase = new LoadRecordsIntoDatabase();
		
		dataBase.loadRecordsIntoDatabase(empRecords);
		
		System.out.println("Successfully Loaded");
		
		
	}

}

