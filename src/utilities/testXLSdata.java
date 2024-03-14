package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class testXLSdata {

	public static <ReadXLSdata> void main(String[] args) throws EncryptedDocumentException, IOException {
		// TODO Auto-generated method stub
		testXLSdata read = new testXLSdata();
		read.getData("login");
	}
	public String[][] getData(String excelSheetName) throws EncryptedDocumentException, IOException
	{
		File f = new File(System.getProperty("/taskEdverse/src/testdata/testdata.xlsx"));
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet<?, ?> sheetName = (Sheet<?, ?>) wb.getSheet(excelSheetName);
		
		int totalRows = ((org.apache.poi.ss.usermodel.Sheet) sheetName).getLastRowNum();
		System.out.println(totalRows);
		Row rowCells = ((org.apache.poi.ss.usermodel.Sheet) sheetName).getRow(0);
		int totalCols = rowCells.getLastCellNum();
		System.out.println(totalCols);
		
		DataFormatter format = new DataFormatter();
		
		String testData[][] = new String[totalRows][totalCols];
		for (int i=1; i<=totalRows; i++) {
			for (int j=0; j<totalCols;j++) {
				testData[i-1][j] = format.formatCellValue(((org.apache.poi.ss.usermodel.Sheet) sheetName).getRow(i).getCell(j));
				System.out.println(testData[i-1][j]);
			}
		}
		return testData;
	}
}
