package a_equivantCurves;

import a_torusPantsHL.HLSI;

public class DriverMatrices {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String dir = "Sasha/";
		String allfilename ="test";

		statics.CreateDirectory.create(dir);
		
		HLSI.manyRandomMatrices("all", 10, "allf", 10, 2, "LECLASSES");
	}

}
