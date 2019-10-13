package assignment;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
	public String parseExcelData(String src) {
		StringBuffer sb = new StringBuffer();
		File file = new File(src);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			Workbook wb = new XSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				Iterator<Cell> cell = row.iterator();
				while (cell.hasNext()) {
					Cell cell1 = cell.next();
					switch (cell1.getCellType()) {
					case STRING:
						sb.append(cell1.getStringCellValue() + ",");
						break;
					case NUMERIC:
						sb.append(cell1.getNumericCellValue() + ",");
						break;
					}
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String data = sb.toString();
		return data;
	}
}