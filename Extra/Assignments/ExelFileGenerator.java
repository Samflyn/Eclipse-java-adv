package com.stech.mnrao.excel;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExelFileGenerator {

	
	public void generateExcelFile(String [] records, String filePath)
	{
		
		try
		{
			FileOutputStream fos = new FileOutputStream(filePath) ;
			
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("EmpData");
			
			int rowIndex=0;
			for(String record:records)
			{
				
				String[] fields = record.split(":");
				
				XSSFRow row = sheet.createRow(rowIndex++);
				
				XSSFCell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.parseInt(fields[0]));
				
				cell = row.createCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(fields[1]);
				
				cell = row.createCell(2);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Double.parseDouble(fields[2]));
				
				cell = row.createCell(3);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.parseInt(fields[3]));
				
				cell = row.createCell(4);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(fields[4]);
				
				cell = row.createCell(5);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(Integer.parseInt(fields[5]));				
			}			
			
			workBook.write(fos);
			
			fos.close();	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
