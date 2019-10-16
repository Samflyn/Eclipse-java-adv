package com.stech.mnrao.xml;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	
	public String parseXMLData(String filePath, String searchKey) throws IOException
	{
		
		File file = new File(filePath);

		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);

		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		try
		{
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Build Document
		try
		{
			document = builder.parse(dis);
			
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// /Normalize the XML Structure; It's just too important !!
		document.getDocumentElement().normalize();

		// Here comes the root node
		Element root = document.getDocumentElement();
		System.out.println (root.getNodeName());

		// Get all employees
		NodeList nList = document.getElementsByTagName(searchKey);

		StringBuffer buffer = new StringBuffer();
		
		System.out.println ("no of Records : "+nList.getLength());

		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node node = nList.item(temp);
			
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				// Print each employee's detail
				Element eElement = (Element) node;
				// buffer.append(eElement.getAttribute("id")+",");
				buffer.append(eElement.getElementsByTagName("EMPNO").item(0).getTextContent()
						+ ",");
				buffer.append(eElement.getElementsByTagName("ENAME").item(0).getTextContent()
						+ ",");
				buffer.append(eElement.getElementsByTagName("SALARY").item(0).getTextContent()
						+ ",");
				buffer.append(eElement.getElementsByTagName("DEPTNO").item(0).getTextContent()
						+ ",");
				
				buffer.append(eElement.getElementsByTagName("GENDER").item(0).getTextContent()
						+ ",");
				
				buffer.append(eElement.getElementsByTagName("AGE").item(0).getTextContent()
						);
				buffer.append("\n");
			}
		}

		String data = buffer.toString();

		return data;

	
	}
}

