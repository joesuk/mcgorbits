package a_keren_GL_samplingByWL;



import java.util.ArrayList;



import a_hypLengComp.ExactMean1;
import boundary.*;

/*
 * Note:
 * m=min lenght of segment in polygon
 * M=max length of segment in polygon
 * l(w) geometric length
 * cl(w) combinatorial lenght
 * 
 *  then m. cl(w) ² l(w) ² M. cl(w)
 *  
 *   Claim m = min{l1, l2, l3/2}
 *   Proof: divide in hexagons, etc.
 *   
 *   If l(w) ² K then cl(w) ² K/m.
 *   
 *   The set of geodesics of geometric length at most K, is contained in the set of words of CL at most K/m..
 *   
 *   
 *   Thus we will look all geodesics of length at most K, as a subset of the set of words of comb length K/m. Now, if m is small, 
 *   K/m is big. This makes computations hard or impossible 
 *   
 *   Thus, one should choose parameters so K/m ² CL, where CL is a combinatorial length the computer can deal with.
 *   
 *   In the program mean we input maxHypLength and metric. 
 *   We compute all cl, such tha cl.m ² maxHypLength.
 *   We need m.maxHypLength ² 10, or 11, 12
 *   
 *   Since m = min { la, lb, lc/2}, then one should take  
 *   
 */

public class DriverMeanAllHypLength {
	public static void main(String[] args) {
		int maxHypLength = 2;
		System.out.println(maxHypLength);
	    meanVary("startoverHL2"+ maxHypLength,maxHypLength);
	}
	
	public static void meanVary( String filename ,int cl){
		String sentence1="la"+"\t"+" lb"+"\t"+" lc"+ "\t"+"average"+"\t"+"counter"+"\t"+"cte";;
		statics.FileStuff.fileSentence(filename ,sentence1);
		double r =0.5;
	       for(int ka=1 ; ka<20 ; ka=ka+2){
	    	   for(int kb=1 ; kb<20 ; kb=kb+2){
	    		   for(int kc=1 ; kc<20 ; kc=kc+2){
	    			   double la =ka*r;
	    			   double lb =kb*r;
	    			   double lc =kc*r;
	    			   double[] m= hypLengthComp.Shortcuts.mean( la,lb,lc, cl);
	    			//   System.out.println(m[0]);
	    			//   System.out.println( la+"\t"+ lb+"\t"+ lc+ "\t"+m[1]+"\t"+m[0]+"\t"+m[2]);
	    			   if(m[0]>5){
	    			   String sentence =   la+"\t"+ lb+"\t"+ lc+ "\t"+m[1]+"\t"+m[0]+"\t"+m[2];
	    		       statics.FileStuff.fileSentence(filename ,sentence);
	    		    //   System.out.println(sentence);
	    			   }
	    	   }}    }
	}



}
