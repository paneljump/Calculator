package brain;
import java.util.ArrayList;
import java.util.List;

/* 
 * The ParenChecker class checks a string for parentheses. All public methods are static. 
 * There are only five, so I didn't feel it was worth it to enforce with an interface.
 *    int checkParen(String str)   
 *    		-- -1 for no error, index of problem character, if any
 *    ParenObject findParenthetical(String str, int startIndex, int endIndex)  
 *    		-- returns first top-level ParenObject (see below)
 *    		-- Note that start index and int index refer to original string, not substrings.
 *    public static ParenObject findInnerParentheticalExpr(String str, int startIndex, int endIndex,
 *		 ExpressionParser EP) 
 *			-- returns ParenObject (see below) drilled down to innermost
 *			-- accounts for 
 *
 *	  public static int findOpenParen(String str, int index)
 *			-- returns index of '(' in string
 *    public static int findCloseParen(String str, int index)
 *    		-- returns index of ')' in string
 * 
 * Additionally, this class uses the class ParenObject, a simple object for passing data
 * 
 * class ParenObject {
 *	public boolean errorQ;
 *	public boolean hasInner;
 *	public boolean parenOpQ;
 *	public int startIndex;
 *	public int endIndex;
 *	public String innerString;
 *	public String fullString;
 * 
 */
public class ParenChecker {
	//private String str;
	
	public ParenChecker(){}
	
	// has parentheses?
	public static boolean hasParen(String str){
		if(!str.contains("(") && !str.contains(")")) 
			return false;
		else
			return true;
	}
	
	// -1 if no error
	public static int checkParen(String str){
		int index=0, c=0, o=0;
		ArrayList<Integer> openIndices = new ArrayList<Integer>();
	
		while(index<str.length()) {
			// if ')' ever ahead of '(', error immediately
			if(str.charAt(index)=='('){
				o++;
				openIndices.add(0,index); // I assume it converts int to Integer for me
			}
			else if(str.charAt(index)==')') {
				c++;
				if(c>o)
					return index; // location of unmatched ')'
				openIndices.remove(0); // removes most recent index of '('
			}
			index++;
		}
		if(o>c){
			return (int) openIndices.get(0); // location of unmatched '('
		}
		return -1;
	}
	
	//returns first top-level paren sub-expr of str, no other checks
	public static ParenObject findParenthetical(String str, int startIndex, int endIndex){ 
		int index=startIndex,endParen;
		ParenObject out = new ParenObject(str, startIndex, endIndex);
		String subStr=str.substring(startIndex, endIndex+1); // str is original string, subStr is what we're dealing with
		
		// case 1: if there are no parentheses at all, no error, full string
		if(!subStr.contains("(") && !subStr.contains(")")) {
			out.hasInner=false;
			out.innerString=subStr;
			return out;
		}
		
		// case 2: has () somewhere
		out.hasInner=true;
		while(index<endIndex) {
			
			if(str.charAt(index)=='(') {
				endParen=findCloseParen(str,index);
				if(endParen!=-1){
					out.startIndex=index+1; // start index of substring (not paren)
					out.endIndex=endParen-1; // end index of substring (not paren)
					out.innerString=str.substring(index+1,endParen);
					return out;
				}
				else {
					out.errorQ=true;
					out.startIndex=index;
					return out;
				}
			}
			index++;
		}
		return out; // should never be reached, but the compiler doesn't know that
	}
	
	public static ParenObject findInnerParentheticalExpr(String str, int startIndex, int endIndex,
			ExpressionParser EP) {
		
		//String subStr;
		int POstartIndex;
	
		ParenObject fromFP=ParenChecker.findParenthetical(str, startIndex,endIndex);
		if(fromFP.errorQ==true){
			// System.out.println("Error in parenthetical expression");
			return fromFP;
		}
		else if(fromFP.hasInner==false && EP.findAllOperatorStartEnd(fromFP.innerString).size()>0) {
			return fromFP;  // if there are operators in inner string, don't look for parenOp
		}
		else if(fromFP.hasInner==false) {
			// step 1: check outside paren for a parenOp (-1 for no parenOp, otherwise index of parenOp in original string)
			
			int parenOpIndex=insideParenOpIndex(str,startIndex,EP);
			// step 2: if parenOp detected, adjust the inner expression to include it
			if(parenOpIndex>-1){
				List<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
				POstartIndex=POlist.get(parenOpIndex).start; 
				fromFP.startIndex=POstartIndex;
				fromFP.endIndex++;
				fromFP.innerString=str.substring(POstartIndex,fromFP.endIndex+1);
				fromFP.parenOpQ=true;
			}
			
			return fromFP;
		}
		else {
			//String subStr=fromFP.innerString;
			int newStartIndex=fromFP.startIndex;
			int newEndIndex=fromFP.endIndex;
			return ParenChecker.findInnerParentheticalExpr(str,newStartIndex,newEndIndex,EP);
			}
	}
	
	// used above and in ExpressionParser.parseParenSolution
	public static int insideParenOpIndex(String str,int oldStartIndex, ExpressionParser EP) {
		//String str=fromFP.fullString;
		boolean hasPO=false;
		// step 1: check outside paren for a parenOp (
		List<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		int POendIndex=oldStartIndex-2; //where the parenOp should end
		int i=0;
		for(i=0;i<POlist.size();i++){
			if(POlist.get(i).end==POendIndex){
				for(int j=0;j<EP.parenOps.length;j++){
					if(POlist.get(i).txt.equals(EP.parenOps[j])){
						hasPO=true;
						break;
					}
				}
			}
			if(hasPO)
				break;
		}
		
		if(hasPO)
			return i;
		else
			return -1;
		
	}
	
	// returns index of ')' in string
	public static int findCloseParen(String str, int index) {
		int i=index+1, stop=str.length(), skip=0;
		char ch;
		while(i<stop){
			ch=str.charAt(i);
			if(ch=='(')
				skip++;
			if(skip==0 && ch==')')
				return i;
			if(ch==')')
				skip--;
			i++;
		}
		return -1; //if match not found
	}
	
	// returns index of '(' in string
	public static int findOpenParen(String str, int index) {
		int i=index-1, skip=0;
		char ch;
		while(i>=0){
			ch=str.charAt(i);
			if(ch==')')
				skip++;
			if(skip==0 && ch=='(')
				return i;
			if(ch=='(')
				skip--;
			i--;
		}
		return -1; //if match not found
	}
	
}



