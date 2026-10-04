package a_WL_SI;

import boundary.Curve;
import boundary.DoublyCyclicWord;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;

public class DriverWL_SI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int wl=17;
		int[][] data = new int[wl+1][wl*wl/3];
		
		for(int h = 1 ; h < 21 ; h++){
			System.out.println(h);
			data= countThree(h,data);
		}

	}
	
	public static int[][] count( int maxWL, int[][] data){



	 
	 	String surf="abcABC";
		String dir ="wl_"+surf+"3/";
	 	statics.CreateDirectory.create(dir);
	 	String filename = dir+surf+"wl_";
		
		SurfaceWord sw1 = new SurfaceWord(surf);
	
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[maxWL], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();

	
		

		while (c.length() <= maxWL ) {	
			
				
			
				data[c.length()-1][c.selfIntersection()]++;
				
			if(c.selfIntersection()<3)
				statics.FileStuff.fileSentence(filename+c.length()+"si"+c.selfIntersection(), c.toString());
				
			c = c.next();
		    	
		}		
			statics.FileStuff.fileMatrix(data, dir+surf+maxWL);	
			return data;
		}
	
	
	public static int[][] countThree( int maxWL, int[][] data){



		 
	 	String surf="abcABC";
		String dir ="wl_"+surf+"3/";
	 	statics.CreateDirectory.create(dir);
	 	String filename = dir+surf+"wl_";
		
		SurfaceWord sw1 = new SurfaceWord(surf);
	
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[maxWL], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();

	
		

		while (c.length() <= maxWL ) {	
			//  System.out.println(c.toString()+"\t"+Helpers.usesThreeGenerators(c.toString()));
				if(Helpers.usesThreeGenerators(c.toString())){
			
					data[c.length()-1][c.selfIntersection()]++;
				
					statics.FileStuff.fileSentence(filename+c.length()+"si"+c.selfIntersection(), c.toString());
				}
			c = c.next();
		    	
		}		
			statics.FileStuff.fileMatrix(data, dir+surf+maxWL);	
			return data;
		}
	

}
