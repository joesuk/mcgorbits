package boundaryBialgebra;

import java.util.*;

/**
* This class represents terms of the bracket with coeficients.
* Conjugacy classes are represented as a vector with interger entries.
* The linear combination is an arraylist.
* @author moira
*
*/

public class BTermsNumber {
	private ArrayList<BTermNumber> terms = new ArrayList<BTermNumber>();
	
	
	
	public ArrayList<BTermNumber> getTerms() {
		return terms;
	}


	public void setTerms(ArrayList<BTermNumber> terms) {
		this.terms = terms;
	}
	
	
	public String toMathematica(CL cl){
		String s = "{";
		
		for(int i=0;i<terms.size();i++){
			/*
			if(getCoef(i)==1){	s+="";

			}else if(getCoef(i)==-1){
				s+="-";
			}else{
				s+=getCoef(i);
			}
			*/
		    String aux =cl.toLetter(   terms.get(i).getV1());
			s +="\""+ aux+"\"";
			if(i!=terms.size()-1){
				s+= " , ";
			}
		}
		return s+"}";
	}

	

	public String toString(SurfaceWordOld sw){
		if (terms.size()==0) return "The bracket is zero";
		String answer ="";
		for(int h= 0 ; h < terms.size() ; h++ ){
			String str =terms.get(h).toString(sw);
		//	if (str != "zero")
			answer=answer+ str+" \n";
		}
		
		return answer;
	}
	
	public void addTerm(BTermNumber c){
		this.terms.add(c);
	}
	
	
	/**
	 * Adds terms, cancels classes with same conjugacy class, groups coefficients.
	 */
	public void  simplify() {
		for(int i = 0 ; i < terms.size()-1 ; i++){
			BTermNumber termi = terms.get(i);
			for(int j =i+1 ; j < terms.size() ; j++ ){
				BTermNumber termj = terms.get(j); 
				if(termi.vectorsEqual(termj)){
					int coef = (termi.getCoef()+termj.getCoef());
					termi.setCoef(coef);
					termj.setCoef(0);
				}
		
			}
 		}
		int h=0;
		while(h<terms.size()){
			if(terms.get(h).getCoef()==0) terms.remove(h);
			else h++;
		}
	}
	
	
	/**
	 * Returns the
	 * sum of hte absolute value of the coefficients.
	 * @return
	 */
	public int  addAbsVCoef() {
		
		int sum=0;
		for(int i= 0; i<terms.size(); i++)
			sum =sum+Math.abs( terms.get(i).getCoef());
			
		return sum;
	}
	
	
	/**
	 *This method check if htere are two terms with the same conjugacy class.
	 *Note that the output of teh bracket is already simplified.
	 * @return
	 */
	public boolean  checkCancelation() {
		for(int i = 0 ; i < terms.size()-1 ; i++){
			BTermNumber termi = terms.get(i);
			for(int j =i+1 ; j < terms.size() ; j++ ){
				BTermNumber termj = terms.get(j); 
				if(termi.vectorsEqual(termj)){
					return true;
				}
			}
		}
		return false;
	  	
	}

	

	
}
