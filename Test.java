package a_000workshop2016;
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
		
		/*BasepointAnalysis ba = new BasepointAnalysis(bp,4);
		ba.analyze();
		ba.printCases();
		ba.printWords();
		
		OrbitEnumeration oe = new OrbitEnumeration();
		oe.setDistance(15);
		oe.setMaxWL(20);
		oe.setBoundWLOrbit(40);
		oe.setSelfIntersections(4);
		oe.generate("ba4.txt");
		oe.remove();		
		oe.printWords();*/
		
		MinimalWord mw = new MinimalWord(4);
		mw.checkMinimal("ba4.reduced.txt");
	}

}
