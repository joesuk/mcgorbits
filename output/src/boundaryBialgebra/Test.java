package boundaryBialgebra;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CL cl = new CL("abcABC");
		final int lowerBound = 5 ;
		final int upperBound = 6;
		
		long elapsed;
		long oldElapsed=1;
		
		String fileName= "Test"+cl.getSurfaceWord()+".txt";
		
		File data = new File(fileName);
		try {
			FileWriter out = new FileWriter(data, true);
			for(int h = lowerBound ; h < upperBound ; h++){
				Long start = System.currentTimeMillis();

				testFabiConj(h,cl);
				elapsed = System.currentTimeMillis() - start;
				Long ratio =new Long(1);
				if (lowerBound > 2) {
					ratio =(Long)(elapsed / oldElapsed);		
				} 
				oldElapsed = elapsed;
				
				
				String answer = composeOutput(cl,elapsed, ratio, h);
				out.write(answer);
				

				out.flush();
				
			}
		          
			System.out.print("end");
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
		
	public static void testFabiConj(int j, CL cl){
		int[] v = new int[j]; 
		int bound = (int) Math.pow(cl.getSurfaceLength(),j);
		for(int h = 0 ; h < bound-1 ; h++){
			addOne(v ,cl.getSurfaceLength() );

			if(statics.Sta.isPrimitive(v) & cl.isCyclicallyReduced(v)){				
							
				if(cl.countSelfIntersectionPrimitive(v) != 0){	
					int[] v1 = new int[2*j];	
					System.arraycopy(v,0,v1,0,v.length);
					System.arraycopy(v,0,v1,v.length,v.length);				
				
					if(cl.cobracket(v1).getTerms().size()==0){
						System.out.println("testing vector");
						for(int t:v)
						System.out.print(t);
						System.out.println();
					}
				}
			}	
		}
		
		System.out.println(j+" tested.");
	}
	
	
	
	public static void testCob(int j, CL cl){
		int[] v = new int[j]; 
		int bound = (int) Math.pow(cl.getSurfaceLength(),j);
		for(int h = 0 ; h < bound-1 ; h++){
			addOne(v ,cl.getSurfaceLength() );

			if(statics.Sta.isPrimitive(v) & cl.isCyclicallyReduced(v)){				
							
				if(cl.countSelfIntersectionPrimitive(v) != 0){	
								
				
					if(cl.cobracket(v).getTerms().size()==0){
					
						
						System.out.println(cl.toLetter(v));
											}
				}
			}	
		}
		
		System.out.println(j+" tested.");
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
	
	
	
	public static String composeOutput(CL cl, long elapsed, Long ratio, int wordSize){
		Date now = new Date();
		
		String answer = 
			"Checked fabi x^2 conjecture for surface "+
			cl.getSurfaceWord()+ " and all words of length "+ wordSize+"\n"+ 
			"Test ended at: "+now.toString()+"\n"+
		"Time used in this test "+ convertTime(elapsed)+"\n"+
		"Ratio (with previous time): "+ ratio.toString()+"\n\n";
		
		System.out.println(answer);
		return answer;
	}
	
	public static String convertTime(long timeInMillis) {
		//		

		// Get elapsed time in seconds
		Long elapsedTimeSec = (Long) (timeInMillis / 1000);

		// Get elapsed time in minutes
		Long elapsedTimeMin = (Long) (timeInMillis / (60 * 1000));

		// Get elapsed time in hours
		Long elapsedTimeHour = (Long) (timeInMillis / (60 * 60 * 1000));

		// Get elapsed time in days
		Long elapsedTimeDay = (Long) (timeInMillis / (24 * 60 * 60 * 1000));

		return elapsedTimeDay.toString() + " days "
				+ elapsedTimeHour.toString() + " hours " + elapsedTimeMin
				+ " minutes " + elapsedTimeSec.toString() + " sec "
				+ ((Long) timeInMillis).toString() + " millis";

	}

}
