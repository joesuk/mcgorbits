//Masters Algorithm for printing the words
package a_equivantCurves;
import java.util.*;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class Masters2 {
	public static void main(String [] args) {
		Random generator = new Random();
		Random generator1 = new Random();
		Random generator2 = new Random();
		
		
		String g = "1";
		
		final int n = 5;
		
		int p[] = new int[n];
		int q[] = new int[n];
		int k[] = new int[n];
		
		for (int i = 0; i < p.length; i++) {
			p[i] = generator.nextInt(1) + 1;
			q[i] = generator1.nextInt(1) + 1;
			k[i] = generator2.nextInt(2) + 1;
		}
		
		for (int i = 0; i < p.length; i++){
			System.out.print(p[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < q.length; i++){
			System.out.print(q[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < k.length; i++){
			System.out.print(k[i] + " ");
		}
		System.out.println();
		
		for (int t = 0; t < Math.pow(2, n-1); t++) {
			
		String v = "a";
		String w = "b";
		
		String bin = Integer.toBinaryString(t);
		
		while (bin.length() < n)
			bin = "0" + bin;
		    
		String b = bin;

		for (int m = 0; m < n; m++){
		
		String v1 = v;
		String v2 = invert(w);
		String v3 = v;
		String v4 = v;
		String v5 = invert(w);
		String v6 = invert(v);
		
		String w1 = v;
		String w2 = v;
		String w3 = invert(w);
		String w4 = invert(v);
		String w5 = v;
		String w6 = invert(w);
		
		String vK1 = "";
		String vK2 = "";
		String vK12 = "";
		String vK4 = "";
		String vK5 = "";
		
		String wK2 = "";
		String wK3 = "";
		String wK23 = "";
		String wK5 = "";
		String wK6 = "";
		
		for (int i = 0; i < p[m] + q[m] - 1; i++) {
			vK1 = vK1 + v1;
			vK4 = vK4 + v4;
			wK2 = wK2 + w2;
			wK5 = wK5 + w5;
		}
		
		for (int i = 0; i < q[m]; i++) {
			vK2 = vK2 + v2;
			vK5 = vK5 + v5;
			wK3 = wK3 + w3;
			wK6 = wK6 + w6;
		}
		
		for (int i = 0; i < k[m]; i++){
			vK12 = vK12 + vK1 + vK2;
			wK23 = wK23 + wK2 + wK3;
		}
		
		v = vK12 + v3 + vK4 + vK5 + v6;
		w = w1 + wK23 + w4 + wK5 + wK6;
		
		String x = b.substring(n-1-m,n-m);
		
		if (x.equals(g)){
			String temp = v;
			v = w;
			w = temp;
			}
		
		}
		
		SurfaceWord sw= new SurfaceWord("aAbB");
		SurfaceWord sw1= new SurfaceWord("abAB");
		Word ww1 = new Word( Word.fromString(v, sw.length()));
		Word L = ww1.cyclicallyReduce();
		Word ww2 = new Word( Word.fromString(w, sw.length()));
		Word M = ww2.cyclicallyReduce();
		
		System.out.println(L);
		System.out.println(M);
		}
		
		
	}
	public static String invert(String v){
		int n = v.length();
		int c = 0;
		
		String x = "";
		
		while (c < n){
			String w = v.substring(c,c+1);
			
			if (w.equals(w.toLowerCase())) {
				w = w.toUpperCase();
			} else {
				w = w.toLowerCase();
			}
			
			    x = x.concat(w);
			    
			    c = c + 1;
		}
		
		int i = 0;
		
		String y = "";
		
		while (i < n){
			
			String z = x.substring(n-1-i,n-i);
			
			y = y.concat(z);
			
			i = i + 1;
		}
		
		return y;
		
	}
}