package brain;
import java.util.ArrayList;
/* This code is copied from what I did months ago. It was working, but I'm 
 * being more careful with OO.
 * (What's OO?? I have no idea what I meant by that note!)
 * 
 */
public class ParenChecker {
	//private String str;
	
	public ParenChecker(){}
	
	// -1 if no error
	public int checkParen(String str){
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
	
	/* I need output to be generally useful objects: string arrays? Sure, why not?
	 * Format of this output is 1. error? 2. has inner? 3. start index, 
	 * 4. end index, 5. inner string (perhaps all) 6. full string (why not?)
	 * 7. parenOp T/F (does it have an operator requiring parentheses, such as
	 * trig or logs?)
	 * 
	 * Note that start index and int index refer to original string, not substrings.
	 */
	public String[] findParenthetical(String str, int startIndex, int endIndex){ //returns first top-level paren sub-expr of str
		int index=startIndex,endParen;
		String[] out=new String[7];
		out[0]="false"; //set to no error, unless we find one
		out[5]=str;
		out[6]="false"; //set to no parenOp; change @end if needed
		String subStr=str.substring(startIndex, endIndex+1);
		
		// case 1: if there are no parentheses at all, no error, full string
		if(!subStr.contains("(") && !subStr.contains(")")) {
			out[1]="false";
			out[2]=String.valueOf(startIndex);
			out[3]=String.valueOf(endIndex);
			out[4]=subStr;
			return out;
		}
		
		// case 2: has () somewhere, matches checked in checkParen()
		
		out[1]="true";
		while(index<endIndex) {
			
			if(str.charAt(index)=='(') {
				endParen=findCloseParen(str,index);
				if(endParen!=-1){
					out[2]=String.valueOf(index+1); //had added startIndex, took out
					out[3]=String.valueOf(endParen-1); // had added startIndex, took out
					out[4]=str.substring(index+1,endParen);
					return out;
				}
				else {
					out[0]="true"; 
					out[2]=String.valueOf(index);
					return out;
				}
			}
			index++;
		}
		return out; // will this ever be reached?
	}
	
	/* I'm modifying this one so that it returns parenOp(expr) if applicable,
	 * rather than always drilling into the argument.
	 * 
	 */
	public String[] findInnerParentheticalExpr(String str, int startIndex, int endIndex,
			ExpressionParser EP) {
		
		String subStr;
		int i,POendIndex,POstartIndex;
		boolean hasPO=false;
	
		String[] fromFP=this.findParenthetical(str, startIndex,endIndex);
		if(fromFP[0].equals("true")){
			System.out.println("Error in parenthetical expression");
			return fromFP;
		}
		else if(fromFP[1].equals("false")){
			// step 1: check outside paren for a parenOp
			ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
			POendIndex=Integer.parseInt(fromFP[2])-2; //where the parenOp should end
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
			// step 2: if parenOp detected, adjust the inner expression to include it
			if(hasPO){
				POstartIndex=POlist.get(i).start;
				fromFP[2]=String.valueOf(POstartIndex);
				fromFP[3]=String.valueOf(Integer.parseInt(fromFP[3])+1);
				fromFP[4]=str.substring(POstartIndex,Integer.parseInt(fromFP[3])+1); 
				fromFP[6]="true";
			}
			
			return fromFP;
		}
		else {
			subStr=fromFP[4];
			int newStartIndex=Integer.parseInt(fromFP[2]);
			int newEndIndex=Integer.parseInt(fromFP[3]);
			return this.findInnerParentheticalExpr(str,newStartIndex,newEndIndex,EP);
			}
	}
	
	// returns index of ')' in string
	public int findCloseParen(String str, int index) {
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
	public int findOpenParen(String str, int index) {
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
