package boundaryBialgebra;

import statics.Sta;

/**
 * This class represents a term of the bracket with a coeficient.
 * Conjugacy classes are represented as a vector with interger entries.
 * @author moira
 *
 */
public class BTermNumber {
	
	private int coef;
	private int[] v1;
	
	
	public BTermNumber(int c, int[] vv1){
		coef=c;
		v1=vv1;
		
	}
	
	public String toString(SurfaceWordOld sw){
		if (coef ==0) return "";
		else return ""+coef+" "+sw.toLetter(v1);
	}
	
	public boolean vectorsEqual(BTermNumber c){
	    return Sta.areCyclicallyEqual(this.getV1(),c.getV1()) ;
	}
	
	

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public int[] getV1() {
		return v1;
	}

	public void setV1(int[] v1) {
		this.v1 = v1;
	}



}
