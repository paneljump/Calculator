package data;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
	
	public DataParser() {}
	
	public static List<ArrayList<?>> filterData( List<ArrayList<?>> rawData, String testName ) {
		List<ArrayList<?>> out = new ArrayList<>();
		int len=rawData.size();
		String tN;
		for(int i=0;i<len;i++) {
			tN=(String) rawData.get(i).get(0);
			if( tN.equalsIgnoreCase(testName) )
				out.add(rawData.get(i));
		}
		return out;
	}
	
	public static Object[][] parseData(List<ArrayList<?>> rawData) {
		int rows = rawData.size();
		int cols = 0;
		for(int i=0;i<rows;i++) { // length of ArrayLists might vary; find the biggest
			if( rawData.get(i).size() > cols )
				cols=rawData.get(i).size();
		}
		Object[][] out=new Object[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++) {
				out[i][j] = rawData.get(i).get(j);
			}
		return out;
	}
	
	public static Object[][] filterParseData(List<ArrayList<?>> unfilteredRawData, String testName) {
		List<ArrayList<?>> rawData = filterData(unfilteredRawData, testName);
		int rows = rawData.size();
		int cols = 0;
		for(int i=0;i<rows;i++) { // length of ArrayLists might vary; find the biggest
			if( rawData.get(i).size() > cols )
				cols=rawData.get(i).size();
		}
		Object[][] out=new Object[rows-1][cols-1];
		for(int i=0;i<rows-1;i++) {
			for(int j=0;j<cols-1;j++) {
				try{
					out[i][j] = rawData.get(i+1).get(j+1);
				}
				catch(Exception e){ // add a null object if the ArrayList is shorter than expected
					out[i][j] = null;
				}
			}
		}
		return out;
	}

}
