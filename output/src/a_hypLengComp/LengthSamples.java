package a_hypLengComp;
import java.util.Random;

import statics.FileStuff;

import boundary.*;



public class LengthSamples {
	


    public static double computea(double la){
    	return 1-2/(Math.cosh(la/2)+1);
    	// return (Math.cosh((la/2))-1)/(Math.cosh((1/2)*la)+1);
	}
    
    public static double computeLengthb(double a, double b, double c,SurfaceWord sw){
    	  int[] ccc={1};
    	  PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord( ccc, sw.length());
    	  Curve cccc =new Curve(dcw, sw);
    	//	System.out.println(cccc.toString()+cccc.hyperbolicLength());
    	  return cccc.hyperbolicLength();
        				}
    
  public static double computeLengthc(double a, double b, double c,SurfaceWord sw){
	  int[] ccc={0,2};
	  PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord( ccc, sw.length());
	  Curve cccc =new Curve(dcw, sw);
	//	System.out.println(cccc.toString()+cccc.hyperbolicLength());
	  return cccc.hyperbolicLength();
    			
    
    	
    	
	}
    
  public static double[] computeParameters(double la, double lb, double lc){
	  double tra = 2*Math.cosh(la/2);
	  double trb = 2*Math.cosh(lb/2);
	  double trab = 2*Math.cosh(lc/2);
	  double disc = 2*Math.sqrt(trab*trab + trab*tra*trb + tra*tra + trb*trb-4);
	  
	  double  a = (tra -2 )/(tra + 2);
	  double b= ( 2*trab + tra*trb+disc)/((trb+2)*(tra+2));
	  double c= (2 *trab+tra * trb+disc)/((trb-2)*(tra+2));
	  
	  double[] parameters = {a,b,c};
	 // System.out.println(a);
	  return parameters;
	  
	  
	}
  
  public static double[][] computeMatrix(double la, double lb, double lc){
	  double tra = 2*Math.cosh(la/2);
	  double trb = 2*Math.cosh(lb/2);
	  double trab = 2*Math.cosh(lc/2);
	  double disc = 2*Math.sqrt(trab*trab + trab*tra*trb + tra*tra + trb*trb-4);
	  
	  double  a = (tra -2 )/(tra + 2);
	  double b= ( 2*trab + tra*trb+disc)/((trb+2)*(tra+2));
	  double c= (2 *trab+tra * trb+disc)/((trb-2)*(tra+2));
	  
	  double[] parameters = {a,b,c};
	 // System.out.println(a);
	  double aa = parameters[0];
		double bb= parameters[1];
		double cc= parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
	  return mat;
	  
	  
	}
 

    public static void computeMeanSample(double la, double lb, double lc, int sampleSize, int length, SurfaceWord sw, String filename){
    	double[] parameters = computeParameters(la,lb,lc);
    	double aa = parameters[0];
    	double bb = parameters[1];
    	double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		//CurveList cl = new CurveList(sw);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		while(counter<=sampleSize){
			counter++;
			double leng=randomWordVectorLength(length,sw);
		//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
		//	System.out.println(counter+" "+totalLeng);
			//statics.FileStuff.fileSentence("sampleSize"+sampleSize+"lenght"+length, legend);		
		}

		double mean = totalLeng/sampleSize;
		double var = totalLengSquare/sampleSize-Math.pow(mean, 2)/sampleSize;

		String legend = la+"\t"+ lb+"\t"+ lc+"\t"+(mean/length)+"\t"+length;
	//	String legend = la+"\t"+ lb+"\t"+ lc+" \t"+ aa+" \t"+bb+"\t "+cc+"\t "+mean+"\t"+var;
		System.out.println(legend);
		
		statics.FileStuff.fileSentence(filename, legend);
	}
    

	/**
	 * This method gives a random  word vector.
	 * @param length
	 * @return
	 */
	public static double randomWordVectorLength(int length, SurfaceWord sw) {
		int[] v = new int[length];
		Random generator = new Random();
		v[0]=generator.nextInt(sw.length());

		for (int h = 1; h <= length-1; h++) {
			int aux=generator.nextInt(sw.length()-1);
			if(aux< sw.bar((v[h-1]))){
				v[h] = aux;
			}else v[h]=aux+1;
		}
		int aux=generator.nextInt(sw.length()-2);

		while(v[length-1]==sw.bar(v[0])){
			aux=generator.nextInt(sw.length()-1);
			if(aux<sw.bar(v[length-2])){
				v[length-1] = aux;
			}else v[length-1]=aux+1;
		}
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(v , sw.length());
		Curve c =new Curve(dcw, sw);
		//System.out.println(c.toString());
		return c.hyperbolicLength();

	}

	public static String randomWordString(int length, SurfaceWord sw) {

		int[] v = new int[length];
		Random generator = new Random();
		v[0]=generator.nextInt(sw.length());

		for (int h = 1; h <= length-1; h++) {
			int aux=generator.nextInt(sw.length()-1);
			if(aux< sw.bar((v[h-1]))){
				v[h] = aux;
			}else v[h]=aux+1;

			//	System.out.println(v[h]);

		}
		int aux=generator.nextInt(sw.length()-2);

		while(v[length-1]==sw.bar(v[0])){
			aux=generator.nextInt(sw.length()-1);
			if(aux<sw.bar(v[length-2])){
				v[length-1] = aux;
			}else v[length-1]=aux+1;
		}
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(v , sw.length());
		Curve c = new Curve(dcw, sw);
		return c.toString();

	}
	

}
