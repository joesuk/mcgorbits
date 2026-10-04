package boundary;

import java.util.ArrayList;

public class Helpers {
	public static double[] matrixMultiply(double[] x, double[] y){
		double[] z={
				x[0]*y[0]+x[1]*y[2]	,
				x[0]*y[1]+x[1]*y[3]	,
				x[2]*y[0]+x[3]*y[2]	,
				x[2]*y[1]+x[3]*y[3]	
			};
		return z;
	}
	public static double[] matrixInverse(double[] x){
		double a = 1.0/(x[0]*x[3] - x[1]*x[2]);
		double[] y = {a*x[3], -a*x[1], -a*x[2], a*x[0]};
		return y;
	}
	public static double[] identityMatrix(){
		double[] i = {1, 0, 0, 1};
		return i;
	}
	public static int mod(int j, int m){
		j = j % m;
		if (j >= 0)
			return j;
		else
			return m + j;
	}
	/**
	 * Return the list of all integer partition of n with exactly k components
	 *               
	@param  n  a integer to partition
	 *                             
	@param  k  the amount of components
	 *                            
	@return An ArrayList of partitions, where each one is represented as an ArrayList          
	 */

	public static ArrayList<ArrayList<Integer>> partition(int n, int k){
		if(k==2){
			return pair(n);
		}
		//I will provide a better algorithm later, I think this will be rarely used
		ArrayList<ArrayList<Integer>> a = partition(n);
		ArrayList<ArrayList<Integer>> r = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<n;i++){
			if(a.get(i).size()==k){
				r.add(a.get(i));
			}
		}
		return r;
	}
	/**
	 * Return the list of all integer partition of n with exactly 2 components
	 *               
	@param  n  a integer to partition
	 *                            
	@return An ArrayList of partitions, where each one is represented as an ArrayList          
	 */
	public static ArrayList<ArrayList<Integer>> pair(int n){
		ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
		for(int i=1;i<=n/2;i++){
			ArrayList<Integer> b = new ArrayList<Integer>();
			b.add(i);
			b.add(n-i);
			a.add(b);
		}
		return a;
		
	}
	/**
	 * Return the list of all integer partition of n
	 *               
	 * The algorithm comes from the paper Fast algorithms for generating integer partitions
	 * Antoine Zoghbi, Ivan Stomenovic, Intern. J. Computer Math., Vol. 70, pp. 319-332
	@param  n  a integer to partition
	 *                                     
	@return      An ArrayList of partitions, where each one is represented as an ArrayList          
	 */
	public static ArrayList<ArrayList<Integer>> partition(int n){
		int[] x = new int[n+1];
		for(int i=1;i<n+1;i++){
			x[i] = 1;
		}
		x[1] = n;
		int m = 1;
		int h = 1;
		ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		b.add(n);
		a.add(b);
		while(x[1]!=1){
			if(x[h]==2){
				m=m+1;
				x[h]=1;
				h=h-1;
			}else{
				int r = x[h]-1;
				int t = m-h+1;
				x[h] = r;
				while(t>=r){
					h=h+1;
					x[h] = r;
					t = t-r;
				}
				if(t==0){
					m=h;
				}else{
					m=h+1;
					if(t>1){
						h = h+1;
						x[h] = t;
					}
				}
			}
			b = new ArrayList<Integer>();
			for(int i=1;i<m+1;i++){
				b.add(x[i]);
			}
			a.add(b);
		}
		return a;
	}
}
