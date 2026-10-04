package examples;
import boundary.*;


public class SICL {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		int maxLength =21;
		int[][] output = new int[21][21*21/4];
		
	
		
		CyclicWord dcw = new CyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
	
		for(int h =c.length() ; h < maxLength ; h++){
			dcw = new CyclicWord(new int[h] , sw.length());
			while(c.length()==h){
			
			output[h-1][c.selfIntersection()]++;
			
			c = c.next();
			}
			statics.FileStuff.fileMatrix(output, z+h, "STUFF");	
			System.out.println(h+"\t\t");
		}
		
		
		
				
		
		
	}	
		
}
