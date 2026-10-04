package boundaryBialgebra;

import java.util.*;

import statics.Sta;
/**
* This class represents linear combinations of term of the bracket with a coeficient.
* Conjugacy classes are represented as string (of letters!)
* @author moira
*
*/
public class BTermsLetter {
	Set<String> bt = new HashSet<String>();
	Map<String, Integer> coefs = new HashMap<String, Integer>();
	
	/**
	 *This method  takes as parameter a word (thought as a cyclic word) and adds it to the list
	 *of terms of the bracket.
	 * @param w
	 * @param sign
	 */
	public void addTerm(String w, int sign){
		boolean test = true;
		//if the list of terms is not empty.
		if(!bt.isEmpty()){
			//first check whether there is a term conjugate
			for (Iterator<?> it = bt.iterator(); it.hasNext()&test;) {
				String word = (String) it.next();
				//System.out.println(word);
				//check if the w is already a term.
				if(Sta.areCyclicallyEqual(word, w)){
					test = false;
				//	System.out.println(word);
					w = word;
				}
			}
			//if the w was already in a term
			if(test == false){
				if(coefs.get(w)+sign ==0){
					bt.remove(w);
					coefs.remove(w);
				}else{
					coefs.put(w, sign+ coefs.get(w));	
				//	System.out.println(w+"2 ");
				}
			}else{//if there is no term
				//System.out.println(w+"e ");
				bt.add(w);
				coefs.put(w, sign);
			}
			
		}else{//if it is  empty.
			
			bt.add(w);
			coefs.put(w, sign);
		}
	}
	
	
	public String toString(){
		String answer = "";
		for (Iterator<?> it = bt.iterator(); it.hasNext();) {
			String word = (String) it.next();
			if(it.hasNext()){
				answer = answer + coefs.get(word) +"  "+word+" +\n";
			}else{
				answer = answer + coefs.get(word) +"  "+word+"\n";
			}
			
		}
		return answer;
	}
	
	public boolean isEmpty(){
		if(bt.isEmpty()) return true;
		else return false;
	}
	
}
