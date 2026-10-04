package boundaryBialgebra;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moira
 *
 */
public class SurfaceWordOld {
	private String surfaceWord;
	protected final  int slength;
	public   Map<Character, Integer> toNumber = new HashMap<Character, Integer>();
	public   Map<Integer, Character> toLetter= new HashMap<Integer, Character>();
    public   Map<Character, Character> barL= new HashMap<Character, Character>();
	public   Map<Integer, Integer> barN= new HashMap<Integer,Integer>();
	public   Map<Integer[], Integer> sr = new HashMap<Integer[], Integer>(); 


	
	/**
	 *This constructor will make computatons associated to the
	 *surface word sw.
	 *VERY IMPORTANT PRECONDITION: The string surface word must be a suruface
	 *word, that is, a word in whihc every letter and its bar have exactly only
	 *one appearance each.
	 * @param sw
	 */
	public SurfaceWordOld(String sw){
		surfaceWord = sw;
		slength = surfaceWord.length();
		//store the translations from letter to number and number to letter.
		for(int i = 0 ; i< slength ; i++){
			toLetter.put(i,surfaceWord.charAt(i));
			toNumber.put(surfaceWord.charAt(i),i);
		}
		//store the bar function for letters.
		
		for(int h = 65 ; h < 91 ; h++){
			 barL.put((char) h, (char) (h - 'A' + 'a'));
		}
		for(int h = 97 ; h < 123 ; h++){
			 barL.put((char) h, (char) (h - 'a' + 'A'));
		}
		
		//store the bar function for numbers
		for(int h =0 ; h < slength ; h++){
			barN.put(h, toNumber.get(barL.get(toLetter.get(h))));
		}
		
		

	}	
	
	
	/**
	 * Returns the bar of a word given in numbers.
	 * @param q
	 * @return
	 */
	public int[] barN(int[] q){
		int[] w = new int[q.length];
		for (int i = 0; i < q.length; i++) {
			w[i] = this.barN.get(q[q.length - i - 1]);
		}
		return w;
	}
	
	public int remainderModSurfaceLength(int j){
		j = j % this.slength;
		if (j >= 0)
			return j;
		else
			return this.slength + j;
	}
	
	
	public  boolean isCyclicallyReduced(int[] v){
		for(int h = 0 ; h < v.length-1 ; h++){
			if(v[h]==barN.get(v[h+1]) ) return false;
		}
		if(v[0]== barN.get(v[v.length-1])) return false;
		return true;
	}
	
	//gives the linearly reduced vector 
	//NOTE: It does not reduce cyclically.
	
	public int[] reduce(int[] v) {
		int count = 0;
		while (count + 1 < v.length) {
			if (v[count] == this.barN.get(v[count + 1])) {
				if (v.length == 2)
					return null;
				int[] w = new int[v.length - 2];
				System.arraycopy(v, 0, w, 0, count);
				System.arraycopy(v, count + 2, w, count, v.length - count - 2);
				v = w;
				if (count > 0)
					count--;
			} else count++;
		}
		return v;
	}
	
	/**
	 * @param s
	 * @return a linearly freely reduced string 
	 */
	public String reduce(String s){
		if(s == null || s.length()<2){
			return s;
		}
		int count = 0;
		while(count+1 <  s.length()){
			if(s.charAt(count) == this.barL.get(s.charAt(count+1))){
				if(s.length()==2){
					return null;
				}
				s = s.substring(0, count).concat(s.substring(count+2));
				if(count>0) count--;
			}else count++;
		}
		return s;
	}
	
	/**
	 * @param s
	 * @return a string which is freely reduced at the ends. 
	 */
	public String reduceEnds(String s){
		int count =0;
		if(s == null || s.length()<2){
			return s;
		}
		while(count < s.length()/2 && s.charAt(count)==this.barL.get(s.charAt(s.length()-count-1))){
			count++;
		}
		if (count==0) return s;
		if(count == s.length() /2 ) return null;
		return s.substring(count, s.length()-count);
	}
	

	
	/* this returns a "cyclically reduced vector"
*
*/ 
	public int[] cycRed(int[] v){
		int count = 0;
		v = this.reduce(v);
		while(count < v.length/2 & v[count] == barN.get(v[v.length-count-1]) ) count++;
		if(count>0) {
			int[] w = new int[v.length-2*count];
			System.arraycopy(v,count , w, 0, v.length-2*count);
			return w;
		}
		return v;
	}

	
	/**
	 * @param s a string
	 * @return the vector corresponding to this string.
	 */
	public int[] toNumber(String s){
		int[] answer = new int[s.length()];
		for(int i=0 ; i < s.length() ; i++){
			answer[i]= toNumber.get(s.charAt(i));
		}
		return answer;
	}
	
	/**
	 * REturns teh vector in letters.
	 * @param p
	 * @return
	 */
	public String toLetter(int[] p){
		String answer = "";
		if(p == null) return null;
		for(int i =0 ; i< p.length ; i++){
			answer=answer+ toLetter.get(p[i]);
		}
		return answer;
	}

	/**
	 *Retrurs the length of the sruface word.
	 * @return
	 */
	public int getSurfaceLength() {
		return slength;
	}

	public String getSurfaceWord() {
		return surfaceWord;
	}

	
	public String barS(String s){
		String answer="";
		for(int i=s.length()-1; i>=0 ; i--){
			answer= answer+barL.get(s.charAt(i));
		}
		return answer;
		
	}
}
