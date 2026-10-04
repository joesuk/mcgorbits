import boundary.*;
import a_torusPantsHL.Parametrizations;
import java.io.*;

public class SIRef {
	public static void main(String[] args) throws IOException {
		double[][][] m = Parametrizations.param4();
		double[][] m1 = {m[1][1], m[1][2]};
		SurfaceWord sw = Parametrizations.torus(m1);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringBuilder out = new StringBuilder();
		while ((line = br.readLine()) != null) {
			Word w = new Word(Word.fromString(line, 4));
			Curve c = new Curve(w, sw);
			out.append(line).append(' ').append(c.selfIntersection()).append(' ').append(c.toString()).append('\n');
		}
		System.out.print(out);
	}
}
