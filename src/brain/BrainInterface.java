package brain;

public interface BrainInterface {
	
	public int isStringValid(String str); 		// -1 if valid, otherwise index of problematic character
	public int showOpenParen(String str); 		// -1 if all closed, otherwise index of open parenthesis
	public String stepStringIn(String str);		// string inside leftmost set of parentheses
	public String solveAll(String str); 		// returns solution in string form
	public String solveStep(String str); 		// performs next step (order of ops), re-parses and returns string

}
