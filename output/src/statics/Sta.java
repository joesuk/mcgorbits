package statics;



public class Sta {
	//public   Map<String, String	> inv = new HashMap<String, String>(); 

	

	
	/**returns a vector of the length of the word with the coordinates 
	equal to the failure function
	**/
	public static int[] failure(String s){
		int[] f= new int[s.length()];
		f[0]=-1;
		int i;
		for(int j=1; j< s.length();j++){
			i=f[j-1];
			while (s.charAt(j) != s.charAt(i+1) &  i>0){ 
				i=f[i];			
			}
		if ( s.charAt(j) != s.charAt(i+1) & i==0 ) {
				f[j]=0;
			}		
		else f[j]=i+1;
		//System.out.println(f[j]);
		}
		return f;
	}
	
	
	
	public static void printVector(int[] v){
		 for(int t : v)
			System.out.print(t);
	}
	
	
	/**
	 *  Checks whehter the strings are cyclically conjuate
	 * @param s1 String 
	 * @param s2 String
	 * @return true o false
	 */
	public static boolean areCyclicallyEqual(String s1, String s2){
		if(s1.length()!=s2.length()) return false;
        String ds2 = s2.concat(s2);
		if(ds2.indexOf(s1)>=0) return true;
		return false;
	}
	
	
	public static int rem(int j, int p) {
		j = j % p;
		if (j >= 0)
			return j;
		else
			return p + j;
	}
	
	
	/**
	 * Returns k such that v=w^k with w primitive.
	 * 
	 * @param v
	 * @return int
	 */
	public static int largestPower(int[] v) {
		// check first the pure powers.
		int count = 1;
		while (count < v.length) {
			if (v[0] == v[count])
				count++;
			else
				count = 10000;
		}

		if (count == v.length)
			return v.length;
//now check non pure powers
		for (int i = v.length - 1; i >= 2; i--) {
			// We will see if v = w^i, where length(w)=period.
			if (v.length % i == 0) {
				int period = v.length / i;
				int d = 1;
				int r = 0;
				boolean isAMultiple = true;
				while (isAMultiple) {
					isAMultiple = (v[r] == v[d * period + r]);
					if (r < period - 1)
						r++;
					else if (d < i - 1) {
						r = 0;
						d++;
					} else if (isAMultiple) {
						return i;
					}
				}
			}
		}
		return 1;
	}
	
	
	/**
	 * Returns k such that v=w^k with w primitive.
	 * 
	 * @param v
	 * @return int
	 */
	public static int largestPower(String v) {
		// check first the pure powers.
		int count = 1;
		while (count < v.length()) {
			if (v.charAt(0) == v.charAt(count))
				count++;
			else
				count = 10000;
		}

		if (count == v.length())
			return v.length();
//now check non pure powers
		for (int i = v.length() - 1; i >= 2; i--) {
			// We will see if v = w^i, where length(w)=period.
			if (v.length() % i == 0) {
				int period = v.length() / i;
				int d = 1;
				int r = 0;
				boolean isAMultiple = true;
				while (isAMultiple) {
					isAMultiple = (v.charAt(r) == v.charAt(d * period + r));
					if (r < period - 1)
						r++;
					else if (d < i - 1) {
						r = 0;
						d++;
					} else if (isAMultiple) {
						return i;
					}
				}
			}
		}
		return 1;
	}
	
	public static boolean isPrimitive(int[] v) {
		for (int i = 2; i <= v.length; i++) {
			// We will see if v = w^i, where length(w)=period.
			if (v.length % i == 0) {
				int period = v.length / i;
				int d = 1;
				int r = 0;
				boolean isAMultiple = true;
				while (isAMultiple) {
					isAMultiple = (v[r] == v[d * period + r]);
					if (r < period - 1)
						r++;
					else if (d < i - 1) {
						r = 0;
						d++;
					} else if (isAMultiple) {
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * Return the char enter as parameter capitalized if it is minuscule, 
	 * and the char in minuscule 
	 * if it is a capital letter.
	 * @precondition: The character is a letter.
	 * @param c
	 * @return char
	 */
	public static char invert(char c) {
		if (c >= 65 && c < 97)
			return (char) (c - 'A' + 'a');
		else
			return (char) (c - 'a' + 'A');
	}

	
	/**
	 * Returns true if one of the vectors is a cyclic permuation of the other. False otherwise.
	 * @param p
	 * @param q
	 * @return
	 */
	public static boolean areCyclicallyEqual(int[] p, int[] q) {
		if (p.length != q.length)
			return false;
	
		for (int i = 0; i < p.length; i++) {
			int[] qi = permute(q, i);
			int h = 0;
			while (h < p.length && p[h] == qi[h])
				h++;
			if (h == p.length)
				return true;
		}
	
		return false;
	}


	
	/**
	 * Returns the i-th cyclic permutation of the vector v. 
	 * @param p
	 * @param i
	 * @return
	 * PRECONDITION: This method assumes that i< length of p
	 */
	public static int[] permute(int[] p, int i) {
		int[] q = new int[p.length];
		System.arraycopy(p, i, q, 0, p.length - i);
		System.arraycopy(p, 0, q, p.length - i, i);
		
		
		return q;
	}
	
	
	/**
	 * This method adds one in a certain basis to a vector v.
	 * IT DOES NOT WORK IF V=[BASE-1,BASE-1..., BASE-1]
	 * @param v
	 * @param base
	 * @return
	 */
	public static int[] addOne(int[] v, int base){
		int h = 1;
		
		while (h <v.length & v[v.length-h] == base-1){
			v[v.length-h]=0;
			h++;
		}
		if(h==v.length & v[0]==base-1) v[0]=0;
		else if(h <=v.length) 
			v[v.length-h]=v[v.length-h]+1;
		
		return v;
	}	
	
	//this just prints the string
	public static void printa(String s){
		System.out.println(s);
	}

	
}
