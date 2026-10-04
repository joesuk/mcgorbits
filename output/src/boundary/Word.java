package boundary;

public class Word implements Comparable<Word>{
	public int[] word;
	public int[] getWord() {
		return word;
	}

	public int base;
	public Word(){
		
	}
	public Word clone(){
		Word v = new Word(this);
		return v;
	}
	public Word(Word w){
		int[] p = w.word;
		word = new int[p.length];
		base = w.base;
		System.arraycopy(p, 0, word, 0, p.length);
	}
	
	public Word(int[] p, int b) {
		word = new int[p.length];
		base = b;
		System.arraycopy(p, 0, word, 0, p.length);
		
		try{
			for(int i=0;i<p.length;i++){
				if(word[i]>=base){
					throw new Exception();
				}
			}
		}catch(Exception e){
			System.err.println("Error: Word and base are not compatible. word:"+this.toString()+" base:"+base);
		}
	}
	
	/**
	 * Set the Word to be a Word with the current length, -1
	 *     
	 */	
	public void initialize(){
		for(int i=0;i<word.length-1;i++){
			word[i]=0;
		}
		word[word.length-1]=-1;
	}

	
	/**
	 * Return the ith cyclic permutation of the Word in array form
	 * 
	@return  the ith cyclic permutation of the Word in array form
	 */	
	public Word permute(int i) {
		int[] q = new int[word.length];
		System.arraycopy(word, i, q, 0, word.length - i);
		System.arraycopy(word, 0, q, word.length - i, i);
		return new Word(q, base);
	}
	
	/**
	 * Return the ith element in the Word
	 *
	 * If the length of the word is n, then it returns i%n-th element
	 * 
	 @param i integer i
	 *
	@return  Return the ith element in the CyclicWord
	 */	
	public int get(int i){
		i = (i%word.length + word.length)%word.length;
		return word[i];
	}
	
	/**
	 * Return the length of the Word
	 * 
	@return    The length of the Word    
	 */	
	public int length(){
		return word.length;
	}
	
	/**
	 * Set the ith element to be j
	 * 
	 * If the length of the Cyclicword is n, it set the i%n-th element
	@param i integer i as index
	 * 
	@param j integer j as element
	 */	
	public void set(int i, int j){
		i = (i%word.length + word.length)%word.length;
		word[i] = j;
	}
	
	/**
	 * Find the bar of i
	 * 
	@param i element i
	 * 
	@return element bar(i)
	 */		
	public static int bar(int i){
		if(i%2==0){
			return i+1;
		}
		return i-1;
	}
	/**
	 * Find the bar of an word in array form
	 * 
	@param q array form of a word
	 * 
	@return bar(q)
	 */		
	public static Word bar(Word q){
		int[] qq = new int[q.length()];
		for(int i=0;i<q.length();i++){
			qq[q.length()-1-i] = bar(q.get(i));
		}
		return new Word(qq,q.base);
	}
	
	/**
	 * Mutates the Word by add one to it's array representation
	 */		
	protected void addOne(){
		int[] v = word;
		int count=0;
		for(int i=0;i<v.length;i++){
			if(v[i]==base-1){
				count++;
			}
		}
		if(count==v.length){
			word = new int[v.length+1];
			return;
		}
		int h = 1;
			
		while (h <v.length & v[v.length-h] == base-1){
			v[v.length-h]=0;
			h++;
		}
		if(h==v.length & v[0]==base-1){
			v[0]=0;
		}else if(h <=v.length) {
			v[v.length-h]=v[v.length-h]+1;
		}
	}
	/******\\
	 * *
	 *Retursn the word plus one.
	 * @param v
	 * @param base
	 * @return
	 */
	public Word  addOneWord(){
		int[]  v = this.word;
		int h = 1;
		
		while (h <v.length & v[v.length-h] == base-1){
			v[v.length-h]=0;
			h++;
		}
		if(h==v.length & v[0]==base-1) v[0]=0;
		else if(h <=v.length) 
			v[v.length-h]=v[v.length-h]+1;
		
		return new Word(v,base);
	}	
	/**
	 * Return if the Word is not in u = v^k form, where k>1
	 * 
	@return true if primitive, false otherwise.
	 */	
	public boolean isPrimitive() {
		for(int n = 1; n <= length()/2;n++){
			if(length()%n==0){
				boolean h = true;
				for(int j=0;j<length()/n;j++){
					boolean hit = true;
					for(int i=0;i<n;i++){
						if(get(i)!=get(j*n+i)){
							hit = false;
							break;
						}
					}
					if(hit == false){
						h = false;
						break;
					}
				}
				if(h){
					return false;
				}
			}
		}
		return true;
	}
	
	
	 public static Word commutator(Word a, Word b, int base){
		    int i;
		    int[] com = new int[(a.length()+b.length())*2]; 
		   
		    for (i=0; i<2*a.length()+2*b.length(); i++){
		    com[i]=0;
		    }
		    for (i=0; i<a.length(); i++){
		    com[i]=a.word[i];
		    }
		   
		    for (i=0; i<b.length(); i++){
		    com[a.length()+i]=b.word[i];
		    }
		   
		    for (i=0; i<a.length(); i++){
		        if (a.word[a.length()-i-1] % 2 == 0)
		        com[a.length()+b.length()+i] = a.word[a.length()-i-1]+1;
		        else com[a.length()+b.length()+i] = a.word[a.length()-i-1]-1;  
		    }
		   
		    for (i=0; i<b.length(); i++){
		        if (b.word[b.length()-i-1] % 2 == 0)
		        com[2*a.length()+b.length()+i] = b.word[b.length()-i-1]+1;
		        else com[2*a.length()+b.length()+i] = b.word[b.length()-i-1]-1;
		    }
		    Word w1 = new Word(com, base);
		    w1.toString();
		    return w1.reduce();
		      }
	/**
	 * Return if the Word's array representation is all zeros
	 * 
	@return true if it's all zeros, false otherwise.
	 */		
	public boolean isAllZeros(){
		for(int i=0;i < length();i++){
			if(word[i]!=0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Create a Word's array representation from String
	 * 
	@param s the String
	 *
	@param base the base
	 *
	@return array representation from a String
	 */	
	public static Word fromString(String s,int base){
		int[] p = new int[s.length()];
		for(int i=0;i<p.length;i++){
			p[i] = (int) s.charAt(i);
			if(p[i] >= 97){
				p[i]-=97;
				p[i]=p[i]*2;
			}else{
				p[i]-=65;
				p[i]=p[i]*2+1;
			}
		}
		return new Word(p,base);
	}
	
	/**
	 * Compare the current CyclicWord with another
	 * 
	@param a the other CyclicWord
	 *
	@return 
	 */	
	public int compareTo(Word a){
		if(this.length()<a.length()){
			return -1;
		}else if(this.length()>a.length()){
			return 1;
		}
		for(int i=0;i<a.length();i++){
			if(this.get(i)<a.get(i)){
				return -1;
			}else if(this.get(i)>a.get(i)){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * If u=v^k, where u is this Word and v is some other Word then return the largest k.
	 * 
	@return    The largest k     
	 */	
	public int largestPower(){
		//n = partition size
		for(int n = 1; n<length()/2+1;n++){
			if(length()%n==0){
				boolean h = true;
				for(int j=0;j<length()/n;j++){
					boolean hit = true;
					for(int i=0;i<n;i++){
						if(get(i)!=get(j*n+i)){
							hit = false;
							break;
						}
					}
					if(hit == false){
						h = false;
						break;
					}
				}
				if(h){
					return length()/n;
				}
			}
		}
		return 1;
	}
	@Override public boolean equals(Object that2){
		if ( this == that2 ) return true;

			    //use instanceof instead of getClass here for two reasons
			    //1. if need be, it can match any supertype, and not just one class;
			    //2. it renders an explict check for "that == null" redundant, since
			    //it does the check for null already - "null instanceof [type]" always
			    //returns false. (See Effective Java by Joshua Bloch.)
		if ( !(that2 instanceof Word) ) return false;
			Word that = (Word) that2;
			return (compareTo(that)==0);
	}
	/**
	 * Returns the string form of the Word
	 * 
	 * This fails as base is larger than 52. This is highly unlikely
	@return    The String that represent the CyclicWord           
	 */	
	

	//This works up to 52 edges. I assume no one will go that far
	@Override public String toString(){
	   
		char[] c = new char[length()];
		for(int i=0;i<c.length;i++){
			if(word[i]%2==0){
				c[i] = (char) (word[i]/2 + 97);
			}else{
				c[i] = (char) (word[i]/2 + 65);
			}
		}
		return String.valueOf(c);
	}
	
	
	//Returns a cyclically reduced version of the Word
	
	public Word reduce( ) {
		int[] v= this.word;
		int count = 0;
		Word n = new Word(v,base);
		while (count + 1 < n.length()) {
			
			if (n.get(count) == bar(n.get(count + 1))) {
				v = n.word;
				if (n.length() == 2) return null;
				int[] w = new int[n.length() - 2];	
				if (count > 0){
					System.arraycopy(v, 0, w, 0, count);
					
				}
				if(v.length - count - 2 > 0){
					System.arraycopy(v, count + 2, w, count, v.length - count - 2);
					
				}
				n = new Word(w,base);
			
				//count=0;
				if (count > 0){
					//System.out.println(count+" ,, ");
					count--;}
			} else count++;
			
			
		}
		
		
		return n;
	}	
	//checks for reduced NOT necessarily cyc reduced
	
	public boolean is_reducedNNC( ) {
		int[] v= this.word;
		int count = 0;
		Word n = new Word(v,base);
		while (count + 1 < n.length()) {
			
			if (n.get(count) == bar(n.get(count + 1))) {
				return false;
			} else count++;
				
		}	
		return true;
	}	
	public boolean Is_Cyc_reduced( ) {
		int[] v= this.word;
		int count = 0;
		Word n = new Word(v,base);
		while (count + 1 < n.length()) {
			
			if (n.get(count) == bar(n.get(count + 1))) {
				return false;
			} else count++;
				
		}	
		if(n.get(0)==bar(n.get(n.length()-1))){
			return false;
		}
		return true;
	}	
	public boolean Is_reduced( ) {
		int[] v= this.word;
		int count = 0;
		Word n = new Word(v,base);
		while (count + 1 < n.length()) {
			
			if (n.get(count) == bar(n.get(count + 1))) {
				return false;
			} else count++;
				
		}	
		return true;
	}	
	//gives the linearly reduced vector 
	//NOTE: It does not reduce cyclically.
	
	public Word reduce1( ) {
		int[] v= this.word;
		int count = 0;
		while (count + 1 < v.length) {
			
			if (v[count] == bar(get(v[count + 1]))) {
				if (v.length == 2) return null;
				
				int[] w = new int[v.length - 1];
				System.arraycopy(v, 0, w, 0, count);
				Word n = new Word(w,base);
				System.out.println("mid "+n.toString());
				System.arraycopy(v, count + 2, w, count, v.length - count - 2);
				v = w;
				 n = new Word(w,base);
				System.out.println(n.toString());
				if (count > 0)
					count--;
			} else count++;
		}
		Word n = new Word(v,base);
		System.out.println(n.toString());
		return new Word(v,base);
	}
	
	public Word cyclicallyReduce( ) {
		Word w1 = this.reduce();
		//System.out.println("xxx"+w1.toString());
		int[] v= w1.word;
		int count = 0;
		while(count < v.length/2 & w1.get(count) == bar(w1.get(v.length-count-1 ))) count++;
		if(count>0) {
			int[] w = new int[v.length-2*count];
			System.arraycopy(v,count , w, 0, v.length-2*count);
			//System.out.println(count);
			return new Word(w,base);
		} else return new Word(v,base);
		

		
		
		
	}
	
	

	
	
	/**
	 *
	 * 
	@return true if reduced, false otherwise.
	 */		
	private boolean isWordReduced(){
		for(int i = 0 ; i < length() ; i++){
			if(get(i) == bar(get(i+1))){
				return false;
			}
		}
		return true;
	}
	

	
	public Word next(){
		Word v = this.clone();
		v.addOne();
		return v;
	}
	
	/**
	 * Return the next CyclicWord
	 * 
	 @return the next CyclicWord
	 */		
	public Word nextReduced(){
		Word v = this.clone();
		while(true){
			v.addOne();
			//check if reduced and smallest
			if(v.isWordReduced()){
				return v;
			}
		}
	}
}
