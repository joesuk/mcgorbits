
package boundaryBialgebra;

import statics.Sta;


/**
 *This class computes the bracket and cobracket following Cohen Lustig and translated to numbers.
 * Also computes intersection and selfintersetion number.
 * @author moira
 *
 */
public class CL extends SurfaceWordOld {
	

	/**
	 * @param s
	 */
	public CL(String s){
		super(s);
	}
	
	public  int si( String s){
 		int[] p = this.toNumber(s);
 		int i=this.countSelfIntersectionPrimitive(p)+Sta.largestPower(p)-1;
	//	System.out.println("Number of selfintersection points of "+s+"  is " +i);
		return i;
 	}
	
	public  int si( String s,boolean output){
 		int[] p = this.toNumber(s);
 		int i=this.countSelfIntersectionPrimitive(p)+Sta.largestPower(p)-1;
		if(output){
			System.out.println("Number of selfintersection points of "+s+"  is " +i);
		}
 		
		return i;
 	}
	
	
	public  int si( String s, String s1){
 		int[] p = this.toNumber(s);
 		int[] p1 = this.toNumber(s1);
 		int i=this.countIntersection(p, p1);
 		
		//System.out.println("Number of intersection points of "+s+" and "+s1 +"  is " +i);
		return i;
 	}

	
	public  int si( String s, String s1,boolean output){
 		
 		int[] p = this.toNumber(s);
 		int[] p1 = this.toNumber(s1);
 		int i= this.countIntersection(p, p1);
 		//if(output){System.out.println("Number of intersection points of "+s+" and "+s1 +"  is " +i);
 		//}
		return i;
 	}
	/**
	 *Returns a matrix of size p.lenght x p.length, with 0 in a_{ij} if i>=j
	 *Then one choses one representative (i,j) of each equivalence class for that,  
	 *a_{ij} is equalt to the sign of the sing of the pair 
	 *(i-cyclic permutatin of p, j cyclic permuation of p)
	 *All the rest of the entries are 0.
	 * @param p
	 * @return
	 */
	public int[][] links1(int[] p) {
		int[][] links = new int[p.length][p.length];
		//choose a pair (i,j), where i>=2, j>=1 
		for (int i = 2; i < p.length; i++) {
			for (int j = 1; j < i; j++) {
				if (p[i] != p[j] & p[i] != (Integer) this.barN.get(p[j - 1])) {
					links[i][j] = isLinked(Sta.permute(p, i), Sta.permute(p, j));	
				} 
			}
		}
		//choose pairs of the form (1,j)
		for (int i = 1; i < p.length; i++) {
			if (p[i] != p[0]) {
				links[i][0] = isLinked(Sta.permute(p, i), Sta.permute(p, 0));
			} 
		}
		//NOTE: When i=0, there are no j<i, that is why those pairs are not studied.
		
		return links;
	}
	

	/**
	 * @param p
	 * @return the number of self intersections of p
	 */
	public int countSelfIntersectionPrimitive(int[] p){
		int number = 0;
		int[][] datos=links1(p);
		for (int i = 1; i < p.length; i++) {
			for (int j = 0; j < i; j++) {
				number=number+Math.abs(datos[i][j]);
					}
				}
		return number;
	}


   
	/**
	 * @param p
	 * @return the number of self intersections of p
	 */
	public int countSelfIntersectionPower(int[] p){
		int number = Sta.largestPower(p)-1;
		int[][] datos=links1(p);
		for (int i = 1; i < p.length; i++) {
			for (int j = 0; j < i; j++) {
				number=number+Math.abs(datos[i][j]);
					}
				}
		return number;
	}
   
	/**
	 * Computes the cobracket.
	 * IMPORTANT: This method returns half of the terms. The other half can be
	 * obtained by symmetrizing each of the exisitnig terms. 
	 * @param p
	 * @return An arraylist CT containg all the terms after cancelation and regrouping.
	 */
	public CobracketTerms cobracket(int[] p){
		CobracketTerms ct = new CobracketTerms();
		int[][] datos=links1(p);
		int[] v1;
		int[] v2;
		   for (int i = 1; i < p.length; i++){	
			          		
	        		if(datos[i][0]!=0){
	        			int c=0;
	        			while(p[i+c]== (Integer)this.barN.get(p[p.length-1-c])){
            				c++;
            			}
	        			
	        			int k=0;
            			while(p[i-k-1]== (Integer)this.barN.get(p[k])){
            				k++;
            			}
            		
            			v1 = new int[p.length-i-2*c];
            			v2= new int[i-2*k];
	        		    System.arraycopy(p, i+c, v1, 0, v1.length);
	        			System.arraycopy(p, k, v2, 0, v2.length);
	        			
	        			if(!Sta.areCyclicallyEqual(v1, v2)){	        	
	        			ct.addCobTerm(new CobracketTerm(datos[i][0], v1, v2));
	        			//ct.addcoTerm(new CobracketTerm(-datos[i][0], v2, v1));
	        			}
	        			
	        			}}
		   	
	       for (int i = 2; i < p.length; i++) {
	        	for (int j = 1; j < i; j++) {
	        		
	        		if(datos[i][j]!=0){
	        			
	                	v1 = new int[p.length-i+j];
	                	System.arraycopy(p, i, v1, 0, p.length-i);
	                	System.arraycopy(p,0,v1,p.length-i,j);
                    		//si inverso
	                		int k=0;
	                		while(p[i-1-k] == (Integer)this.barN.get(p[j+k])){	                			
	                			k++;
	                		}	                			
	                		v2= new int[i-j-2*k];
	                		System.arraycopy(p, j+k, v2, 0, v2.length);	
	        		
	                		if(!Sta.areCyclicallyEqual(v1, v2)){	        	            		
	                			ct.addCobTerm(new CobracketTerm(datos[i][j], v1, v2));
	                			//ct.addcoTerm(new CobracketTerm(-datos[i][j], v2, v1));
	                		}
		        	}}}		
		return ct;
	        	}
	
	/**
	 *Returns a matrix of size (p.lenght x q.length), 
	 *Then one choses one reprensentant (i,j) of each equivalence class for that,  
	 *a_{ij} is equalt tothe sign of the sing of the pair 
	 *(i-cyclic permutatin of p, j cyclic permuation of q)
	 *All the rest of the entries are 0.
	 * @param p
	 * @return the matrix
	 */
	public int[][] links2(int[] p,int[] q) {
		int[][] links;
		links = new int[p.length][q.length];
		for(int i=0; i<p.length; i++){
			for(int j=0; j<q.length; j++){
				if(p[i]!=q[j]& 
						p[i] != (Integer)this.barN.get(q[Sta.rem(j - 1,q.length)])){
					links[i][j]= isLinked(Sta.permute(p, i), Sta.permute(q, j));
				}}}
		
		return links;
	}
	
	
	
	
	/**
	 * @param p
	 * @param q
	 * @return the minimal intersecion number of p and q.
	 */
	public int countIntersection(int[] p, int[] q){
		int number = 0;
		int[][] datos=links2(p,q);
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < q.length; j++) {
				number=number+Math.abs(datos[i][j]);
					}
				}
		return number;
	}

	
	

	/**
	 * Returns 0 if the vectors are not linked
	 * Returns 1 if they are linked with positive sign
	 * Returns -1 if they are linked with negative sign.
	 * @param p
	 * @param q
	 * @return integer
	 */
	public  int isLinked(int[] p, int[] q) {
		int[] p1, p2, q1, q2;
		int s=1;
		if (isSmallerThan(p, barN(p))) {
			p1 = p;
			p2 = barN(p);
		} else {
			p1 = barN(p);
			p2 = p;	
			s=-1;
		}
		if (isSmallerThan(q, barN(q))) {
			q1 = q;
			q2 = barN(q);		
		} else {
			q1 = barN(q);
			q2 = q;
			s=s*(-1);
		}
	
	
		if (isSmallerThan(p1, q1) && isSmallerThan(q1, p2)
				&& isSmallerThan(p2, q2)){
			return s;
		}else{	
		if (isSmallerThan(q1, p1) && isSmallerThan(p1, q2)
				&& isSmallerThan(q2, p2)){
			return -s;
		}else{
		return 0;
			}
		}
	}
		

	
	
	/**
	 * Returns true if p^\infty < q^\infty in the cyclic lexicographic order. False otherwise.
	 * @param p
	 * @param q
	 * @return boolean
	 * Precondition: p and q are not power of the same word.
	 */
	public  boolean isSmallerThan(int[] p, int[] q)  {
		//first we give the answer if the two words start in different letters.
		if (p[0] < q[0])
			return true;
		if (p[0] > q[0])
			return false;
		//If they start in the same letter, we count the maximal number of letters of 
		//which are equal (starting from the begining)
		int coincidence = 0;
		while (p[coincidence % p.length] == q[coincidence % q.length]) {
			coincidence++;
		}
		int start = (Integer) barN.get(p[(coincidence - 1) % p.length]);
		int coordAtVector = this.remainderModSurfaceLength(p[coincidence % p.length] - start);
		int coordAtQ = this.remainderModSurfaceLength(q[coincidence % q.length] - start);
	
		if (coordAtVector < coordAtQ)
			return true;
	
		return false;
	}
	

	
	public BTermsNumber bracket(int[] p1, int[] p2){
		BTermsNumber ct = new BTermsNumber();
		int sign = 0;
		int[] v;
		for (int i = 0; i < p1.length; i++) {
			for (int j = 0; j < p2.length; j++) {
				//if (i,j) is in an inverse equivalence class (i,j) equivalent (i-1,j+1)
				if(p1[Sta.rem(i-1,p1.length)] == (Integer)this.barN.get(p2[j])){
					//if (i,j) is "extreme" in its class i.e., (i,j) nequiv (i+1,j-1)
					if(p1[i] != (Integer)this.barN.get(p2[Sta.rem(j - 1, p2.length)])){
						int[] pi = Sta.permute(p1,i);
						int[] pj =Sta.permute(p2,j);
						sign =isLinked(pi,pj);
						if( sign != 0 ){
							int min = Math.min(p1.length, p2.length);
							int coinc = 0;
							while( coinc< min &
									pj[coinc % pj.length]==
										(Integer) barN.get(pi[Sta.rem(pi.length-coinc-1,pi.length)])
									  ){
								coinc++;
							}
							if(coinc < min){
								v = new int[p1.length+p2.length-2*coinc];
								System.arraycopy(pj, coinc, v, 0, p2.length-coinc);
								System.arraycopy(pi, 0, v, p2.length-coinc, p1.length - coinc);
							}else if(p1.length < p2.length){
								v = new int[p2.length-p1.length];
								System.arraycopy(pj, p1.length, v, 0, p2.length-p1.length );
								
							}else {
								v = new int[p1.length-p2.length];
								System.arraycopy(pi, 0, v, 0, p1.length-p2.length );
								
							}
							ct.addTerm(new BTermNumber(sign, v));
					
						}
					}//now we find the two extrems of two "simetric" direct classes
				}else if(p1[i] != p2[j]
				        & p1[i] != (Integer)this.barN.get(p2[Sta.rem(j - 1, p2.length)])){
					int[] pi = Sta.permute(p1,i);
					int[] pj = Sta.permute(p2,j);
					sign = isLinked(pi,pj);
					if(sign != 0) {
							 v= new int[p1.length + p2.length];
							 
							 System.arraycopy(pi, 0, v, 0, p1.length);
							 System.arraycopy(pj, 0, v, p1.length, p2.length );
						
						 ct.addTerm(new BTermNumber(sign, v));
						 
					}
				}
			
			}
		}
		ct.simplify();
		return ct;
	}
	

	
	
	public BTermsLetter bracketLetters(int[] p1, int[] p2){
		BTermsLetter bt= new BTermsLetter();
		int sign = 0;
		int[] v;
		for (int i = 0; i < p1.length; i++) {
			for (int j = 0; j < p2.length; j++) {
				//if (i,j) is in an inverse equivalence class (i,j) equivalent (i-1,j+1)
				if(p1[Sta.rem(i-1,p1.length)] == (Integer)this.barN.get(p2[j])){
					//if (i,j) is "extreme" in its class i.e., (i,j) nequiv (i+1,j-1)
					if(p1[i] != (Integer)this.barN.get(p2[Sta.rem(j - 1, p2.length)])){
						int[] pi = Sta.permute(p1,i);
						int[] pj =Sta.permute(p2,j);
						sign =isLinked(pi,pj);
						if( sign != 0 ){
							int min = Math.min(p1.length, p2.length);
							int coinc = 0;
							while( coinc< min &
									pj[coinc % pj.length]==
										(Integer) barN.get(pi[Sta.rem(pi.length-coinc-1,pi.length)])
									  ){
								coinc++;
							}
							if(coinc < min){
								v = new int[p1.length+p2.length-2*coinc];
								System.arraycopy(pj, coinc, v, 0, p2.length-coinc);
								System.arraycopy(pi, 0, v, p2.length-coinc, p1.length - coinc);
							}else if(p1.length < p2.length){
								v = new int[p2.length-p1.length];
								System.arraycopy(pj, p1.length, v, 0, p2.length-p1.length );
								
							}else {
								v = new int[p1.length-p2.length];
								System.arraycopy(pi, 0, v, 0, p1.length-p2.length );
								
							}
							
							bt.addTerm(this.toLetter(v),sign);
					
						}
					}//now we find the two extrems of two "simetric" direct classes
				}else if(p1[i] != p2[j]
				        & p1[i] != (Integer)this.barN.get(p2[Sta.rem(j - 1, p2.length)])){
					int[] pi = Sta.permute(p1,i);
					int[] pj = Sta.permute(p2,j);
					sign = isLinked(pi,pj);
					if(sign != 0) {
							 v= new int[p1.length + p2.length];
							 
							 System.arraycopy(pi, 0, v, 0, p1.length);
							 System.arraycopy(pj, 0, v, p1.length, p2.length );
						
							 bt.addTerm(this.toLetter(v),sign);
						 
					}
				}
			
			}
		}
		return bt;
	}
}
