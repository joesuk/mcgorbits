package boundary;

/**
 * This class contains the basic operations related to a surface word. 
 * 
 * We design SurfaceWord as immutable objects
 * 
 * @author moira
 *
 */
public class SurfaceWord extends Word{
	int[] encode;
	double[][] hyperbolic_matrix;

	/**
	 *This constructor will make computations associated to the
	 *surface word sw.
	 *VERY IMPORTANT PRECONDITION: The string surface word must be a surface
	 *word, that is, a word in whicj every letter and its bar have exactly only
	 *one appearance each.
	 * @param sw
	 */
	public SurfaceWord(int[] sw){
		word = sw;
		translationTable();
	}
	public SurfaceWord(String sw){
		word = Word.fromString(sw, sw.length()).word;
		translationTable();
	}
	public SurfaceWord(int[] sw, double[][] mat){
		word = sw;
		translationTable();
		setHyperbolicMatrix(mat);
	}
	public SurfaceWord(String sw, double[][] mat){
		word = Word.fromString(sw, sw.length()).word;
		translationTable();
		setHyperbolicMatrix(mat);
	}
	//Create the translation table during initialization
	private void translationTable(){
		encode = new int[length()];
		for(int i = 0 ; i< length() ; i++){
			encode[word[i]] = i;
		}
	}
	
	public void setHyperbolicMatrix(double[][] mat){
		try{
			if(mat.length!=length()/2){
				throw new Exception();
			}
		}catch(Exception e){
			System.out.println("Amount of matrix doesn't match SurfaceWord length/2");
		}
		hyperbolic_matrix = new double[mat.length*2][4];
		//create a new matrix to contain inverses
		for(int i=0;i<mat.length;i++){
			hyperbolic_matrix[2*i] = Helpers.matrixMultiply(mat[i],Helpers.identityMatrix());
			hyperbolic_matrix[2*i+1] = Helpers.matrixInverse(mat[i]);
		}
	}
	
	public double[][] getHyperbolicMatrix(){
		return hyperbolic_matrix;
	}
	
	public int[] encode(int[] p){
		int[] q = new int[p.length];
		for(int i=0;i<q.length;i++){
			q[i] = encode[p[i]];
		}
		return q;
	}
	
	public int encode(int p){
		return encode[p];
	}

	/**
	 * @param s a string
	 * @return the vector corresponding to this string.
	 */
/*	public int[] toNumber(String aWord){
		int[] answer = new int[aWord.length()];
		for(int i=0 ; i < aWord.length() ; i++){
			answer[i]= toNumber.get(aWord.charAt(i));
		}
		return answer;
	}*/
	
	
	/**
	 * Returns the vector in letters.
	 * @param p
	 * @return
	 */
	/*public String toLetter(int[] p){
		String answer = "";
		if(p == null) return null;
		for(int i =0 ; i< p.length ; i++){
			answer=answer+ toLetter.get(p[i]);
		}
		return answer;
	}*/

	/**
	 *
	 * @return the length of the surface word.
	 */
	public int length() {
		return word.length;
	}

	/**
	 * 
	 * @return the surfaceWord as a string
	 */
	/*public String toString() {
		return surfaceWord;
	}*/

	/* Returns the bar of a word given in numbers.
	 * @param q
	 * @return
	 */
	/*public int[] barN(int[] q){
		int[] w = new int[q.length];
		for (int i = 0; i < q.length; i++) {
			w[i] = CyclicWord.bar((q[q.length - i - 1]));
		}
		return w;
	}*/
	

}
