package a_equivantCurves;

import java.util.ArrayList;

import boundary.CurveList;

public class DriverEquivClasses {
	
	public static void main(String[] args){
		
		
		

		double error = Math.pow(10, -4)	;
		int top = 10;
		int sizeClass = 2;
		String dir ="TrEquivCl"+error+"/";

	
		
		
		for(int wordLength = 5 ; wordLength < 21 ; wordLength++){
				
			ArrayList<CurveList> al = FindLengthEquivClassesF2.checkList0(wordLength, error, 1,2,3, sizeClass);
					//,sizeClass );
		
			al =  FindLengthEquivClassesF2.checkList(error, 0.1,2,1, al, sizeClass);
			
		//	FindLengthEquivClassesF2.printListofListMathematica(al,dir+"FIRST1"+error+"listOfClasses"+wordLength);
			for(int h =0 ; h <  al.size() ; h++){
			//	FindLengthEquivClasses.printListOfCurvesMathematica(al.get(h),"test"+al.get(h).size());
			}
		
		

			for(int sa = 1; sa < top ; sa++  ){
				for(int sb = 1 ; sb <top ; sb++){
			 		for(int sc = 1 ; sc <top ; sc ++){
						//System.out.println(sa+" "+sb+" "+sc);
			 			
			 	//		System.out.println("WL is "+wordLength+" \t"+sa+ "\t" + "\t"+sb+"\t"+sc);
						al = FindLengthEquivClassesF2.checkList(error, sa, sb, sc, al, sizeClass);
					//	System.out.println("Classes "+al.size());
					//	FindLengthEquivClassesF2.printListofListMathematica(al,dir+"FIRST"+error+"listOfClasses"+wordLength);
						
					}

				}
			}
			

			
		//	FindLengthEquivClassesF2.printListofListMathematica(al,dir+"XXX"+error+"listOfClasses"+wordLength);
			double[] m11= {0,-1,1,0};
 			double[] m22={1,1,1,2};
 			
 		//	System.out.println("WL is "+wordLength+" \t"+sa+ "\t" + "\t"+sb+"\t"+sc+"\t"+sd+"\t"+(sa*sd-sb*sc)+"\t "+m22[0]*m22[3]);
			al = FindLengthEquivClassesF2.checkListTrace(error, m11,m22, al, sizeClass );
		
			
			
			
			
			
			statics.CreateDirectory.create(dir);
			System.out.println("*****Final Answer*********");
		//	printListOfListSILength(al,dir+"test"+wordLength+"_size_", statics.MoreHelpers.get_sw_Pants(la, lb, lc));
			FindLengthEquivClassesF2.printListofListMathematica(al,dir+"Error_"+error+"listOfClassesWL_"+wordLength+"#Classes"+al.size());
			FindLengthEquivClassesF2.printListDifferentSI(al,dir+"z_differentSI_"+wordLength+"_ClassSize_");
		//	printListDifferentSI(al, dir+"test"+wordLength+"expError_"+exponent,statics.MoreHelpers.get_sw_Pants(la,lb,lc));
			
			
			for(int h =0 ; h <  al.size() ; h++){
			//	FindLengthEquivClassesF2.printListOfCurvesMathematica(al.get(h),dir+"testSizeClass"+al.get(h).size()+"wordLength_"+wordLength);
			}
			System.out.println("Number of classes "+al.size());
			System.out.println("*******end*******\n\n");

			//for(int cl = 3; cl < 13 ;cl++){check(cl,Math.pow(exponentBase, -exponent));}

		}
	}

}
