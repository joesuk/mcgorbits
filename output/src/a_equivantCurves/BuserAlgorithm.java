package a_equivantCurves;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class BuserAlgorithm {
	
	
	public static void main(String[] args) {
		
		for(int h = 1 ; h < 10 ; h++)
			System.out.println(testBuser(h));
		
		
		
		
	}
	
	public static boolean testBuser(int n){
		

		int si0pants=0;
		int si0torus=0;
		int sipants=0;
		int sitorus=0;
		int wlen=0;
		
		for (int i = 0; i < Math.pow(2, n); i++) {
			String bin = Integer.toBinaryString(i);
			
			while (bin.length() < n) bin = "0" + bin;

			String v = "b"; 
			String w = bin;
			String g = "0";
			int l = w.length();
			int c = 0;			
			while (c < l){
				String x = w.substring(l-1-c,l-c);
				if (x.equals(g)){
					v = v + "A" + invert(v) + "a" + v;
				} else {
					v = invert(v) + "a" + v + "A" + invert(v);
				}
				c = c + 1;
			}
		
			SurfaceWord sw= new SurfaceWord("aAbB");
			SurfaceWord sw1= new SurfaceWord("abAB");
			Word ww1 = new Word( Word.fromString(v, sw.length()));
			Word w1 = ww1.cyclicallyReduce();
			Curve c1 = new Curve(w1, sw);
			Word ww2 = new Word( Word.fromString(v, sw.length()));
			Word w2 = ww2.cyclicallyReduce();
			Curve c2 = new Curve(w2, sw1);
			
			
			if(i==0) {
				si0pants = c1.selfIntersection();
				si0torus= c2.selfIntersection();
			}else{
				sipants = c1.selfIntersection();
				sitorus =c2.selfIntersection();
			}
		
		//	System.out.println(i+ " "+v+" pants "+si0pants+" torus "+si0torus);

			System.out.println(v+". Sins pair of pants "+si0pants+". Sins punctured torus "+si0torus);
		///	System.out.println(" pants "+c1.selfIntersection()+" torus "+c2.selfIntersection());
			
		//	System.out.println(i);
			wlen=c1.length();
			if(i!=0 & si0pants != sipants) return false;
			if(i!=0 & si0torus!= sitorus) return false;
			

		}
		System.out.println(n);
		statics.FileStuff.fileSentence("testBuser","True for n = "+n+ " SI of class pants "+ si0pants+" SI torus "+si0torus+" word length "+wlen);
		return true;
	}

	//Changes lower case letters to capital letters without reversing order
	public static String halfInvert(String v){
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
		
		return x;
	}
	
	//word inversion method
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
