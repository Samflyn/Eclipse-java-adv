package assignment;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	public void WriteData(String data, String string) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(string);
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Emp Data");
			int rowIndex = 0;
			String[] records = data.split("\n");
			for (String record : records) {
				String[] split = record.split(":");
				XSSFRow row = sheet.createRow(rowIndex++);
				XSSFCell cell = row.createCell(0);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellValue(Integer.parseInt(split[0]));
				cell = row.createCell(1);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(split[1]);
				cell = row.createCell(2);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellValue(Double.parseDouble(split[2]));
				cell = row.createCell(3);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellValue(Integer.parseInt(split[3]));
				wb.write(fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
}
