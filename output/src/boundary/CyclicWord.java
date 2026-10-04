package boundary;

public class CyclicWord extends Word{

	/**
	 * Constructor
	 *               
	@param  p  The array form of the CyclicWord
	 *                                     
	@param  b  The base the CyclicWord is in, the base is the SurfaceWord's length                       
	@return    The CyclicWord            
	 */	
	public CyclicWord(int[] p, int b){		
		super(p,b);
		canonicalization();
		
	}
	
	public CyclicWord(int[] p, int b, boolean check){
		super(p,b);
		//Should one check if p is in canonical form?
		if(check==true){
			canonicalization();
		}
	}
	public void initialize(){
		for(int i=0;i<word.length-1;i++){
			word[i]=0;
		}
		word[word.length-1]=-1;
	}
	public CyclicWord(Word p){
		super(p);
		canonicalization();
	}
	public CyclicWord(Word p, boolean check){
		super(p);
		if(check==true){
			canonicalization();
		}
	}
	
	//There is actually a O(n) algorithm, I'm going to implement that sometime
	public void canonicalization(){
		Word max = new Word(word, base);
		for(int i=1;i<length();i++){
			Word nw = this.permute(i);
			if(nw.compareTo(max)<0){
				max = nw;
			}
		}
		this.word = max.word;
	}
	
	/**
	 * Returns the clone of this CyclicWord
	 * 
	@return   A clone of the CyclicWord         
	 */	
	public CyclicWord clone(){
		Word a = new Word(word,base);
		CyclicWord v = new CyclicWord(new int[0], base);
		v.word = a.word;
		return v;
	}

	/**
	 * Set the ith element to be j
	 * 
	 * If the length of the Cyclicword is n, it set the i%n-th element
	@param i integer i as index
	 * 
	@param j integer j as element
	 */		
	/*public CyclicWord replace(int[] table){
		CyclicWord v2 = this.clone();
		int[] v = v2.word;
		for(int i=0;i<v.length;i++){
			v[i] = table[v[i]];
		}
		return v2;
	}*/
	
	/**
	 * Return the next CyclicWord
	 * 
	 @return the next CyclicWord
	 */		
	public CyclicWord next(){
		CyclicWord v = this.clone();
		while(true){
			v.addOne();
			//check if reduced and smallest
			if(v.isReduced()&&v.isSmallest()){
				return v;
			}
		}
	}
	

	/**
	 * Return if the bar(i)i exists
	 * 
	@return true if reduced, false otherwise.
	 */		
	public boolean isReduced(){
		for(int i = 0 ; i < length() ; i++){
			if(get(i) == bar(get(i+1))){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Return if the CyclicWord is the smallest of all cyclic permutations
	 * 
	@return true if smallest, false otherwise.
	 */		
	private boolean isSmallest(){
		for(int i = 0 ; i < length() ; i++){
			for(int j=0;j<length();j++){
				if(get(i+j)>get(j)){
					break;
				}
				if(get(i+j)<get(j)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Return if the CyclicWord is the smallest of all cyclic permutations in both directions
	 * 
	@return true if smallest, false otherwise.
	 */	
	public boolean isDoublySmallest(){
		CyclicWord v = clone();
		for(int i=0;i<length();i++){
			v.word[i] = bar(word[length()-1-i]);			
		}
		for(int i = 0 ; i < length() ; i++){
			for(int j=0;j<length();j++){
				if(v.get(i+j)>get(j)){
					break;
				}
				if(v.get(i+j)<get(j)){
					return false;
				}
			}
		}
		return isSmallest();
	}
	
	
	public boolean isDoublyPalSmallest(){
		CyclicWord v1 = clone();
		for(int i=0;i<length();i++){
			v1.word[i] = bar(word[length()-1-i]);			
		}
		for(int i = 0 ; i < length() ; i++){
			for(int j=0;j<length();j++){
				if(v1.get(i+j)>get(j)){
					break;
				}
				if(v1.get(i+j)<get(j)){
					return false;
				}
			}
		}
		
		CyclicWord v2 = clone();
		for(int i=0;i<length();i++){
			v2.word[i] = (word[length()-1-i]);			
		}
		for(int i = 0 ; i < length() ; i++){
			for(int j=0;j<length();j++){
				if(v2.get(i+j)>get(j)){
					break;
				}
				if(v2.get(i+j)<get(j)){
					return false;
				}
			}
		}
		return isSmallest();
	}

}
