package a_000workshop2016;
import a_orbits.DriverOrbits2016;
import a_000workshop2016.*;
/**
 * Class for testing basepoint analysis.
 * @author Joe
 *
 */
public class Test {
	
	public static void main(String[] args) {
		BasepointReader bp = new BasepointReader(4);
		try {
		bp.readDiagrams("bp4.txt");
		bp.readLoops("si4.txt");
		} catch (Exception e) {System.out.println("ee");}
		
		BasepointAnalysis ba = new BasepointAnalysis(bp,4);
		ba.analyze();
		
		
	}

}
