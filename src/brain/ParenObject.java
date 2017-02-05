package brain;
/*
 * Used by the ParenChecker, and could be placed back in that file if it were reverted to package
 * protection rather than public protection.
 * (this class is public for testing purposes, but it should not matter if accessed indirectly 
 * from a Calculator object)
 * 
 */

public class ParenObject {
	public boolean errorQ;
	public boolean hasInner;
	public boolean parenOpQ;
	public int startIndex;
	public int endIndex;
	public String innerString;
	public String fullString;
	
	public ParenObject(String str, int startIndex, int endIndex) {
		this.errorQ=false;
		this.hasInner=false;
		this.parenOpQ=false;
		this.startIndex=startIndex;
		this.endIndex=endIndex;
		this.innerString=new String(str);
		this.fullString=str;
	}
	public ParenObject() {
		this.errorQ=false;
		this.hasInner=false;
		this.parenOpQ=false;
		this.startIndex=0;
	}
}
