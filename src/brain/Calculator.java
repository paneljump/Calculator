package brain;

import java.util.Collections;
import java.util.List;

public class Calculator implements BrainInterface {
	//private String[] operators={"!","^","*","/","+","-"};
	private String[] oper0={"!"};
	private String[] oper1={"^"};
	private String[] oper2={"*","/"};
	private String[] oper3={"+","-"};
	
	// NOTE: operators requiring parentheses are separated anticipating future 
	// development. At this time only parenOps is used.
	private String[] trigFns={"sin","cos","tan","csc","sec","cot"};
	private String[] invTrigFns={"asin","acos","atan","acsc","asec","acot"};
	private String[] logFns={"ln","log10"};
	private String[] parenOps; // concatenate all operaters with parentheses in constructor
	private String[][] operators;
	
	// for flushing illegal characters: note I have added single-char operators to this list
	private String permittedChars="1234567890.abcdefghijklmnopqrstuvwxyz()!^*/+-";
	// calculator mode: solve step-by-step, or fully resolve
	public boolean stepQ=false; 
	
	public ExpressionParser EP; // switch back to private after testing
	public Evaluator EV;
	public String currentString;
	public boolean inRadians=false;
	//change those to private after testing...?
	
	public Calculator(){
		EP=new ExpressionParser();
		//PC=new ParenChecker();
		EV=new Evaluator();
		
		// set parenOps (all operators before a parenthetical expression)
		int tl=this.trigFns.length;
		int il=this.invTrigFns.length;
		int ll=this.logFns.length;
		String[] pO=new String[tl+il+ll];
		for(int i=0;i<tl;i++)
			pO[i]=this.trigFns[i];
		for(int i=0;i<il;i++)
			pO[i+tl]=this.invTrigFns[i];
		for(int i=0;i<ll;i++)
			pO[i+tl+il]=this.logFns[i];
		this.parenOps=pO;
		
		// create and populate "operators", a jagged 2-d string array
		// operator precedence is represented by index of sub-array containing it
		operators = new String[5][];
		operators[0] = this.oper0;
		operators[1] = this.oper1;
		operators[2] = this.oper2;
		operators[3] = this.oper3;
		operators[4] = this.parenOps;
		
		EP.setOperatorsAndPermitted(this.operators, this.permittedChars, 
				this.trigFns, this.invTrigFns, this.logFns, this.parenOps);
		
	}
	
	// accessors
	public int setCurrentString(String str){
		this.currentString=str;
		return EP.checkValid(str);
	}
	
	// BrainInterface methods
	
	@Override
	public int isStringValid(String str) {
		return EP.checkValid(str);
	}

	@Override
	public int showOpenParen(String str) {
		//int index;
		//return ParenChecker.findOpenParen(str, index);
		return -1;
	}

	@Override
	public String stepStringIn(String str) {
		ParenObject paren = ParenChecker.findParenthetical(str, 0, str.length()-1);
		if(paren.errorQ==true)
			return paren.fullString; // if there is a parentheses error, return full string
		else
			return paren.innerString;
	}

	@Override
	public String solveAll(String str) {
		boolean temp = this.stepQ;
		this.stepQ=false;
		String out=this.NewDrillDownSolveAndSub(str);
		this.stepQ=temp;
		return out;
	}

	@Override
	public String solveStep(String str) {
		boolean temp = this.stepQ;
		this.stepQ=true;
		String out=this.NewDrillDownSolveAndSub(str);
		this.stepQ=temp;
		return out;
	}
	
	// generalized case, single step (NEEDS WORK!! catch errors as needed)
	public String NewDrillDownSolveAndSub(String str) {
		if(str.contains("Error"))
			return str;
		int i=EP.checkValid(str);
		if(i>-1)
			return "Error at character index "+i;
		String newExpr,newSub="",newStr="";
		
		// step 1: drill down to innermost paren, save start and end indices for substitution
		ParenObject subStr=ParenChecker.findInnerParentheticalExpr(str,0,str.length()-1,this.EP);
		if(subStr.errorQ)
			return "Error in searching for innermost parenthetical expression in string "+str;
		int startIndex=subStr.startIndex;
		int endIndex=subStr.endIndex;
		List<PlacedOperator> POlist = EP.findAllOperatorStartEnd(subStr.innerString);
		//System.out.println("Inner string is "+subStr.innerString);
		if(POlist.size()==0) {
			newStr = EP.parseParenSolution(str, startIndex,endIndex, subStr.innerString);
			List<PlacedOperator> newPOList = EP.findAllOperatorStartEnd(newStr);
			if(!ParenChecker.hasParen(newStr) && newPOList.size()==0) {
				return newStr;
			}
		}
		else{
			// step 2: get strings before and after, and operator information, to pass to Evaluator
			String[] prePost = getStringsForEvaluator(subStr.innerString, POlist);
			Collections.sort(POlist, new PlacedOperator.byRank());
			PlacedOperator PO = POlist.get(0);
			
			// step 3: solve the selected step
			SimpleAnswer s=EV.solveOp(prePost[0], prePost[1], PO.txt, PO.parenQ, inRadians);
			if(s.errorQ)
				return s.errorMsg;
			newExpr=s.s;
			
			// step 4: substitute into innermost substring
			int oldStart = PO.start-prePost[0].length();
			int oldEnd = PO.end+prePost[1].length();
			if(PO.parenQ)
				oldEnd+=2;
			newSub=EP.parseParenSolution(subStr.innerString, oldStart, oldEnd, newExpr);
			
			// step 5: substitute into full string
			newStr=EP.parseParenSolution(str, startIndex,endIndex, newSub);
			
		}
		
		if(this.stepQ)
			return newStr;
		else
			return NewDrillDownSolveAndSub(newStr);
		
	}
	
	// gets substrings before and after operator. Substrings are "" if not needed.
	private String[] getStringsForEvaluator(String str, List<PlacedOperator> POlist) {
		Collections.sort(POlist, new PlacedOperator.byRank());
		PlacedOperator PO = POlist.get(0);
		Collections.sort(POlist, new PlacedOperator.byIndex());
		int POindex = POlist.indexOf(PO);
		
		String before,after;
		String[] out = new String[2];
		
		// set "before" string
		if(PO.parenQ)
			before="";
		else {
			before=EP.findPreOperator(str, POlist, POindex).s;
		}
		
		// set "after" string
		if(PO.txt.equals("!"))
			after="";
		else {
			after=EP.findPostOperator(str, POlist, POindex).s;
		}
		out[0]=before;
		out[1]=after;
		return out;
	}
	
	

}
