package utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	int rowcount =0;
	int startrow = 0;
	int startcol = 0;
	int cellcount=0;
	
	
	public void setExcel(String excelName) {
		
		try {
			workbook = new XSSFWorkbook(new File("data\\"+excelName+".xlsx"));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public String[][] getTableArray(String SheetName) throws Exception {
		
		System.out.println("ExcelLib");
		String[][] data = null;
		
		System.out.println(SheetName);

		try {
			
			sheet = workbook.getSheet(SheetName);
			
			rowcount = sheet.getPhysicalNumberOfRows();
			cellcount = sheet.getRow(0).getPhysicalNumberOfCells();
			data = new String[rowcount-1][cellcount];
			System.out.println(rowcount);
			System.out.println(cellcount);


			for (int i = 1; i < rowcount; i++) {
				System.out.println("i---> " + i);
				row = sheet.getRow(i);

				for (int j = 0; j < cellcount; j++) {

					cell = row.getCell(j);

					data[i-1][j] = cell.getStringCellValue();



					System.out.println("data["+(i-1)+"]["+(j)+"]" + "--->" +data[i-1][j]);

				}
			}
			
			workbook.close();

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Excel Lib error");

		
		}
		//		System.out.println(data);
		return data;   
	}
}
