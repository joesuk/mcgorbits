package boundaryBialgebra;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;



public class CopyOfTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
	
		String surfWord ="abcABC";
		String fileName= "Test"+surfWord+".txt";
		
		testCurves(6,surfWord,0);
		
		
		
	
				
				
				 
	
	}
	
		
	public static void testCurves(int wl, String surfWord, int si){
		CL cl = new CL(surfWord);
		String dir=surfWord+"/";
		statics.CreateDirectory.create(dir);
		SurfaceWordOld sw = new SurfaceWordOld(surfWord);
		int[] v = new int[wl]; 
		int bound = (int) Math.pow(cl.getSurfaceLength(),wl);
		
		for(int h = 0 ; h < bound-1 ; h++){
			addOne(v ,cl.getSurfaceLength() );

			if(statics.Sta.isPrimitive(v) & cl.isCyclicallyReduced(v)){				
							
				if(cl.countSelfIntersectionPrimitive(v) != 0){	
					
					if(cl.cobracket(v).getTerms().size()==si){
						String legend=sw.toLetter(v)+"\t" +cl.cobracket(v).toString(sw);
						System.out.println();
						if(si>0)
							statics.FileStuff.fileSentenceNoNewLine(dir+wl+"test"+si, legend);
						else
							statics.FileStuff.fileSentence(dir+wl+"test"+si, legend);
						
					}
				}
			}	
		}
		
		System.out.println(wl+" tested.");
	}
	
	
	


	public static int[] addOne(int[] v, int base){
		int h = 1;
		while (h < v.length & v[v.length-h] == base-1){
			v[v.length-h]=0;
			h++;
		}
		if(h <=v.length) v[v.length-h]=v[v.length-h]+1;
		
		return v;
	}	
	
	

	


}
