package brain;

/*
 * This is an object for passing information; it has no internal processing.
 * It makes room for multiple data types, mostly primitives.
 */

public class SimpleAnswer {
	public boolean errorQ;
	public double d;
	public int i;
	public float f;
	public String s;
	public String errorMsg;
	public int[] oldIndices;
	
	public SimpleAnswer(){
		oldIndices=new int[2];
	}

}
