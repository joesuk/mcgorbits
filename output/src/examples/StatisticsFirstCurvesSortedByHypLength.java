package examples;
import boundary.*;


public class StatisticsFirstCurvesSortedByHypLength {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		//int total =2000
		int combLength = 5 ;
	
		
		double la = 0.55;
		double lb = 0.25 ;
		double lc = 0.5;
		
		
		double[] parameters = hypLengthComp.LengthSamples.computeParameters(la, lb, lc);
		/****************************************/
		/*    Sorted list by hyperbolic length  */
		/****************************************/
		//Need  a < 1 < b < c
		double aa = parameters[0];
		double bb= parameters[1];
		double cc= parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		
		double constant= Math.min(Math.min(la, lb), lc/2);
		double maxHyp =combLength/constant;
		System.out.println("Max hyperbolic length " +maxHyp+" constant "+constant);
		System.out.println("parameters are "+ aa+" "+bb+" "+cc+" "+d);
		
		
		sw.setHyperbolicMatrix(mat);
		CurveList cl = new CurveList(sw);
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		double q=1;
		double p=1;
		
	 while(c.length()<combLength){
		//System.out.println(c.toString()+c.hyperbolicLength());
		
		if(c.hyperbolicLength()<maxHyp){
			 q = Math.max(q,c.hyperbolicLength()/c.length());
			 p= Math.max(q,c.length()/c.hyperbolicLength());;
			// System.out.println(p+ " "+q+" "+c.hyperbolicLength()+" "+c.length());
			cl.add(c);
			
			//System.out.println(c.toString());
		}//else System.out.println(c.toString()+ " "+c.hyperbolicLength()+" "+c.length());
		
			c = c.next();
		}
		System.out.println();
		String w ="";
		int[] intersection = new int[combLength*combLength/4];
		cl = cl.sortedCurveList(new SortByHyperbolicLength());
		c.initialize();
	
		for(int i=0;i<cl.size();i++){
			//cl.add(c);
			
			double hyp=cl.get(i).hyperbolicLength();
			//double q =  (cl.get(i).hyperbolicLength()/cl.get(i).length());
			//System.out.println(cl.get(i).toString()+"\t "+hyp+"\t"+q);
			
				 w = cl.get(i).toString();
				intersection[cl.get(i).selfIntersection()]++;
				System.out.println(cl.get(i).toString());
			
			
		}
		
		//System.out.println(w);


		for(int h= 0 ; h < intersection.length ; h++){
			System.out.print(intersection[h]+"\t ");
		}
		
		String filename ="data1"+combLength;
		
		statics.FileStuff.fileVector(intersection, filename, "last word "+w);
		statics.FileStuff.fileSentence(filename, "combinatorial length "+combLength+"\n hyperbolic lenth "+maxHyp );
		statics.FileStuff.fileSentence(filename, "comb length / hyp length "+ p);
		statics.FileStuff.fileSentence(filename, "last word "+w+ "\n hyp leng /comb length "+ q);
	}
}
