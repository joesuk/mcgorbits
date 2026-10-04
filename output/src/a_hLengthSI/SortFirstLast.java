package a_hLengthSI;
import a_torusPantsHL.Parametrizations;
import boundary.*;
//simple curves sorted by hyperbolic metric.

public class SortFirstLast {
	public static void main(String[] args) {
	
		

		double la = 5;
		double lb = 76;
		double lc = 78;
		
		int maxWL=5;
	
		//int cl1 = 12;
		String filename = la+"_"+lb+"_"+lc;
		SurfaceWord sw =  Parametrizations.pants(la, lb, lc);
			
			PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
			Curve c = new Curve(dcw, sw);
			c.initialize();
			

			CurveList cl = new CurveList(sw);

//			PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
			double constant1 = Math.min(Math.min(la, lb), lc*0.5);

			while (c.length() <= maxWL) {
				
				System.out.println("First "+c.toString()+"\t"+c.selfIntersection()+"\t"+c.hyperbolicTrace()+"\t"+c.hyperbolicLength());
				
				if(c.hyperbolicLength()<constant1*maxWL){
				cl.add(c);
				
			
				
				}
				c = c.next();
			}

			cl = cl.sortedCurveList(new SortByHyperbolicLength());
			
		
			String sentence ="";
			String words="";
			String sis="";
			for(int hh = 0 ; hh < cl.size() ; hh++){
				words=words+"\""+cl.get(hh).toString()+"\",";
				sis=sis+cl.get(hh).selfIntersection()+",";
				sentence=sentence+cl.get(hh).toString()+"\t"+cl.get(hh).hyperbolicLength()+"\t"+cl.get(hh).hyperbolicTrace()+"\t"+cl.get(hh).selfIntersection()+"\n";
			}
			
			
			
		//	+"\t"+cl.get(cl.size()-1) + 
		//	"\t"+ cl.get(cl.size()-1).hyperbolicLength();
			
			System.out.println(sentence+"\n");
			statics.FileStuff.fileSentence(filename,sentence);
			statics.FileStuff.fileSentence(filename+"XX",words);
			statics.FileStuff.fileSentence(filename+"XXXX",sis);
			
			
		}
		

	
}
