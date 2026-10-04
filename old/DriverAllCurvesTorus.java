package a_000workshop2016;

import a_torusPantsHL.Parametrizations;
import a_torusPantsHL.TestsTorus;
import boundary.SurfaceWord;

public class DriverAllCurvesTorus {
	public static void main(String[] args) {

		double[][][] m=Parametrizations.param4();
		double[][] m1={m[1][1],m[1][2]};
		SurfaceWord swTorus=Parametrizations.torus(m1);
		
		
		
		 String dir="torusResults/";
		 statics.CreateDirectory.create(dir);
		 String filename="results";
			
		 String filenameAll=dir+"/all";

		 for(int wl=4 ; wl < 10 ; wl++){
				
			
			 TestsTorus.s_k_MaskitWord(swTorus, filename+wl, wl, filenameAll, dir);
		 }
	}
}
