package boundary.bialgebra;

import java.util.*;

import boundary.*;

/**
 * This class represents a term of the bracket with a coeficient.
 * Conjugacy classes are represented as a vector with interger entries.
 * @author moira
 *
 */
public class BTerms {
	private ArrayList<CyclicWord> terms = new ArrayList<CyclicWord>();
	private ArrayList<Integer> coefficients = new ArrayList<Integer>();
	public ArrayList<CyclicWord> getTerms() {
		return terms;
	}

	public void setTerms(ArrayList<CyclicWord> terms) {
		this.terms = terms;
	}

	public String toString(){
		String s = "";
		
		for(int i=0;i<size();i++){
			if(getCoef(i)==1){
				s+="";

			}else if(getCoef(i)==-1){
				s+="-";
			}else{
				s+=getCoef(i);
			}
			
			s += getTerm(i);
			if(i!=size()-1){
				s+= " + ";
			}
		}
		return s;
	}
	
	public void addTerm(CyclicWord c){
		this.terms.add(c);
		this.coefficients.add(1);
	}
	public void addTerm(CyclicWord c, int co){
		this.terms.add(c);
		this.coefficients.add(co);
	}
	public CyclicWord getTerm(int i){
		return terms.get(i);
	}
	public CyclicWord setTerm(int i,CyclicWord t){
		return terms.set(i,t);
	}
	public int getCoef(int i){
		return coefficients.get(i);
	}
	public int setCoef(int i, int co){
		return coefficients.set(i, co);
	}
	public void removeTerm(int i){
		terms.remove(i);
		coefficients.remove(i);
	}
	
	/**
	 * Adds terms, cancels classes with same conjugacy class, groups coefficients.
	 */
	public void  simplify() {
		for(int i = 0 ; i < size() ; i++){
			for(int j =i+1 ; j < size() ; j++ ){
				if(getTerm(i).equals(getTerm(j))){
					setCoef(i, getCoef(i)+getCoef(j));
					setCoef(j, 0);
				}
			}
 		}
		int i=0;
		while(i<terms.size()){
			if(getCoef(i)==0){
				removeTerm(i);
				
			}
			else i++;
		}
	}
	
	public int size(){
		return terms.size();
	}
	/**
	 * Returns the
	 * sum of hte absolute value of the coefficients.
	 * @return
	 */
	public int  addAbsVCoef() {
		
		int sum=0;
		for(int i= 0; i<terms.size(); i++)
			sum =sum+Math.abs(getCoef(i));
			
		return sum;
	}
	
	
	/**
	 *This method check if htere are two terms with the same conjugacy class.
	 *Note that the output of teh bracket is already simplified.
	 * @return
	 */
	public boolean  checkCancelation() {
		for(int i = 0 ; i < terms.size(); i++){
			
			for(int j =i+1 ; j < terms.size() ; j++ ){
				if(getTerm(i).equals(getTerm(j))){
					return true;
				}
			}
		}
		return false;
	  	
	}
}
