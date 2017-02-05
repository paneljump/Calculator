package brain;

/* Receives {double,operator,double,startIndex,intIndex} from EP.stringToReplace
 * Generally returns SimpleAnswer (has errorTF, int, double, float, whatever.)
 */

import java.lang.Math;
import java.math.BigDecimal;

//import java.math.*;
import brain.SimpleAnswer;

public class Evaluator {
	
	public SimpleAnswer solveOp(String before, String after, String opTxt, boolean parenOpQ, boolean inRadians) {
		SimpleAnswer s = new SimpleAnswer();
		double bD,aD;
		try{
			bD=Double.parseDouble(before);
		}
		catch(Exception e) { bD=0.0;}
		try{
			aD=Double.parseDouble(after);
		}
		catch(Exception e) { aD=0.0;}

		if( opTxt.equals("!") )
			s=solveFactorial(before);
		else if( opTxt.equals("^") )
			s=solveExponential(bD,aD);
		else if( parenOpQ)
			s=solveParenOp(opTxt, aD, inRadians);
		else
			s=this.solveMDAS(bD, aD,opTxt);
		
		return s;
	}
	
	public SimpleAnswer solveFactorial(String str){ //from in[0]
		int outNum;
		double d=Double.parseDouble(str);
		int num=(int) d;
		SimpleAnswer s=new SimpleAnswer();
		
		try{
			outNum=1;
			for(int i=2;i<=num;i++)
				outNum=outNum*i;
			s.i= outNum;
			s.s= String.valueOf(outNum);
		}
		catch(Exception e){
			s.errorQ=true;
			s.errorMsg="Error with factorial";
		}
		return s;
	}
	
	public SimpleAnswer solveExponential(double baseNum, double pwr){
		double outNum;
		SimpleAnswer s=new SimpleAnswer();
		s.errorQ=false;
		// this needs an error checker
		outNum=Math.pow(baseNum,pwr);
		s.d=outNum;
		s.s=String.valueOf(outNum);
		return s;
	}
	
	public SimpleAnswer solveMDAS(double one, double two, String sign){
		SimpleAnswer s=new SimpleAnswer();
		s.errorQ=false;
		
		if(sign.equals("/") && two==0.0){
			s.errorQ=true;
			s.errorMsg="Error: cannot divide by 0";
			return s;
		}
		else if(sign.equals("*"))
			s.d=one*two;
		else if(sign.equals("/"))
			s.d=one/two;
		else if(sign.equals("+"))
			s.d=one+two;
		else if(sign.equals("-"))
			s.d=one-two;
		else
			s.errorQ=true;
		
		if(!s.errorQ)
			s.s=String.valueOf(s.d);
		return s;
		
	}
	
	// assuming arg in radians for now, but designed for easy update when I implement mode
	public SimpleAnswer solveParenOp(String trigFn, double arg, boolean inRadians){
		double radArg;
		if(inRadians)
			radArg=arg;
		else
			radArg=Math.toRadians(arg);
		
		SimpleAnswer s=new SimpleAnswer();
		s.errorQ=false;
		double d;
		
		try{
			if(trigFn.equalsIgnoreCase("sin"))
				d=Math.sin(radArg);
			else if(trigFn.equalsIgnoreCase("cos"))
				d=Math.cos(radArg);
			else if(trigFn.equalsIgnoreCase("tan")) {
				d=Math.tan(radArg);
				//System.out.println(d);
			}
			else if(trigFn.equalsIgnoreCase("csc"))
				d=1.0/Math.sin(radArg);
			else if(trigFn.equalsIgnoreCase("sec"))
				d=1.0/Math.cos(radArg);
			else if(trigFn.equalsIgnoreCase("cot"))
				d=1.0/Math.tan(radArg);
			else if(trigFn.equalsIgnoreCase("asin")){
				d=Math.asin(arg);
				if(!inRadians)
					d=Math.toDegrees(d);
			}
			else if(trigFn.equalsIgnoreCase("acos")){
				d=Math.acos(arg);
				if(!inRadians)
					d=Math.toDegrees(d);
			}
			else if(trigFn.equalsIgnoreCase("atan")){
				d=Math.atan(arg);
				if(!inRadians)
					d=Math.toDegrees(d);
			}
			else if(trigFn.equalsIgnoreCase("acsc")){
				d=Math.asin(1.0/arg);
			}
			else if(trigFn.equalsIgnoreCase("asec")){
				d=Math.acos(1.0/arg);
				if(!inRadians)
					d=Math.toDegrees(d);
			}
			else if(trigFn.equalsIgnoreCase("acot")){
				d=Math.atan(1.0/arg);
				if(!inRadians)
					d=Math.toDegrees(d);
			}
			else if(trigFn.equalsIgnoreCase("ln")) {
				d=Math.log(arg);
			}
				
			else if(trigFn.equalsIgnoreCase("log10"))
				d=Math.log10(arg);
			else throw new Exception(); // throwing generic exception for now
					
		
			s.d=d;
			s.s=new BigDecimal(d).toPlainString();
		}
		catch(Exception e){
			s.errorQ=true;
			s.errorMsg="Error in computation "+trigFn+"("+arg+")";
		}
		
		return s;
		
	}

}
