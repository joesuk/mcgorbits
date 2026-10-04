package a_keren_GL_samplingByWL;


import hypLengthComp.Histogram;
import hypLengthComp.LengthSamples;
import hypLengthComp.Shortcuts;
import boundary.SurfaceWord;

public class MeanSDListAll {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String dir ="DataFeb2013/";
		statics.CreateDirectory.create(dir); 
	    double la, lb, lc;
	    int minLen =7;
	    int maxLen= 20;
	    String legend ="la+\t+lb+\t+lc+\t+len+\t+mean+\t+ variance+\t+mean/len+\t+variance/len";
		for(int len =minLen ; len <maxLen; len++){
			
		//Histogram.findSortedListOfAllGLengthsCommas(6, sw, "list"+la+"_"+lb+"_"+lc);
			
		    la=3;
			lb=6;
			lc=12;
			if(len==minLen){
				String filenameEst = dir+"stats"+"la"+la+"lb"+lb+"lc"+lc;
				statics.FileStuff.fileSentence(filenameEst, legend)	;
			}
		    Shortcuts.allMeanSDMaxMin(len, la, lb, lc);
		   
		   
			
		}
		

	}
	
	
	

}
