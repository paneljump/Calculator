package brain;

import java.util.Collections;
/* This internal object exists so that I can locate operators (some may be 
 * multi-char), control by precedence or name, and easily sort by start index
 * 
 * 
 */
public class PlacedOperator implements Comparable {
	String txt;
	int index,start,end;
	
	public PlacedOperator(String str, int ind, int s, int e){
		this.txt=str;
		this.index=ind;
		this.start=s;
		this.end=e;
	}

	@Override
	public int compareTo(Object comparestu) {
		int compareStart= ((PlacedOperator) comparestu).start;
		return this.start-compareStart;
	}

}

