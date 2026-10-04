package boundary.bialgebra;

import java.util.*;

import boundary.*;

/**
 * This class represents terms fo cobracket with coeficienst.
 * Conjugacy classes are represented as a vector with interger entries.
 * @author moira
 *
 */


/**
 * @author moira
 *
 */
public class CobracketTerms {
	private ArrayList<CobracketTerm> terms = new ArrayList<CobracketTerm>();
	
	public int sumAbsCoef(){
		int pos=0;
		int abs_coef=0;
		while(pos < terms.size()){
			abs_coef = abs_coef + Math.abs(terms.get(pos).getCoef());
			pos++;
		}
		return abs_coef;
	}
	
	public ArrayList<CobracketTerm> getTerms() {
		return terms;
	}

	public int addCoef(){
		int number =0;
		for(int h =0 ; h< terms.size() ; h++){
			number = number + Math.abs(terms.get(h).getCoef());
		}
		
		return number;	
	}
	
	public void setTerms(ArrayList<CobracketTerm> terms) {
		this.terms = terms;
	}
	
	
	public String toString(){
		String answer ="";
		if(terms.size()==0) return "zero";
		
		for(int h= 0 ; h < terms.size() ; h++ ){
			answer=answer+ terms.get(h)+" + ";
		}
		return answer;
	}
	
	
	
	/**
	 *
	 * @param c
	 */
	public void addCobTerm(CobracketTerm c){
		//if there are no terms, add c to the (up to now empty) list.
		if(terms.size()==0){
			this.terms.add(c);
			
		}else{				
			int i=0;
			//find the first i such that the vectors at i or the symmetrized vectors at i
			//are equal to the vectors of c
			//The while loop stops in two ways: Either i=terms.size()-1 or the term or its symmtrized
			//are equal to c.
			while(i < terms.size()-1 
					& !terms.get(i).vectorsEqual(c)
					&!terms.get(i).vectorsEqual(c.pair())){
				i++;			
			}
	        //If i=terms.size()-1 , do i++; else, if term(i) is eual to c symetrized, cambiar
			// c por su symmetrized.
			if(!terms.get(i).vectorsEqual(c)){
				if(!terms.get(i).vectorsEqual(c.pair())){
					i++;
				}else{
					c=c.pair();
				}
			}
					
			//Now, "add" the term.			
			if(i<terms.size()){
	            //if at i the term is equla at c, compute the coeficient of c after adding c
				int coef = (terms.get(i).getCoef()+c.getCoef());
				//if the coefficient is zero, remove the term.
				if(coef==0){
					this.terms.remove(i);
				}else{
				terms.get(i).setCoef(coef);
				}
			}else{
			//IF c is not equal to the vectors in the list.	
			this.terms.add(c);
			}
		}
	}
	
	public boolean  checkCancelation() {
		for(int i = 0 ; i < terms.size()-1 ; i++){
			CobracketTerm termi = terms.get(i);
			for(int j =i+1 ; j < terms.size() ; j++ ){
				CobracketTerm termj = terms.get(j); 
				if(termi.vectorsEqual(termj)){
					return true;
				}
			}
		}
		return false;
	  	
	}
	public String findEqualLongWords(SurfaceWord sw){
		String answer ="";
		for(int i = 0 ; i < terms.size()-1 ; i++){
			CobracketTerm termi = terms.get(i);
			for(int j =i+1 ; j < terms.size() ; j++ ){
				CobracketTerm termj = terms.get(j); 
				boolean auxi = termi.getV1().length() <= termi.getV2().length();
				boolean auxj = termj.getV1().length() <= termj.getV2().length();
				boolean common=false;
				if(auxi & auxj){
					 common=termi.getV2().equals(termj.getV2());
				   
				}else if(auxi & !auxj){
					 common=termi.getV2().equals(termj.getV1());
					 
				}else if(!auxi & auxj){
					 common=termi.getV1().equals(termj.getV2());
				}else if(!auxi & !auxj){
					
					 common=termi.getV1().equals(termj.getV1());
					// 
				}else common=false;
				
					 
				if(common){
					System.out.println("TEST 2 "+termi+" "+termj+" "+common);
					answer = termi+" has common factor with "+termj+"\n";
				}
			}
		}	
		return answer;
	}
	
	public String findEqualWords(SurfaceWord sw){
		String answer ="";
		for(int i = 0 ; i < terms.size()-1 ; i++){
			CobracketTerm termi = terms.get(i);
			for(int j =i+1 ; j < terms.size() ; j++ ){
				CobracketTerm termj = terms.get(j); 
				boolean common=termi.getV1().equals(termj.getV1())
					||termi.getV1().equals(termj.getV2())
					||termi.getV2().equals(termj.getV1())
					||termi.getV2().equals(termj.getV2());
				if( common){
					
					answer = termi+" has common factor with "+termj+"\n";
				}
				
				
			}
		}
		
		
		return answer;
	}

	
}
