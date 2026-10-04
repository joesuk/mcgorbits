package a_equivantCurves;

import java.util.ArrayList;

import boundary.CurveList;

public class DriverEquivClassesF3 {
	
	public static void main(String[] args){

		int exponent =2;
		int exponentBase  = 10;
		int top = 6;
	//	double constant = 0.20;
		int sizeClass = 2;
		//	int wordLength= 15;
		String surfaceString = "aAbBcC";
		double[] m1 = {2,1,1,1};
		double[] m2 = {2,0,0,0.5};
		double[] m3 ={1,2,0,1};
        
		double error = Math.pow(exponentBase, -exponent);
		System.out.println("The error is "+error);
	//	int wl =3;
		
		
		for(int wordLength = 3 ; wordLength < 15; wordLength++){
			
			ArrayList<CurveList> al = FindLengthEquivClassesF3.checkListTrace0(wordLength,
					Math.pow(exponentBase, -exponent), m1,m2,m3, sizeClass,surfaceString );
		
			al =  FindLengthEquivClassesF3.checkListTrace(error,m2,m1,m3,al,sizeClass,"aAbBcC" );
			
			for(int h =0 ; h <  al.size() ; h++){
			//	FindLengthEquivClasses.printListOfCurvesMathematica(al.get(h),"test"+al.get(h).size());
			}
		
			System.out.println(al.size()+"********"+wordLength);

			for(int sa = 1; sa < top ; sa++  ){
				for(int sb = 1 ; sb <top ; sb++){
			 		for(int sc = 1 ; sc <top ; sc ++){
			 			
						//System.out.println(sa+" "+sb+" "+sc);
			 			double sd = (double)(1+sb*sc)/(double)sa;
			 			double[] m11= {sa,sb,sc,sd};
			 			double[] m22={sa,0,0,1/(double)sa};
			 			double[] m33={0,-sb,1/(double)sb, 1};
			 		//	System.out.println(sa+ "\t"+sb+"\t"+sc+"\t"+sd+"\t"+(sa*sd-sb*sc)+"\t "+m22[0]*m22[3]+"\t"+(-1)*m33[2]*m33[1]);
						al = FindLengthEquivClassesF3.checkListTrace(error, m11,m22,m33, al, sizeClass,surfaceString );
						al = FindLengthEquivClassesF3.checkListTrace(error, m22,m33,m11, al, sizeClass,surfaceString );
						al = FindLengthEquivClassesF3.checkListTrace(error,m33, m22,m11,al, sizeClass,surfaceString );
					
					}

				}
			}
			String dir ="ECF3_2014_March30/";
			statics.CreateDirectory.create(dir);
			System.out.println("*****Final Answer*********");
		//	printListOfListSILength(al,dir+"test"+wordLength+"_size_", statics.MoreHelpers.get_sw_Pants(la, lb, lc));
		//	FindLengthEquivClassesF3.printListofListMathematica(al,dir+"z12nextest"+wordLength);
		//	FindLengthEquivClassesF3.printListDifferentSI(al,dir+"z12_differentSI_"+wordLength+"_size_");
		//	printListDifferentSI(al, dir+"test"+wordLength+"expError_"+exponent,statics.MoreHelpers.get_sw_Pants(la,lb,lc));
			
			
			for(int h =0 ; h <  al.size() ; h++){
				FindLengthEquivClassesF3.printListOfCurvesMathematica(al.get(h),dir+"testSizeClass"+al.get(h).size()+"wordLength_"+wordLength);
			}
			System.out.println("Number of classes "+al.size());
			System.out.println("*******end*******\n\n");

			//for(int cl = 3; cl < 13 ;cl++){check(cl,Math.pow(exponentBase, -exponent));}

		}
	}

}
