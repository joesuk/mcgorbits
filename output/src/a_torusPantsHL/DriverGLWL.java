package a_torusPantsHL;

public class DriverGLWL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double la=1;
		double lb=1;
		double lc=1;
		int maxWL=17;
		String filename="1lengths"+la+"_"+lb+"_"+lc;
		String dir = "3Pants"+la+"_"+lb+"_"+lc+"/";
		statics.CreateDirectory.create(dir);
		HLSI.pantsHistWLGL(la, lb, lc, maxWL, filename, dir);
	}

}
