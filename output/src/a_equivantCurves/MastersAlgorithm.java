//Masters Algorithm with Self Intersection numbers shown
//The idea is to first generate random sequences of exponents {p}, {q}, and {k}
//Then generate the tree at depth n using the exponents as Masters describes.

package a_equivantCurves;

import java.util.*;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class MastersAlgorithm {
	public static void compute(int n) {
		String dir = "dataMasters/";
		statics.CreateDirectory.create(dir);
		String filename = dir + "test" + n;

		String g = "1"; // String needed for while loop below when determining
		// when to swap words before iterating

		// final int n = 3; //CHOOSE DEPTH HERE

		// Material for generating random sequences of exponents
		Random generator = new Random();
		Random generator1 = new Random();
		Random generator2 = new Random();

		int p[] = new int[n];
		int q[] = new int[n];
		int k[] = new int[n];

		for (int i = 0; i < p.length; i++) {
			p[i] = generator.nextInt(2) + 1; // Can change max integer inside
			// parentheses. Max integer is
			// not achieved. +1 needed so
			// that the 0 exponent does not
			// show
			q[i] = generator1.nextInt(2) + 1;
			k[i] = generator2.nextInt(1) + 1;
		}

		/*
		 * p[0]=2; p[1]=1; q[0]=3; q[1]=1;
		 */
		String sentence = "";
		// Printing sequence
		for (int i = 0; i < p.length; i++) {
			System.out.print(p[i] + " ");
			sentence = sentence + p[i] + " ";
		}
		sentence = sentence + "\n";

		System.out.println();
		for (int i = 0; i < q.length; i++) {
			System.out.print(q[i] + " ");
			sentence = sentence + q[i] + " ";
		}
		System.out.println();
		sentence = sentence + "\n";
		for (int i = 0; i < k.length; i++) {
			System.out.print(k[i] + " ");
			sentence = sentence + k[i] + " ";
		}
		System.out.println();

		statics.FileStuff.fileSentence(filename, sentence + "\n");

		int interPants = 0;
		int interTorus = 0;
		// Words are generated in this for loop.
		for (int t = 0; t < Math.pow(2, n - 1); t++) {

			String v = "a";
			String w = "b";

			String bin = Integer.toBinaryString(t); // Turn value t into binary
			// string. Used for
			// swapping, 0 means v and w
			// are NOT swapped before
			// next iteration begins,
			// and 1 means v and w are
			// swapped.

			while (bin.length() < n)
				// Makes sure bin has enough digits for swapping information
				bin = "0" + bin;

			String b = bin;

			// Code for word corresponding to bin
			for (int m = 0; m < n; m++) {

				// Cuts up word into 6 pieces as shown by Masters so that
				// exponents can be computed for each piece.
				String v1 = v;
				String v2 = invert(w); // Inverts w, method is at the bottom.
				String v3 = v;
				String v4 = v;
				String v5 = invert(w);
				String v6 = invert(v);

				String w1 = v;
				String w2 = v;
				String w3 = invert(w);
				String w4 = invert(v);
				String w5 = v;
				String w6 = invert(w);

				// Empty strings used for computing exponents.
				String vK1 = "";
				String vK2 = "";
				String vK12 = "";
				String vK4 = "";
				String vK5 = "";

				String wK2 = "";
				String wK3 = "";
				String wK23 = "";
				String wK5 = "";
				String wK6 = "";

				// Computes exponents
				for (int i = 0; i < p[m] + q[m] - 1; i++) {
					vK1 = vK1 + v1;
					vK4 = vK4 + v4;
					wK2 = wK2 + w2;
					wK5 = wK5 + w5;
				}

				// Computes exponents
				for (int i = 0; i < q[m]; i++) {
					vK2 = vK2 + v2;
					vK5 = vK5 + v5;
					wK3 = wK3 + w3;
					wK6 = wK6 + w6;
				}

				// Computes exponents
				for (int i = 0; i < k[m]; i++) {
					vK12 = vK12 + vK1 + vK2;
					wK23 = wK23 + wK2 + wK3;
				}

				// Pieces word back together after exponents are computed
				v = vK12 + v3 + vK4 + vK5 + v6;
				w = w1 + wK23 + w4 + wK5 + wK6;

				// Now bin is used to determine if v and w should be swapped for
				// next iteration.
				String x = b.substring(n - 1 - m, n - m);

				if (x.equals(g)) { // Recall g = "1"
					String temp = v;
					v = w;
					w = temp;
				}

			}

			// Code for computing Self Intersection numbers
			SurfaceWord swPants = new SurfaceWord("aAbB");
			SurfaceWord swTorus = new SurfaceWord("abAB");
			Word ww1 = new Word(Word.fromString(v, swPants.length()));
			Word W1 = ww1.cyclicallyReduce();
			Curve c1Pants = new Curve(W1, swPants);
			Word ww2 = new Word(Word.fromString(v, swPants.length()));
			Word W2 = ww2.cyclicallyReduce();
			Curve c1Torus = new Curve(W2, swTorus);
			Word ww3 = new Word(Word.fromString(w, swPants.length()));
			Word W3 = ww3.cyclicallyReduce();
			Curve c2Pants = new Curve(W3, swPants);

			Word ww4 = new Word(Word.fromString(w, swPants.length()));
			Word W4 = ww4.cyclicallyReduce();
			Curve c2Torus = new Curve(W4, swTorus);

			// System.out.println(c1Pants.toString()+ " pants " +
			// c1Pants.selfIntersection() + " torus +
			// c1Torus.selfIntersection());
			// System.out.println(c2Pants.toString()+" pants " +
			// c2Pants.selfIntersection() + " torus "+
			// c2Torus.selfIntersection());

			String legend = c1Pants.toString() + " pants "
					+ c1Pants.selfIntersection() + " torus "
					+ c1Torus.selfIntersection();
			System.out.println(legend);
			statics.FileStuff.fileSentence(filename + "si", legend);

			legend = c2Pants.toString() + " pants "
					+ c2Pants.selfIntersection() + " torus "
					+ c2Torus.selfIntersection();
			System.out.println(legend);

			statics.FileStuff.fileSentence(filename + "si", legend);

			if (t == 0) {
				interPants = c1Pants.selfIntersection();
				interTorus = c1Torus.selfIntersection();
				// System.out.println(t+ " "+interPants);
				if (interPants != c2Pants.selfIntersection()) {
					statics.FileStuff.fileSentence(filename, "Counterexamplex "
							+ t);
					// System.out.println(t+ " "+22);
				}
			} else if (interPants != c1Pants.selfIntersection()
					|| interPants != c2Pants.selfIntersection()) {
				statics.FileStuff.fileSentence(filename, "Counterexample "
						+ interPants + " " + c1Pants.selfIntersection() + " "
						+ c2Pants.selfIntersection());
				statics.FileStuff.fileSentence(
						filename + "Counterexample",
						"Counterexample " + interPants + " "
								+ c1Pants.selfIntersection() + " "
								+ c2Pants.selfIntersection());
			} else if (interTorus != c1Torus.selfIntersection()
					|| interTorus != c2Torus.selfIntersection()) {
				statics.FileStuff.fileSentence(filename, "Counterexample "
						+ interTorus + " " + c1Torus.selfIntersection() + " "
						+ c2Torus.selfIntersection());
				statics.FileStuff.fileSentence(
						filename + "Counterexample",
						"Counterexample " + interTorus + " "
								+ c1Torus.selfIntersection() + " "
								+ c2Torus.selfIntersection());
			}

			if (t == Math.pow(2, n - 1) - 1) {
				sentence = "Word Length " + c1Pants.length() + " pants: "
						+ interPants + " Torus " + interTorus;
				statics.FileStuff.fileSentence(filename, sentence);
				statics.FileStuff.fileSentence(filename, "Test Completed"
						+ "\n\n\n");

				System.out.println("Test completed" + "\n\n\n");
			}

		}

		// Invert method code. Also used in BuserAlgorithm.
	}

	public static String invert(String v) {
		int n = v.length();
		int c = 0;

		String x = "";

		while (c < n) {
			String w = v.substring(c, c + 1);

			if (w.equals(w.toLowerCase())) {
				w = w.toUpperCase();
			} else {
				w = w.toLowerCase();
			}

			x = x.concat(w);

			c = c + 1;
		}

		int i = 0;

		String y = "";

		while (i < n) {

			String z = x.substring(n - 1 - i, n - i);

			y = y.concat(z);

			i = i + 1;
		}

		return y;

	}
}
