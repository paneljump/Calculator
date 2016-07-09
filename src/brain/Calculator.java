package brain;

import java.util.ArrayList;

public class Calculator {
	private String[] operators={"!","^","*","/","+","-"};
	private String permittedChars="1234567890.abcdefghijklmnopqrstuvwxyz";
	
	// NOTE: operators requiring parentheses are separated anticipating future 
	// development. At this time only parenOps is used.
	private String[] trigFns={"sin","cos","tan","csc","sec","cot"};
	private String[] invTrigFns={"asin","acos","atan","acsc","asec","acot"};
	private String[] logFns={"ln","log10"};
	private String[] parenOps;
	
	// calculator mode: solve step-by-step, or fully resolve
	public boolean stepQ=false; 
	
	public ExpressionParser EP;
	public ParenChecker PC;
	public Evaluator EV;
	public String currentString;
	public boolean inRadians=false;
	//change those to private after testing...?
	
	public Calculator(){
		EP=new ExpressionParser();
		PC=new ParenChecker();
		EV=new Evaluator();
		
		// set operator list to include trig, inverse trig, more as needed
		int ol=this.operators.length;
		int tl=this.trigFns.length;
		int il=this.invTrigFns.length;
		int ll=this.logFns.length;
		String[] fullOps=new String[ol+tl+il+ll];
		for(int i=0;i<ol;i++)
			fullOps[i]=this.operators[i];
		for(int i=0;i<tl;i++)
			fullOps[i+ol]=this.trigFns[i];
		for(int i=0;i<il;i++)
			fullOps[i+ol+tl]=this.invTrigFns[i];
		for(int i=0;i<ll;i++)
			fullOps[i+ol+tl+il]=this.logFns[i];
		this.operators=fullOps;
		
		// set parenOps (all operators before a parenthetical expression)
		String[] pO=new String[tl+il+ll];
		for(int i=0;i<tl;i++)
			pO[i]=this.trigFns[i];
		for(int i=0;i<il;i++)
			pO[i+tl]=this.invTrigFns[i];
		for(int i=0;i<ll;i++)
			pO[i+tl+il]=this.logFns[i];
		this.parenOps=pO;
			
		String fullPermitted=permittedChars.substring(0);
		for(int i=0;i<operators.length;i++)
			fullPermitted=fullPermitted+operators[i];
		EP.setOperatorsAndPermitted(this.operators, fullPermitted, 
				this.trigFns, this.invTrigFns, this.logFns, this.parenOps);
		
	}
	
	public int setCurrentString(String str){
		this.currentString=str;
		return EP.checkValid(str);
	}
	
	public String solveAllOperations(String str){
		if(this.stepQ)
			return solveAllOperationsStep(str);
		else
			return solveAllOperationsFull(str);
	}
	
	public String solveAllOperationsStep(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		String endStr;
		if(POlist.size()==0)
			return str;
		else if(listHasOperator("!",POlist)){
			endStr= this.solveAllFactorials(str);
			//System.out.println("Step-solve factorial: "+endStr);
			return endStr;
		}
		else if(listHasOperator("^",POlist))
			return this.solveAllExponentials(str);
		else if(listHasOperator("*",POlist) || listHasOperator("/",POlist))
			return this.solveAllMultDiv(str);
		else if(listHasOperator("+",POlist) || listHasOperator("-",POlist))
			return this.solveAllAddSub(str);
		else
			return("Error in Calculator.solveAllOperationsStep("+str+")");
	}
	
	public boolean listHasOperator(String oper, ArrayList<PlacedOperator> POlist){
		boolean out=false;
		for(int i=0;i<POlist.size();i++)
			if(POlist.get(i).txt.equalsIgnoreCase(oper))
				out=true;
		return out;
	}
	
	public String solveAllOperationsFull(String str){
		String newStr;
		
		newStr=solveAllFactorials(str);
		//System.out.println("factorials solved: "+newStr);
		newStr=this.solveAllExponentials(newStr);
		//System.out.println("exponentials solved: "+newStr);
		newStr=solveAllMultDiv(newStr);
		//System.out.println("multdiv solved: "+newStr);
		newStr=solveAllAddSub(newStr);
		//System.out.println("addsub solved: "+newStr);
		return newStr;
	}
	
	public String DrillDownSolveAndSub(String str){
		if(str.contains("Error"))
			return str;
		String newExpr,newStr;
		String[] subStr=PC.findInnerParentheticalExpr(str,0,str.length()-1,this.EP);
		//System.out.println("string is "+str+"\nnext inner is "+subStr[4]);
		if(subStr[0].equalsIgnoreCase("true"))
			return "Error in searching for innermost parenthetical expression in string "+str;
		
		int startIndex=Integer.parseInt(subStr[2]);
		int endIndex=Integer.parseInt(subStr[3]);
		
		
		// if substring has a parenOp
		if(subStr[6].equalsIgnoreCase("true")){
			//System.out.println("entering parenOp case of DrillDOwn:");
			newExpr=this.fullSolveParenOp(subStr[4]);
			newStr=EP.parseParenSolution(str, startIndex,endIndex, newExpr);
			if(this.stepQ)
				return newStr;
			else
				return DrillDownSolveAndSub(newStr);
			
		}
		
		// if there are no operators left in substring (i.e. if operations completed)
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(subStr[4]);
		if(POlist.size()==0){
			//if also has no inner expression (i.e. completely simplified/solved)
			if(subStr[1].equalsIgnoreCase("false"))
				return str;
			else{
				return DrillDownSolveAndSub(EP.parseParenSolution(str, startIndex,endIndex, subStr[4]));
			}
			//this should reduce parentheses if they're nested and spit out appropriate error
		}
		
		newExpr=solveAllOperations(subStr[4]);
		if(newExpr.contains("Error")){
			return newExpr;
		}
		
		newStr=EP.parseParenSolution(str, startIndex,endIndex, newExpr);
		
		// if calculator is in Step mode, returns string after a single substitution
		if(this.stepQ)
			return newStr;
		else
			return DrillDownSolveAndSub(newStr);
			
	}
	
	// this takes string drilled down to a parenOp fn, from paren parser
	public String fullSolveParenOp(String str){
		//solution and whether fully simplified;
		String[] out=new String[2];
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		PlacedOperator p=POlist.get(0);
		String arg,solveArg,oper=p.txt;
		int startInd=p.end+2, endInd=str.length()-2;
		
		arg=str.substring(startInd,endInd+1);
		oper=POlist.get(0).txt;
		
		solveArg=DrillDownSolveAndSub(arg);
		POlist=EP.findAllOperatorStartEnd(solveArg);
		if(POlist.size()==0) {//if argument has been fully simplified, i.e. contains no operators
			SimpleAnswer s=EV.solveParenOp(oper, Double.parseDouble(solveArg), this.inRadians);
			out[1]=String.valueOf(s.d);
			return out[1];
		}
		else{
			out[0]="false";
			out[1]=oper+"("+solveArg+")";
			return out[1];
		}
		//return String.valueOf(s.d); //assuming solveParenOp places its answer in d
		
	}
	
	public SimpleAnswer solveFirstFactorial(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		String sub;
		SimpleAnswer s=new SimpleAnswer();
		int i;
		
		for(i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("!")){
				sub=EP.findPreOperator(str, POlist, i).s;
				s=EV.solveFactorial(sub);
				break;
			}
		}
		s.oldIndices[0]=EP.findPreOperator(str, POlist, i).oldIndices[0];
		s.oldIndices[1]=POlist.get(i).end;
		
		return s;
	}
	
	public String solveAllFactorials(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		
		boolean hasOperator=false;
		for(int i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("!"))
				hasOperator=true;
		}
		if(hasOperator==false)
			return str;
		
		SimpleAnswer s=solveFirstFactorial(str);
		if(s.errorQ)
			return s.errorMsg;
		String newStr=EP.parseInSimpleAnswer(str, s, "int");
		if(this.stepQ)
			return newStr;
		else
			return solveAllFactorials(newStr);
		
	}
	
	public SimpleAnswer solveFirstExponential(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		String sub1,sub2;
		SimpleAnswer s=new SimpleAnswer();
		int i;
		
		for(i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("^")){
				sub1=EP.findPreOperator(str, POlist, i).s;
				sub2=EP.findPostOperator(str, POlist, i).s;
				s=EV.solveExponential(Double.parseDouble(sub1),Double.parseDouble(sub2));
				break;
			}
		}
		s.oldIndices[0]=EP.findPreOperator(str, POlist, i).oldIndices[0];
		s.oldIndices[1]=EP.findPostOperator(str, POlist, i).oldIndices[1];
		
		return s;
	}
	
	public String solveAllExponentials(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		
		boolean hasOperator=false;
		for(int i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("^")){
				hasOperator=true;
			}
		}
		if(hasOperator==false)
			return str;
		
		SimpleAnswer s=solveFirstExponential(str);
		if(s.errorQ)
			return s.errorMsg;
		String newStr=EP.parseInSimpleAnswer(str, s, "double");
		
		if(this.stepQ)
			return newStr;
		else
			return solveAllExponentials(newStr);
		
	}
	
	public SimpleAnswer solveFirstMultDiv(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		String sub1,sub2;
		SimpleAnswer s=new SimpleAnswer();
		int i;
		
		for(i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("*") || POlist.get(i).txt.equals("/")){
				sub1=EP.findPreOperator(str, POlist, i).s;
				sub2=EP.findPostOperator(str, POlist, i).s;
				s=EV.solveMDAS(Double.parseDouble(sub1),Double.parseDouble(sub2),POlist.get(i).txt);
				break;
			}
		}
		s.oldIndices[0]=EP.findPreOperator(str, POlist, i).oldIndices[0];
		s.oldIndices[1]=EP.findPostOperator(str, POlist, i).oldIndices[1];
		
		return s;
	}
	
	public String solveAllMultDiv(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		
		boolean hasOperator=false;
		for(int i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("*") || POlist.get(i).txt.equals("/")){
				hasOperator=true;
			}
		}
		if(hasOperator==false)
			return str;
		
		SimpleAnswer s=solveFirstMultDiv(str);
		if(s.errorQ)
			return s.errorMsg;
		String newStr=EP.parseInSimpleAnswer(str, s, "double");
		
		if(this.stepQ)
			return newStr;
		else
			return solveAllMultDiv(newStr);
		
	}
	
	public SimpleAnswer solveFirstAddSub(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		String sub1,sub2;
		SimpleAnswer s=new SimpleAnswer();
		int i;
		
		for(i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("+") || POlist.get(i).txt.equals("-")){
				sub1=EP.findPreOperator(str, POlist, i).s;
				sub2=EP.findPostOperator(str, POlist, i).s;
				s=EV.solveMDAS(Double.parseDouble(sub1),Double.parseDouble(sub2),POlist.get(i).txt);
				break;
			}
		}
		s.oldIndices[0]=EP.findPreOperator(str, POlist, i).oldIndices[0];
		s.oldIndices[1]=EP.findPostOperator(str, POlist, i).oldIndices[1];
		
		return s;
	}
	
	public String solveAllAddSub(String str){
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(str);
		
		boolean hasOperator=false;
		for(int i=0;i<POlist.size();i++){
			if(POlist.get(i).txt.equals("+") || POlist.get(i).txt.equals("-")){
				hasOperator=true;
			}
		}
		if(hasOperator==false)
			return str;
		
		SimpleAnswer s=solveFirstAddSub(str);
		if(s.errorQ)
			return s.errorMsg;
		String newStr=EP.parseInSimpleAnswer(str, s, "double");
		
		if(this.stepQ)
			return newStr;
		else
			return solveAllAddSub(newStr);
		
	}
	

}
