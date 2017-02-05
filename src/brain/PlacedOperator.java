package brain;

import java.util.Comparator;

//import java.util.Comparator;

/* 
 * This internal object exists so that I can locate operators (some may be 
 * multi-char), control by precedence or name, and easily sort
 * 
 * NOTE: this version has two comparators:
 *    * byIndex sorts by start index only.
 *    * byRank sorts by rank and then by start index.
 *    
 * This allows me to flush overlapping operators, and then to re-sort for actual computation 
 * 
 */
public class PlacedOperator /*implements Comparable<Object>*/ {
	String txt;
	int rank,start,end;
	boolean parenQ;
	
	public PlacedOperator(String str, String[][] rankedOperators, int s, int e){
		this.txt=str;
		this.start=s;
		this.end=e;
		this.rank=findRank(str,rankedOperators);
		if(rank==4)
			this.parenQ=true; 	
		else
			this.parenQ=false;
	}
	
	// returns the rank (0-5) of the operator, based on location in jagged rankedOps array
	// it will return -1 if rank is not found, but this should not happen.
	private int findRank(String s, String[][] rankedOps) {
		int out=-1;
		for(int i=0;i<rankedOps.length;i++) {
			for(int j=0; j<rankedOps[i].length;j++) {
				if(rankedOps[i][j].equalsIgnoreCase(s))
					return i;
			}
		}
		return out;
	}
	
    /*
	@Override
	public int compareTo(Object comparestu) {
		PlacedOperator compTo = (PlacedOperator) comparestu;
		if(this.rank!=compTo.rank)
			return this.rank-compTo.rank;
		else
			return this.start-compTo.start;
	}
	*/
	public static class byIndex implements Comparator<PlacedOperator> {
		public byIndex() {}

		@Override
		public int compare(PlacedOperator p1, PlacedOperator p2) {
			Integer pInd1 = p1.start;
			Integer pInd2 = p2.start;
			int out = pInd1.compareTo(pInd2);
			return out;
		  }
	}
	
	public static class byRank implements Comparator<PlacedOperator> {
		public byRank() {}

		@Override
		public int compare(PlacedOperator p1, PlacedOperator p2) {
			Integer pRan1 = p1.rank;
			Integer pRan2 = p2.rank;
			int out = pRan1.compareTo(pRan2);
			if(out==0) {
				Integer pInd1 = p1.start;
				Integer pInd2 = p2.start;
				out = pInd1.compareTo(pInd2);
			}
			return out;
		  }
	}

}

