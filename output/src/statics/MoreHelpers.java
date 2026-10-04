package statics;

import boundary.SurfaceWord;

public class MoreHelpers {
	
	public static String reverse(String s2){
		String aux="";
		for(int i =0 ; i < s2.length() ; i++){
			aux = aux+s2.charAt(s2.length()-1-i);
			
		
		}
		return aux;
	}
	
	public static String cycPermute(String s2, int h){
		String aux = s2.substring(h,s2.length())+s2.substring(0,h);
		//System.out.println(aux);
		return aux;
	}
	
	public static String invert(String s2){
		String aux="";
		for(int h=0; h < s2.length() ; h++){
			aux=aux+Sta.invert(s2.charAt(s2.length()-1-h));
		}
		return aux;
	}
	
	
	public static boolean areInverseOrPalindromePairs(String s1,String s2){
		if(s1.length()!=s2.length()) return false;
		
	    String aux = reverse(s2);
	    String aux1 = invert(s2);
	 //   System.out.println(s1+" "+aux);
		for(int i = 0; i< s1.length() ; i++){
		//	System.out.println(aux);
			if(s1.equals(aux)) return true;
			if(s1.equals(aux1)) return true;
			
			aux=cycPermute(aux,1);
			aux1=cycPermute(aux1,1);
		
		}
		return false;
		
	}
	public static SurfaceWord get_sw_Pants(double la, double lb, double lc){
		SurfaceWord swPants = new SurfaceWord("aAbB");
		double[][] mat1 = hypLengthComp.LengthSamples.computeMatrix(la,lb,lc);
		swPants.setHyperbolicMatrix(mat1);
		return swPants;
	}

}
