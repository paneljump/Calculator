package data;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;

// HSSF for <=2003
// XSSF for >=2007
// add either to the front of Workbook, Sheet, Row, Cell

public class MyExcelReader {
	private String filepath;
	private int numberOfSheets;
	private String[] sheetNames;
	
	// this should set filepath and pull file-level data. Always close streams.
	public MyExcelReader(String fp) throws IOException{
		this.setupByFilepath(fp);
	}
	
	public void setupByFilepath(String fp) throws IOException { // so I can re-use the object
		this.filepath=fp;
		FileInputStream inputStream=new FileInputStream(new File(this.filepath));
		Workbook workbook=getWorkbook(inputStream);
		numberOfSheets=workbook.getNumberOfSheets();
		sheetNames=new String[numberOfSheets];
		for(int i=0;i<numberOfSheets;i++){
			sheetNames[i]=workbook.getSheetName(i);
		}
		workbook.close();
	    inputStream.close();
	}
	
	private Workbook getWorkbook(FileInputStream inputStream) throws IOException{
		Workbook workbook = null;
		 
	    if (this.filepath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook(inputStream);
	    } else if (this.filepath.endsWith("xls")) {
	        workbook = new HSSFWorkbook(inputStream);
	    } else {
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	 
	    return workbook;
	}
	
	private Object getCellValue(Cell cell){
		switch(cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(cell))
				return cell.getDateCellValue();
			else
				return cell.getNumericCellValue();
		default:
			System.out.println("Object type not handled, returning null.");
			return null;
			
		}
	}
	
	public List<ArrayList<?>> readDataFromExcelSheet(String sheetName) throws IOException {
		int sheetNum=-1;
		for(int i=0;i<this.numberOfSheets;i++) 
			if(this.sheetNames[i].equalsIgnoreCase(sheetName)) {
				sheetNum=i;
				break;
			}
		if(sheetNum==-1)
			return null;
		
		return readDataFromExcelSheet(sheetNum);
	}
	
	public List<ArrayList<?>> readDataFromExcelSheet(int sheetNum) throws IOException {
	    List<ArrayList<?>> sheetData = new ArrayList<>();
	    FileInputStream inputStream=new FileInputStream(new File(this.filepath));
		Workbook workbook=getWorkbook(inputStream);
		
	    Sheet thisSheet = workbook.getSheetAt(sheetNum);
	    Iterator<Row> iterator = thisSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        ArrayList<Object> innerList=new ArrayList<>();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            innerList.add(getCellValue(nextCell));
	        }
	        sheetData.add(innerList);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return sheetData;
	}

}
