package test;
import brain.*;

import java.util.ArrayList;
import java.util.Scanner;

public class testMe {

	public static void main(String[] args) {
		Scanner stuff=new Scanner(System.in);
		String testLine;
		//boolean carryOn=true;
		int index;
		
		
		/* It's 6/28 and I'm testing some new stuff here. 
		 * 
		 */
		
		System.out.println("Testing new operator finder for string...");
		String tempStr="2+2*-3-4+2--6", pre, post;
		System.out.println("String is "+ tempStr);
		
		Calculator C=new Calculator();
		ExpressionParser EP=C.EP;
		ParenChecker PC=C.PC;
		String oper="-";
		int[] out;
		out=EP.findOperatorStartEnd(tempStr, oper);
		System.out.println("Operator range is "+out[0]+" "+out[1]);
		
		tempStr="!acos(4)-3*/";
		System.out.println("String is "+ tempStr);
		ArrayList<PlacedOperator> POlist=EP.findAllOperatorStartEnd(tempStr);
		EP.displayOperatorList(POlist);
		int i=4; //index of operator I'm looking at
		//EP.displayPrePost(tempStr, POlist, i);
		System.exit(0);
		String testNewInnerParen="4+cos((6+3!))/4-(4*3^2-1/(8+4!))";
		String newInner=PC.findInnerParentheticalExpr(testNewInnerParen, 0,
				testNewInnerParen.length()-1, EP)[4];
		System.out.println(testNewInnerParen+"\nfirst inner is "+newInner);
		//quit program because stuff below will be broken...
		//System.exit(0);
		
		String testFactorials="6+3!-2*5+6!-1";
		String tfAnswer=C.solveAllFactorials(testFactorials);
		System.out.println(tfAnswer);
		
		String testExponentials="6+3^4-4*3^2-1";
		String teAnswer=C.solveAllExponentials(testExponentials);
		System.out.println(teAnswer);
		
		String testMultDiv="6+3/4-4*3^2-1/8";
		String tmdAnswer=C.solveAllMultDiv(testMultDiv);
		System.out.println(testMultDiv+"\n"+tmdAnswer);
		
		String testAddSub="6+3/4-4*3^2-1/8";
		String tasAnswer=C.solveAllAddSub(testAddSub);
		System.out.println(testAddSub+"\n"+tasAnswer);
		
		String testAllOperations="6+3!/4-4*3^2-1/8+4!";
		System.out.println(testAllOperations);
		String taoAnswer=C.solveAllOperations(testAllOperations);
		System.out.println(taoAnswer);
		
		System.out.println("\nApparently we have an error here, let's have a look...");
		String testDrillDown="(6+3!)/4-(4*3^2-1/(8+4!))";
		System.out.println(testDrillDown);
		String tddAnswer=C.DrillDownSolveAndSub(testDrillDown);
		System.out.println(tddAnswer);
		System.out.println("solving by steps...");
		C.stepQ=true;
		tddAnswer=testDrillDown;
		do{
			POlist=EP.findAllOperatorStartEnd(tddAnswer);
			//EP.displayOperatorList(POlist);
			tddAnswer=C.DrillDownSolveAndSub(tddAnswer);
			System.out.println(tddAnswer);
		}
		while(POlist.size()>0);
		//System.exit(0);
		
		C.stepQ=false;
		String testTrig="sin(6+cos(3!))/4-(4*3^2-1/cos(8+4!))";
		System.out.println("\ntesting trig: "+testTrig);
		//C.solveFirstTrig(testTrig); //this will print arg of first trig fn
		System.out.println("\nsolving all trig...");
		//C.solveAllOperations("0.6264457718885821/4-(4*3^2-1/0.8342233605065102)");
		String trigAnswer=C.DrillDownSolveAndSub(testTrig);
		System.out.println(trigAnswer);
		//System.exit(0);
		System.out.println("solving all trig by steps...");
		C.stepQ=true;
		trigAnswer=testTrig;
		do{
			POlist=EP.findAllOperatorStartEnd(trigAnswer);
			//EP.displayOperatorList(POlist);
			trigAnswer=C.DrillDownSolveAndSub(trigAnswer);
			System.out.println(trigAnswer);
		}
		while(POlist.size()>0);
		
		
		/*
		System.out.println("testing inner parenthetical parser ('quit' to end)...");
		ParenChecker PC = new ParenChecker();
		while(true){
			testLine=stuff.nextLine();
			if(testLine.equalsIgnoreCase("quit"))
				break;
			index=PC.checkParen(testLine);
			if(index==-1){
				System.out.println("Parentheses match");
				testLine=PC.findInnerParentheticalExpr(testLine);
				System.out.println("Answer is: "+testLine);
			}
			else
				System.out.println("Error: bad parenthesis at index "+index);
			
			
		}
		*/
		
		//int operIndex;
		/*
		System.out.println("testing isDashOperator");
		ExpressionParser e=new ExpressionParser();
		testLine=stuff.nextLine();
		operIndex=Integer.parseInt(stuff.nextLine());
		System.out.println(e.isDashOperator(testLine, operIndex));
		*/
		
		/*
		System.out.println("testing stringToReplace ('quit' to end)...");
		ExpressionParser EP=new ExpressionParser();
		String[] outString;
		
		while(true){
			//System.out.println("type a line...");
			testLine=stuff.nextLine();
			//System.out.println("no, type an integer...");
			if(testLine.equalsIgnoreCase("quit")){
				break;
			}
			EP.setString(testLine);
			operIndex=Integer.parseInt(stuff.nextLine());
			outString=EP.stringToReplace(testLine, operIndex);
			for(int i=0;i<5;i++)
				System.out.println(outString[i]);
		}
		stuff.close();
		*/

	}

}
