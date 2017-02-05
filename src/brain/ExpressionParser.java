package brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brain.PlacedOperator;

/* 1. makes sure string is valid expression: permitted chars, match paren,...?
 * 2. 
 * 
 */
public class ExpressionParser {
	//String oldOperators="!^*/+-"; // string of non-() operators in order of precedence
	String[][] rankedOperators;		// gets from Calculator
	String permittedChars; 		// gets from Calculator
	//private String currentString;
	private String[] trigFns;
	private String[] invTrigFns;
	private String[] logFns;
	public String[] parenOps;
	//private boolean isValid;
	boolean hasInner;
	int[] parenIndex;
	
	String[] errorMessages={"Error: parentheses cannot be parsed"};
	boolean[] errorStatus={false};
	
	public ExpressionParser(){}
	
	public void setOperatorsAndPermitted(String[][] ops, String perm, 
			String[] tf, String[] itf, String[] lf, String[] pO) {
		this.rankedOperators=ops;
		this.permittedChars=perm;
		this.trigFns=tf;
		this.invTrigFns=itf;
		this.logFns=lf;
		this.parenOps=pO;
	}
	
	private int characterFlush(String str){
		for(int i=0;i<str.length();i++){
			if(this.permittedChars.indexOf(str.charAt(i))==-1) {
				System.out.println("invalid character at " + i);
				return i;
			}
		}
		return -1;
	}
	
	public int checkValid(String str){ //confirms that string is a valid expression
		int i=-1;
		
		// return index of first invalid character
		i=this.characterFlush(str);
		if(i>-1){
			//this.isValid=false;
			return i;
		}
		
		// return index of first unmatched '(' or ')'
		i=ParenChecker.checkParen(str);
		if(i>-1){
			return i; 
		}
		
		// NOTE: This isn't built yet, but I'd like it to 
		List<PlacedOperator> placedOps = findAllOperatorStartEnd(str);
		
		return i;
	}
	
	
	// these create PlacedOperators for a string
	
	public int[] findOperatorStartEnd(String str, String oper){
		int[] out=new int[2];
		int operStart=-1;
		
		operStart=str.indexOf(oper);
		out[0]=operStart;
		if(operStart==-1)
			out[1]=-1;
		else
			out[1]=operStart+oper.length()-1;
		
		return out;
	}
	
	public void displayOperatorList(List<PlacedOperator> list){
		int len=list.size();
		for(int i=0;i<len;i++){
			System.out.println(list.get(i).txt + "   " + list.get(i).rank + "   " + 
					list.get(i).start + "   " + list.get(i).end+"   "+list.get(i).parenQ);
		}
	}
	
	public void displayPrePost(String str, List<PlacedOperator> list, int index){
		String pre,post,oper;
		pre=this.findPreOperator(str, list, index).s;
		post=this.findPostOperator(str, list, index).s;
		oper=list.get(index).txt;
		System.out.println(pre+oper+post);
	}
	
	public void displayOperators(List<PlacedOperator> POlist) {
		for(int i=0;i<POlist.size();i++) {
			PlacedOperator PO=POlist.get(i);
			System.out.println(PO.txt+"   "+PO.rank+"   "+PO.start+"   "+PO.end+"   "+PO.parenQ);
		}
	}
	
	public List<PlacedOperator> findAllOperatorStartEnd(String str){
		List<PlacedOperator> outS=new ArrayList<PlacedOperator>();
		int[] out=new int[2];
		
		PlacedOperator p;
		
		int nextStringStart,totalOffset, len=str.length(),operIndex=0;
		String subStr, oper;
		
		// flatten operators for ease in iteration
		List<String> fO = new ArrayList<String>();
		for(int i=0;i<5;i++){
			for(int j=0;j<this.rankedOperators[i].length;j++)
				fO.add(rankedOperators[i][j]);
		}
		String[] flatOps = new String[fO.size()];
		flatOps = fO.toArray(flatOps);
		
		// for all operators in flatOps
		for(operIndex=0;operIndex<flatOps.length;operIndex++){
			nextStringStart=totalOffset=0;
			out[0]=0;
			oper=flatOps[operIndex];
			subStr=str.substring(0);
			while(out[0]>-1 && totalOffset<len){
			    out=findOperatorStartEnd(subStr,oper);
			    if(out[0]>-1){
				    nextStringStart=out[1]+1; 
				    p=new PlacedOperator(oper,rankedOperators,out[0]+totalOffset,out[1]+totalOffset);
					outS.add(p);
				    totalOffset += nextStringStart;
				    subStr=subStr.substring(nextStringStart);
			    }
		    }
		}
		Collections.sort(outS, new PlacedOperator.byIndex() ); // in order of occurrence in string
		for(int i=0;i<outS.size();i++){
			if(outS.get(i).txt.equals("-")){
				if(!this.isDashOperator(str, outS, i)){
					outS.remove(i);
					i--;
				}
			}
		}
		flushOperators(outS);
		return outS;
		
	}
	
	/* valid operators will not overlap. This removes things like "tan" detected
	 * inside of "atan". NOTE: do not create shorter operators that match a 
	 * long_operator.substring(0,x) because longer operators will be flushed.
	 */
	public void flushOperators(List<PlacedOperator> POlist){
		Collections.sort(POlist, new PlacedOperator.byIndex() );
		int i=0,j=1,end1,start2;
		while(j<POlist.size()){
			end1=POlist.get(i).end;
			start2=POlist.get(j).start;
			if(end1>=start2)
				POlist.remove(j);
			else{
				i++;
				j++;
			}
		}
	}
	
	// '-' can be operator or negative sign. "index" is index of sign in list, not in string
	public boolean isDashOperator(String str, List<PlacedOperator> list, int index){
		PlacedOperator p=list.get(index);
		
		if(p.start==0)
			return false;
		else if(str.charAt(p.start-1)=='(') // in case haven't drilled below parentheses
			return false;
		else if(index>0 && p.start-1==list.get(index-1).end)
			return false;
		else{
			return true;
		}
	}
	
	// this next set of functions 
	
	/* This takes string, list of placed operators, and index (within list) 
	 * of operator in question and returns the indices of substring of number 
	 * after operator AND number after operator (in string form) within a 
	 * SimpleAnswer object
	 */
	public SimpleAnswer findPostOperator(String str, List<PlacedOperator> list, int index){
		SimpleAnswer s=new SimpleAnswer();
		
		if(list.get(index).parenQ) {
			int[] out=new int[2];
			int len=list.size();
			out[0]=list.get(index).end+2; // make room for parentheses
			String sub2 = str.substring(list.get(index).end+1); // substring starting after parenOp
			int endParen = ParenChecker.findCloseParen(sub2, 0) + out[0]-1; // index of closed paren
			out[1] = endParen-1;
			s.s=str.substring(out[0], out[1]+1);
			return s;
		}
		else {
			int[] out=new int[2];
			int len=list.size();
			out[0]=list.get(index).end+1;
			if(index==len-1)
				out[1]=str.length()-1;
			else
				out[1]=list.get(index+1).start-1;
		
			s.s=str.substring(out[0],out[1]+1);
			s.oldIndices=out;
		}
		return s;
	}
	
	public SimpleAnswer findPreOperator(String str, List<PlacedOperator> list, int index){
		SimpleAnswer s=new SimpleAnswer();
		int[] out=new int[2];
		if(index==0)
			out[0]=0;
		else
			out[0]=list.get(index-1).end+1;
		out[1]=list.get(index).start-1;
		
		s.s=str.substring(out[0],out[1]+1);
		s.oldIndices=out;
		return s;
	}
	
	// These parse answers back in
	
	public String parseInSimpleAnswer(String str, SimpleAnswer s, String dataType){
		String pre=str.substring(0,s.oldIndices[0]);
		String post=str.substring(s.oldIndices[1]+1);
		String newBit;
		if(dataType.equals("int"))
			newBit=String.valueOf(s.i);
		else if(dataType.equals("float"))
			newBit=String.valueOf(s.f);
		else // current default: (dataType.equals("double"))
			newBit=String.valueOf(s.d);
		return pre+newBit+post;
	}
	
	public String parseParenSolution(String expr, int oldStartIndex, int oldEndIndex, 
			String answer) {
		// decide whether I keep parentheses:
		
		String front;//=expr.substring(0,oldStartIndex);
		String back;//=expr.substring(oldEndIndex+1);
		List<PlacedOperator> POlist=this.findAllOperatorStartEnd(answer);
		
		boolean dropParenQ=false;
		if(oldStartIndex==0 || oldEndIndex>=expr.length()-1)
			dropParenQ=false;
		else if(expr.charAt(oldStartIndex-1)=='(' && expr.charAt(oldEndIndex+1)==')' 
				&& POlist.size()==0)
			dropParenQ=true;
		
		// if inside a parenOp, don't drop parentheses
		if(ParenChecker.insideParenOpIndex(expr, oldStartIndex, this)>-1)
			dropParenQ=false;
		
		if(oldStartIndex<=0) //oldStartIndex should never be negative but this takes care of it
			front="";
		else if(dropParenQ){ //>0 so won't go out of bounds
			if(oldStartIndex<=1)
				front="";
			else
				front=expr.substring(0,oldStartIndex-1);
		}
		else {
			front=expr.substring(0,oldStartIndex); 
		}
		
		
		if(oldEndIndex>=expr.length()-1)
			back="";
		else if(dropParenQ){
			if(oldEndIndex>=expr.length()-2)
				back="";
			else
				back=expr.substring(oldEndIndex+2);
		}
		else {
			back=expr.substring(oldEndIndex+1);
		}
		
		return front+answer+back;
	}

}
