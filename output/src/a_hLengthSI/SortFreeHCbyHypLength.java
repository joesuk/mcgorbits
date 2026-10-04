package a_hLengthSI;
import boundary.*;
//simple curves sorted by hyperbolic metric.

public class SortFreeHCbyHypLength {
	public static void main(String[] args) {
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);

		double la = 1;
		double lb = 1;
		double lc = 1;

		double[] parameters = hypLengthComp.LengthSamples.computeParameters(la, lb, lc);
		String filename ="order5";
		for (int h = 0; h < 1; h++) {
			lc = lc ;
			la = la ;
			parameters = hypLengthComp.LengthSamples.computeParameters(la, lb, lc);
		    String  s= la+" "+lb+" "+lc;
			System.out.println(s);
			
			statics.FileStuff.fileSentence(filename,s);
			// System.out.println(lc);
			double aa = parameters[0];
			double bb = parameters[1];
			double cc = parameters[2];
			double d = 1 / (cc - bb);
			double e = 1 / (1 - aa);
			double[][] mat = {
					{ (1 + aa) * e, 2 * aa * e, 2 * e, (1 + aa) * e },
					{ (bb + cc) * d, -2 * bb * cc * d, -2 * d, (bb + cc) * d } };

			sw.setHyperbolicMatrix(mat);
			for(int cl1 = 1; cl1<5;cl1++){

			CurveList cl = new CurveList(sw);

			PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
					new int[cl1], sw.length());
			Curve c = new Curve(dcw, sw);
			c.initialize();

			while (c.length() == cl1) {

				cl.add(c);

				c = c.next();
			}

			cl = cl.sortedCurveList(new SortByHyperbolicLength());
			
			for (int i = 0; i < cl.size(); i++) {
				String sentence = cl.get(i) + "\t"
						+ cl.get(i).hyperbolicLength()+"\t"
						+ cl.get(i).selfIntersection();
				System.out.println(sentence);
				statics.FileStuff.fileSentence(filename,sentence);
				c = c.next();
			}
			System.out.println();
		}
			
		}
	}

		
	
}
