package boundary.bialgebra;
import boundary.*;
public class CobracketTerm {
	
	private int coef;
	private CyclicWord v1;
	private CyclicWord v2;
	
	public CobracketTerm(int c, CyclicWord vv1, CyclicWord vv2){
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
	

	public String toString(){
		 return ""+coef+"( "+ v1 +" o "+ v2 +" - "
								+v2+" o "+v1+" ) ";
	}
		
	
	public boolean vectorsEqual(CobracketTerm c){
	    return (this.getV1().equals(c.getV1())
	    	&  this.getV2().equals(c.getV2()));
	}
	
	public boolean vectorsEqualPermuted(CobracketTerm c){
	    return (this.getV1().equals(c.getV2())
	    	&  this.getV2().equals(c.getV1()));
	}
	
	

	public int getCoef() {
		return coef;
	}
;
	public void setCoef(int coef) {
		this.coef = coef;
	}

	public CyclicWord getV1() {
		return v1;
	}

	public void setV1(CyclicWord v1) {
		this.v1 = v1;
	}

	public CyclicWord getV2() {
		return v2;
	}

	public void setV2(CyclicWord v2) {
		this.v2 = v2;
	}
}