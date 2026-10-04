package boundary;


import hypLengthComp.LengthSamples;

public class StudyPairsEquivCurves {
	
	/**
	 *
	 *Computes proportions of words of each CL and each SI, that have the same intersection with a pairof equivalent curves
	 *
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxCL
	 */
	public static void proportions(SurfaceWord sw, String filename, int cl,String s1, String s2) {

		int p1 = cl+1;
		int p2 = (int) (cl*cl/3);
		int[][] si= new int[p1][p2];
		int[][] siNo= new int[p1][p2];
		int[][] siTotal= new int[p1][p2];
		 PrimitiveDoublyCyclicWord dcw ;
		
		 System.out.println("Start"+sw.length());
		 
		// String filename = "ADX" + combLength1;
		Word ww1 = new Word( Word.fromString(s1, sw.length()));
		Word ww2 = new Word(Word.fromString(s2,sw.length()));
		
		Word w1 = ww1.cyclicallyReduce();
		Word w2 = ww2.cyclicallyReduce();
		
		Curve c1 = new Curve(w1, sw);
		Curve c2 = new Curve(w2, sw);
		
	//	System.out.println(c1.selfIntersection()+" "+c2.selfIntersection());
		
		for(int cl1 =1; cl1 <=cl ; cl1++){
			dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
			 Curve c = new Curve(dcw, sw);
			 c.initialize();
		while (c.length() == cl1) {
			//System.out.println("curve "+c.toString());
			if (c.intersection(c1) == c.intersection(c2)) {
					//System.out.println(c.toString()+" "+c.selfIntersection()+"_"+c.intersection(c1) +"_"+ c.intersection(c2));
					si[c.length()][c.selfIntersection()]++;
			}else siNo[c.length()][c.selfIntersection()]++;
			c = c.next();
			}
		for(int i = 1; i<=cl1; i++){
			for(int j = 0; j < p2 ; j++){
				if(si[i][j]+siNo[i][j] !=0){
					int aux =(int) Math.round( 100*si[i][j]/ (si[i][j]+siNo[i][j]) );
					siTotal[i][j]= aux;
					//System.out.println("cl "+i+" si "+j+" "+aux+" Total "+(si[i][j]+siNo[i][j]) );
				}else siTotal[i][j]=222;
				}
		}
		
	 statics.Sta1.fileMatrixSI_CL(siTotal, filename+cl1 );	
		}
		
	}


}
