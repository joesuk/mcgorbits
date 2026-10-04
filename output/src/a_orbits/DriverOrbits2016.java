package a_orbits;




import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;



public class DriverOrbits2016 {


	public static void main(String[] args) {

		System.out.println("Start");


		
	
		//(Class of) curve to find the orbit for
		String curve = "aabbaB";
		// String curve2 = "aaaaa";
		// String curve3 = "aabbaB";
		
		
		//The set of curves
		HashSet<String> hs = new HashSet<String>();
		hs.add(curve);
		// hs.add(curve2);
		// hs.add(curve3);
		
		//Maximal word  length of the (classes of) curves to find
		int maxWL=20;
		//
		int boundWLOrbit=maxWL+20;
		// how many times we apply the generators.
		int times = 15; 
		
		
		//directory to store results
				String dir=curve+times+"_orbitERASE/";	
				statics.CreateDirectory.create(dir);	
				
		// the set of curves
	    hs = Mcg.findOrbits(times, hs, dir, maxWL);

	    //count different classes
	  
	    int[][] total1 = new int[boundWLOrbit+100][2];

	    //Store the results
	    Iterator it = hs.iterator();
	    
	    while(it.hasNext()){
	    	String s1 = (String) it.next();
	    	if(s1.length()<200){
	    		int homLength= Math.abs(Mcg.count(s1,'a')-Mcg.count(s1,'A'))+Math.abs(Mcg.count(s1,'b')-Mcg.count(s1,'B'));



	    		statics.FileStuff.fileSentence(dir+curve+"allHere"+s1.length(), s1+"\t"+Mcg.count(s1,'a')+"\t"+Mcg.count(s1,'A')+"\t"+Mcg.count(s1,'b')+"\t"+Mcg.count(s1,'B'));



	    		//save s1 in a file
	    		if(homLength>= s1.length()-2){
	    			statics.FileStuff.fileSentence(dir+"type1"+curve+"_"+s1.length(), s1+"\t"+homLength);
	    			total1[s1.length()][0]++;
	    		}
	    		else{
	    			statics.FileStuff.fileSentence(dir+"type2"+curve+"_"+s1.length(), s1+"\t"+homLength);
	    			total1[s1.length()][1]++;
	    		}


	    	}
	    }
	    statics.FileStuff.fileMatrix(total1,  dir+curve+"_COUNT");
	
	    
	}
	
	
	
	
	public static String representatives(int number){
		ArrayList<String> sts  = new ArrayList<String>();
		String a="a";
		//Selfint one
		String o1="aabb";//0
		String o2="aabAB";
		//Selfint two
		String s5= "aabABabAB";
		String s6= "aaabAB";
		String s7= "aaabb";
		String s8= "abaBabAB";
		String s9= "aabAAB";
		String s10= "aabaB";//7

		//self-int three
		String t1= "aabABabABabAB";//8
		String t3="aabAbaBAb";
		String t7= "aabbAB";//10


		String t2="aaabABabAB";

		String t4="aabAABabAB";
		String t5="abaBAbaBAbaB";
		//	String t5="AbabABabAB";
		String t6= "aaaabAB";

		String t8= "aabaBabAB";
		//	String t9= "aabABabaB";

		String t10= "aabaBAbaB";
		String t11= "aaabAAB";
		String t12= "aaaabb";
		String t13= "aaabaB";
		String t14= "aabaaB";
		String t15= "abaBAbAB";		//aabABabaB

		sts.add(a);
		sts.add(o1);
		sts.add(o2);
		sts.add(s5);
		sts.add(s6);
		sts.add(s7);
		sts.add(s8);
		sts.add(s9);
		sts.add(s10);
		sts.add(t1);
		sts.add(t2);
		sts.add(t3);
		sts.add(t4);
		sts.add(t5);
		sts.add(t6);
		sts.add(t7);
		sts.add(t8);//15
		//	sts.add(t9);//16
		sts.add(t10);
		sts.add(t11);
		sts.add(t12);
		sts.add(t13);
		sts.add(t14);
		sts.add(t15	);//22
		// System.out.println("The number of curves is "+sts.size());
		return sts.get(number);
	}









	

}
