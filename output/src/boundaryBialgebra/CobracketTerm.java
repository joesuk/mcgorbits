package boundaryBialgebra;

import statics.Sta;

/**
* This class represents a term of the cobracket with a coeficient.
* Conjugacy classes are represented as a vector with interger entries.
* @author moira
*
*/

public class CobracketTerm {
	
	private int coef;
	private int[] v1;
	private int[] v2;
	
	public CobracketTerm(int c, int[] vv1, int[] vv2){
		coef=c;
		v1=vv1;
		v2=vv2;
	}
	
	/**
	 * This gives the term with the tensor product interchanging factors and the sign inverted.
	 * @return
	 */
	public CobracketTerm pair(){
		return new CobracketTerm(-this.coef,this.v2,this.v1);
				
	}
	

	public String toString(SurfaceWordOld sw){
		 return ""+coef+"( "+ sw.toLetter(v1)+" o "+sw.toLetter(v2)+" - "
								+sw.toLetter(v2)+" o "+sw.toLetter(v1)+" ) ";
	}
	

		
	
	public boolean vectorsEqual(CobracketTerm c){
	    return Sta.areCyclicallyEqual(this.getV1(),c.getV1()) 
	    	&  Sta.areCyclicallyEqual(this.getV2(),c.getV2());
	}
	
	public boolean vectorsEqualPermuted(CobracketTerm c){
	    return Sta.areCyclicallyEqual(this.getV2(),c.getV1()) 
	    	&  Sta.areCyclicallyEqual(this.getV1(),c.getV2());
	}
	
	

	public int getCoef() {
		return coef;
	}
;
	public void setCoef(int coef) {
		this.coef = coef;
	}

	public int[] getV1() {
		return v1;
	}

	public void setV1(int[] v1) {
		this.v1 = v1;
	}

	public int[] getV2() {
		return v2;
	}

	public void setV2(int[] v2) {
		this.v2 = v2;
	}

	
	
	
}
