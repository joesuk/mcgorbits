package a_torusPantsHL;
import hypLengthComp.LengthSamples;

import java.util.ArrayList;

import a_hypLengComp.PantsWithMetric;
import boundary.Curve;
import boundary.Helpers;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;


public class Parametrizations {

	
	public static void manyMetricsPants(String filename, int maxCL,
			String allFilename, int tot, double kte, String dir) {
	
		ArrayList<PantsWithMetric> listPants = new ArrayList<PantsWithMetric>();
		double maxHypLength = 0;
		int counterMetr = 0;
	
		// compute matrices and stuff for each metric.
		for (int na = 0; na < tot; na++) {
			for (int nb = na; nb < tot; nb++) {
				for (int nc = nb; nc < tot; nc++) {
	
					PantsWithMetric pwm = new PantsWithMetric(0.01 + kte * na,
							0.01 + kte * nb, 0.01 + kte * nc, maxCL);
					listPants.add(pwm);
	
					counterMetr++;
					// System.out.println("Metrics number "+counterMetr+" "+na+
					// " "+nb+" "+nc);
				}
			}
		}
	
		System.out.println("Metrics number " + counterMetr);
		// System.out.println("\nStarting with WL "+" "+maxCL+"\t"+m1[0]+"\t"+m1[1]+"\t"+m1[2]+".");
		System.out.println("Max WL is " + maxCL + ".");
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], 4);
		Curve c = new Curve(dcw, listPants.get(0).getSw());
		c.initialize();
	
		int actualMaxSI = 0;
	
		// String filename2="alltheInfo"+maxCL;
	
		// statics.FileStuff.fileSentence(filename2,"abc");
	
		while (c.length() <= maxCL) {
			actualMaxSI = Math.max(actualMaxSI, c.selfIntersection());
	
			// need to study this curve for all metrics
			for (int h = 0; h < counterMetr; h++) {
				Word w0 = new Word(Word.fromString(c.toString(), 4));
				PrimitiveDoublyCyclicWord w2 = new PrimitiveDoublyCyclicWord(
						w0.getWord(), 4);
				// PrimitiveDoublyCyclicWord i2 = w2;
				c = new Curve(w2, listPants.get(h).getSw());
	
				if (c.hyperbolicLength() <= listPants.get(h).getMaxHL()) {
	
					double[][] histAux = listPants.get(h).getHist();
	
					maxHypLength = Math.max(maxHypLength, c.hyperbolicLength());
					histAux[c.selfIntersection()][1]++;
					if (histAux[c.selfIntersection()][2] == -1) {
						histAux[c.selfIntersection()][2] = c.hyperbolicLength();
					} else if (c.hyperbolicLength() < histAux[c
					                                          .selfIntersection()][2]) {
						histAux[c.selfIntersection()][2] = c.hyperbolicLength();
					}
					// System.out.println(c.toString()+" "+c.hyperbolicLength()+" "+"bla");
					listPants.get(h).setHist(histAux);
	
				}
			}
			c = c.next();
	
		}
	
		for (int h = 0; h < listPants.size(); h++) {
			listPants.get(h).fileHist(filename);
			statics.FileStuff.fileSentence(filename + "all", listPants.get(h)
					.giveData());
			statics.FileStuff.fileSentence(dir + "metric"
					+ listPants.get(h).giveLengthBC(), listPants.get(h)
					.giveData());
	
		}
	
		System.out.println(maxCL + " done\n\n");
	
	}

	public static SurfaceWord pants(double la, double lb, double lc){
		SurfaceWord sw = new SurfaceWord("aAbB");
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
			{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);
		return sw;
	}

	public static double singleCurveLength(String st, SurfaceWord sw){
		Word wwa = new Word(Word.fromString(st,sw.length() ));
		Curve ca = new Curve(wwa,sw);
		return ca.hyperbolicLength();
	
	}

	public static double singleCurveSI(String st, SurfaceWord sw){
		Word wwa = new Word(Word.fromString(st,sw.length() ));
		Curve ca = new Curve(wwa,sw);
		return ca.selfIntersection();
	
	}



	
	public static boolean checkParametersRectangularTorus(double lam, double beta){
		if(Math.sinh(lam)*Math.sinh(beta)<=1){
			System.out.println("WRONG PARAMETERS one");
			statics.FileStuff.fileSentence("WROngParameters", lam +"\t"+ beta);
			return false;
		}
		
	
		double[] m1={Math.exp(beta),0,0,Math.exp(-beta)};
		double[] m2={Math.cosh(lam),-Math.sinh(lam),-Math.sinh(lam),Math.cosh(lam)};
		double[][] m={m1,m2};
		SurfaceWord sw = new SurfaceWord("abAB");
		sw.setHyperbolicMatrix(m);
		
		
		double la =singleCurveLength("a",sw);
		double lb=singleCurveLength("b",sw);
		double lab=singleCurveLength("ab",sw);
		double laB=singleCurveLength("aB",sw);
		double labAB=singleCurveLength("abAB",sw);
		boolean test = (la<lb)& (lb<lab)& (lb<laB);
		if(!test){
			System.out.println("WRONG PARAMETERS");
			statics.FileStuff.fileSentence("WROngParameters", "wrong parameters "+lam+"\t"+beta);
			return false;
			
		}
		
		return true;
	
	
	
	}

	public static SurfaceWord torusRectangular(double lam, double beta){
		if(Math.sinh(lam)*Math.sinh(beta)<=1){
			System.out.println("WRONG PARAMETERS yes");
			statics.FileStuff.fileSentence("WROngParameters", "wrong parameters"+lam+"\t"+beta);
			return new SurfaceWord("abAB");
		}
		
	
		double[] m1={Math.exp(beta),0,0,Math.exp(-beta)};
		double[] m2={Math.cosh(lam),-Math.sinh(lam),-Math.sinh(lam),Math.cosh(lam)};
		double[][] m={m1,m2};
		SurfaceWord sw = new SurfaceWord("abAB");
		sw.setHyperbolicMatrix(m);
		
		
		double la =singleCurveLength("a",sw);
		double lb=singleCurveLength("b",sw);
		double lab=singleCurveLength("ab",sw);
		double laB=singleCurveLength("aB",sw);
		double labAB=singleCurveLength("abAB",sw);
//		boolean test = (la<lb)& (lb<lab)& (lb<laB);
//		if(!test){
//			
//			System.out.println("WRONG PARAMETERS");
//			statics.FileStuff.fileSentence("WROngParameters", "wrong parameters "+lam+"\t"+beta);
//			return new SurfaceWord("abAB");
//			
//		}
		
		return sw;
	
	
	
	}

	
	public static SurfaceWord torus(double lam, double beta, double theta){
		if(Math.sinh(lam)*Math.sinh(beta)<=1){
			System.out.println("WRONG PARAMETERS yes");
			statics.FileStuff.fileSentence("WROngParameters", "wrong parameters"+lam+"\t"+beta);
			return new SurfaceWord("abAB");
		}
		
	
		double[] m1={Math.exp(beta),0,0,Math.exp(-beta)};
		double[] m2={Math.cosh(lam),-Math.sinh(lam),-Math.sinh(lam),Math.cosh(lam)};
		double a=1;// Math.cos(theta);
		double[] m3={Math.cos(theta/2),Math.sin(theta/2),Math.sin(theta/2),Math.cos(theta/2)};
	//	double[] m5={Math.cos(-theta/2)*a,Math.sin(-theta/2)*a,Math.sin(-theta/2)*a,Math.cos(-theta/2)*a};
		double[] m5= Helpers.matrixInverse(m3);
	//	double[] m4= Helpers.matrixMultiply(m1, m3);
		double[] m4= Helpers.matrixMultiply(Helpers.matrixMultiply(m5, m1),m3);
		double[][] m={m1,m4};
		SurfaceWord sw = new SurfaceWord("abAB");
		sw.setHyperbolicMatrix(m);
	//	System.out.println("{{"+m3[0]+","+m3[1]+"},{"+m3[2]+","+m3[3]+"}};");
		System.out.println("{{"+m4[0]+","+m4[1]+"},{"+m4[2]+","+m4[3]+"}};");
		
		double la =singleCurveLength("a",sw);
		double lb=singleCurveLength("b",sw);
		double lab=singleCurveLength("ab",sw);
		double laB=singleCurveLength("aB",sw);
		double labAB=singleCurveLength("abAB",sw);
	/*	boolean test = (la<lb)& (lb<lab)& (lb<laB);
		if(!test){
			
			System.out.println("WRONG PARAMETERS");
			statics.FileStuff.fileSentence("WROngParameters", "wrong parameters "+lam+"\t"+beta);
			return new SurfaceWord("abAB");
			
		}*/
		
		return sw;
	
	
	
	}


	public static SurfaceWord torus(double[] m1, double[] m2){
		
		
		
		double[][] m={m1,m2};
		SurfaceWord sw = new SurfaceWord("abAB");
		sw.setHyperbolicMatrix(m);

	
		
		return sw;
	
	}
	
public static SurfaceWord torus(double[][] m1){
		
		
		
	
		SurfaceWord sw = new SurfaceWord("abAB");
		sw.setHyperbolicMatrix(m1);

	
		
		return sw;
	
	}
	
	public static SurfaceWord torus(double m1, double m2, double m3,double m4, double m5, double m6, double m7, double m8){
		
		
	
		double[][] m={{m1,m2,m3,m4},{m5,m6,m7,m8}};
		SurfaceWord sw = new SurfaceWord("abAB");
		sw.setHyperbolicMatrix(m);

	
		
		return sw;
	
	
	
	}
	
	public static double traceCurve(String st, SurfaceWord sw){
		Word wwa = new Word(Word.fromString(st,sw.length() ));
		Curve ca = new Curve(wwa,sw);
		return ca.hyperbolicTrace();
	
	}


	public static double[][][] param4(){
		double[][][] mm={ {{18,7,24.},{0.000911882,0,0,1096.63},
			{2.01273*Math.pow(10,7) ,2.63237*Math.pow(10,7) , 2.62434*Math.pow(10,7)  ,3.45059*Math.pow(10,7) }}, 
			{{7,8,13.5},{0.000335463,0,0,2980.96},
			{244.695,456.579,456.579,851.94}},
			{{1.,1.2,1.},{0.297545,0,0,3.36084},{1.7279,1.1872,1.1872,1.39444}},
			{{2.,11.6727,10},{ 8.52313*Math.pow(10,-6) ,0. , 0 ,117328.},{0.892185 ,2.60281 , 2.60281 ,8.71413}},
			{{1.,1.,0.847451},{0.367879,0,0,2.71828},{1.54308,1.1752,1.1752,1.54308}},
			 {{0.881374,0.881374,7.88495*Math.pow(10, -8) },{0.414113,0,0,2.4148},{1.37741,0.999657,0.999657,1.45151}},
			 {{0.89,0.889,0.2},{0.409749,0,0,2.44052},{1.55975,1.00799,1.00799,1.29254}}
			};
			
		
		return mm;
		
	}

	
	public static double[][][] param2(){
		
		double mm[][][] = {
				
					
								{
										{ 5, 5, 1 },
										{ 0.005357482394, 0, 0, 186.6548364 },
										{ 0.1072621385, 4.360053487,
												4.360053487, 186.5529318 } },
								{
										{ 5, 5, 3 },
										{ 0.006568528959, 0, 0, 152.2410887 },
										{ 0.1898792271, 5.279451994,
												5.279451994, 152.0577780 } },
								{
										{ 5, 5, 5 },
										{ 0.006715666026, 0, 0, 148.9055585 },
										{ 1.050491565, 12.42286422,
												12.42286422, 147.8617826 } },
								{
										{ 5, 5, 7 },
										{ 0.006735325024, 0, 0, 148.4709344 },
										{ 7.435463060, 32.36841235,
												32.36841235, 141.0422067 } },
								{
										{ 5, 5, 9 },
										{ 0.006737901091, 0, 0, 148.4141703 },
										{ 54.61841974, 71.57055042,
												71.57055042, 93.80248847 } } ,
					
								{
										{ 5, 7, 1 },
										{ 0.0008275873367, 0, 0, 1208.331684 },
										{ 0.06670336582, 4.418596876,
												4.418596876, 307.6905956 } },
								{
										{ 5, 7, 3 },
										{ 0.0009076997640, 0, 0, 1101.685865 },
										{ 0.05380464196, 2.889259847,
												2.889259847, 173.7363567 } },
								{
										{ 5, 7, 5 },
										{ 0.0009114469872, 0, 0, 1097.156515 },
										{ 0.1666540894, 4.930934488,
												4.930934488, 151.8961521 } },
								{
										{ 5, 7, 7 },
										{ 0.0009118264664, 0, 0, 1096.699906 },
										{ 1.030434625, 12.30362620,
												12.30362620, 147.8785883 } },
								{
										{ 5, 7, 9 },
										{ 0.0009118754623, 0, 0, 1096.640979 },
										{ 7.417217545, 32.33072346,
												32.33072346, 141.0604008 } } ,
					
								{
										{ 5, 9, 1 },
										{ 0.0001143708952, 0, 0, 8743.483190 },
										{ 0.1363382966, 9.944875557,
												9.944875557, 732.7401935 } },
								{
										{ 5, 9, 3 },
										{ 0.0001232006091, 0, 0, 8116.842988 },
										{ 0.05690951417, 3.911922573,
												3.911922573, 286.4747390 } },
								{
										{ 5, 9, 5 },
										{ 0.0001233993924, 0, 0, 8103.767616 },
										{ 0.05294820639, 2.861446299,
												2.861446299, 173.5257065 } },
								{
										{ 5, 9, 7 },
										{ 0.0001234087252, 0, 0, 8103.154770 },
										{ 0.1662353684, 4.924430997,
												4.924430997, 151.8931915 } },
								{
										{ 5, 9, 9 },
										{ 0.0001234096665, 0, 0, 8103.092961 },
										{ 1.030068062, 12.30143598,
												12.30143598, 147.8788953 } },
				
								{
										{ 7, 7, 1 },
										{ 0.0007250019797, 0, 0, 1379.306578 },
										{ 0.01452160541, 4.362286731,
												4.362286731, 1379.292782 } },
								{
										{ 7, 7, 3 },
										{ 0.0008888933264, 0, 0, 1124.994384 },
										{ 0.02570996544, 5.284215040,
												5.284215040, 1124.969563 } },
								{
										{ 7, 7, 5 },
										{ 0.0009088058030, 0, 0, 1100.345087 },
										{ 0.1422462003, 12.46995601,
												12.46995601, 1100.203749 } },
								{
										{ 7, 7, 7 },
										{ 0.0009114672432, 0, 0, 1097.132132 },
										{ 1.0068385588, 33.20575445,
												33.20575445, 1096.126205 } },
								{
										{ 7, 7, 9 },
										{ 0.0009118268310, 0, 0, 1096.699467 },
										{ 7.395819727, 89.75132395,
												89.75132395, 1089.304560 } },
						
								{
										{ 7, 9, 1 },
										{ 0.0001120012728, 0, 0, 8928.469963 },
										{ 0.009028563638, 4.419470853,
												4.419470853, 2274.085164 } },
								{
										{ 7, 9, 3 },
										{ 0.0001228435998, 0, 0, 8140.432236 },
										{ 0.007283161846, 2.890120631,
												2.890120631, 1284.167160 } },
								{
										{ 7, 9, 5 },
										{ 0.0001233507778, 0, 0, 8106.961449 },
										{ 0.02256096962, 4.934528961,
												4.934528961, 1123.603129 } },
								{
										{ 7, 9, 7 },
										{ 0.0001234021413, 0, 0, 8103.587095 },
										{ 0.1395005133, 12.34811530,
												12.34811530, 1100.181983 } },
								{
										{ 7, 9, 9 },
										{ 0.0001234087754, 0, 0, 8103.151471 },
										{ 1.0041489470, 33.16136648,
												33.16136648, 1096.128448 } } ,
				
						{
								{ 9, 9, 1 },
								{ 0.00009811821340, 0, 0, 10191.78770 },
								{ 0.001965298606, 4.362327645, 4.362327645,
										10191.78583 } },
						{
								{ 9, 9, 3 },
								{ 0.0001202984803, 0, 0, 8312.656960 },
								{ 0.003479496710, 5.284302306, 5.284302306,
										8312.653601 } },
						{
								{ 9, 9, 5 },
								{ 0.0001229933398, 0, 0, 8130.521550 },
								{ 0.01925112231, 12.47081780, 12.47081780,
										8130.502422 } },
						{
								{ 9, 9, 7 },
								{ 0.0001233535265, 0, 0, 8106.780799 },
								{ 0.1362621680, 33.22091173, 33.22091173,
										8106.644660 } },
						{
								{ 9, 9, 9 },
								{ 0.0001234021917, 0, 0, 8103.583789 },
								{ 1.0009255639, 90.05044389, 90.05044389,
										8102.582987 } } 
		};
		
		
		
		return mm;
	}
	
	public static double[][][] param3(){
		 
			double[][][] mm=	{ {{1,2,2},{0.1352005399,0,0,7.396420167},
				{1.545153639,1.182362957,1.182362957,1.551937685}}, 
				{{2,2,3},{0.1350201177,0,0,7.406303719},
					{3.109409080,3.575001536,3.575001536,4.431914757}}, 
					{{3,3,5},{0.04976890642,0,0,20.09286665},
						{7.538115504,9.696098598,9.696098598,12.60452005}}, 
						{{7,8,9},{0.0003354550672,0,0,2981.025174},
							{2.722737985,54.57042254,54.57042254,1094.093898}}, 
							{{5,7,9},{0.0009118754623,0,0,1096.640979},
								{7.417217545,32.33072346,32.33072346,141.0604008}},
								{{2,3,3},{0.04947464804,0,0,20.21237219},{1.531431782,2.937177356,2.937177356,6.286281200}},
								{{5,7,5},{0.0009114469872,0,0,1097.156515},{0.1666540894,4.930934488,4.930934488,151.8961521}}, 
								{{8,9,9},{0.0001234070047,0,0,8103.267742},{1.0016452109,54.62929282,54.62929282,2980.456155}}};	

	return mm;
}
	


	public static double[][][] param1(){
		double[][][] mm =
			{  
		{{1,2,1},{0.1249943743,0,0,8.000360059},{1.989056482,1.588122535,1.588122535,1.770755743}},   
		{{1,2,2},{0.1352005399,0,0,7.396420167},{1.545153639,1.182362957,1.182362957,1.551937685}},   
		{{1,3,1},{0.04585808115,0,0,21.80640740},{2.409381236,2.392549306,2.392549306,2.790879285}},   
		{{1,3,2},{0.04926553768,0,0,20.29816474},{1.659865327,1.510269232,1.510269232,1.976614066}},  
		{{1,3,3},{0.04977772803,0,0,20.08930579},{1.518367537,1.184088420,1.184088420,1.582005231}},  
		{{1,4,1},{0.01695525019,0,0,58.97878172},{3.423870082,3.762484801,3.762484801,4.426655076}},   
		{{1,4,2},{0.01812304282,0,0,55.17837209},{2.139276409,2.230164957,2.230164957,2.792362740}},   
		{{1,4,3},{0.01828961041,0,0,54.67585024},{1.617283808,1.497156945,1.497156945,2.004273401}},   
		{{1,4,4},{0.01831515559,0,0,54.59959076},{1.514112910,1.184234438,1.184234438,1.586679031}},   
		{{1,5,1},{0.006254299508,0,0,159.8900083},{5.310969648,6.084036741,6.084036741,7.157921357}},   
		{{1,5,2},{0.006671879022,0,0,149.8828136},{3.143753374,3.516609458,3.516609458,4.251778205}},   
		{{1,5,3},{0.006728375866,0,0,148.6242772},{2.105717029,2.209571678,2.209571678,2.793446090}},  
		{{1,5,4},{0.006736650677,0,0,148.4417180},{1.611555609,1.495333027,1.495333027,2.008010673}},  
		{{1,5,5},{0.006737922814,0,0,148.4136918},{1.513526158,1.184252239,1.184252239,1.587321999}},   
		{{2,2,1},{0.1110655434,0,0,9.003692499},{1.855068668,3.530895396,3.530895396,7.259689375}},   
		{{2,2,2},{0.1294694625,0,0,7.723829087},{1.884096079,3.201023425,3.201023425,5.969202470}},   
		{{2,2,3},{0.1350201177,0,0,7.406303719},{3.109409080,3.575001536,3.575001536,4.431914757}},    
		{{2,3,1},{0.04375138216,0,0,22.85642077},{1.216321620,3.318756542,3.318756542,9.877440955}},   
		{{2,3,2},{0.04838283598,0,0,20.66848666},{1.087571272,2.684624251,2.684624251,7.546362781}},   
		{{2,3,3},{0.04947464804,0,0,20.21237219},{1.531431782,2.937177356,2.937177356,6.286281200}},   
		{{2,3,4},{0.04976950142,0,0,20.09262644},{2.980786467,3.549112517,3.549112517,4.561279318}},   
		{{2,4,1},{0.01665297325,0,0,60.04933684},{1.266334326,4.073810758,4.073810758,13.89517265}},   
		{{2,4,2},{0.01800121357,0,0,55.55181023},{0.9373185187,2.812967412,2.812967412,9.508812087}},   
		{{2,4,3},{0.01824404940,0,0,54.81239269},{0.9929038968,2.555178064,2.555178064,7.582742864}},   
		{{2,4,4},{0.01829993522,0,0,54.64500217},{1.484134184,2.897028533,2.897028533,6.328790499}},   
		{{2,4,5},{0.01831475135,0,0,54.60079587},{2.962708251,3.545075975,3.545075975,4.579446411}},   
		{{2,5,1},{0.006212385334,0,0,160.9687658},{1.713645644,5.926376454,5.926376454,21.07900079}},   
		{{2,5,2},{0.006655273858,0,0,150.2567770},{1.103854594,3.675251364,3.675251364,13.14255761}},   
		{{2,5,3},{0.006722185754,0,0,148.7611376},{0.9028483045,2.746842937,2.746842937,9.464653229}},   
		{{2,5,4},{0.006734371054,0,0,148.4919664},{0.9802868909,2.537322741,2.537322741,7.587581516}},   
		{{2,5,5},{0.006737164143,0,0,148.4304047},{1.477744911,2.891508744,2.891508744,6.334532260}},   
		{{3,3,1},{0.03974988964,0,0,25.15730255},{0.7770460162,4.239748661,4.239748661,24.42000642}},   
		{{3,3,2},{0.04647583434,0,0,21.51655832},{0.8089300782,3.973489528,3.973489528,20.75410407}},   
		{{3,3,3},{0.04871346717,0,0,20.52820417},{1.365874181,5.023929562,5.023929562,19.21104346}},   
		{{3,3,4},{0.04950519347,0,0,20.19990086},{3.012824021,7.136581001,7.136581001,17.23658203}},   
		{{3,3,5},{0.04976890642,0,0,20.09286665},{7.538115504,9.696098598,9.696098598,12.60452005}},   
		{{3,4,1},{0.01599878254,0,0,62.50475607},{0.4784777341,3.655388720,3.655388720,30.01574718}},  
		{{3,4,2},{0.01772339461,0,0,56.42259972},{0.4341427977,3.014998251,3.014998251,23.24169492}},   
		{{3,4,3},{0.01813786254,0,0,55.13328804},{0.6235703156,3.457735196,3.457735196,20.77701962}},   
		{{3,4,4},{0.01826063601,0,0,54.76260516},{1.229566469,4.771356754,4.771356754,19.32863808}},   
		{{3,4,5},{0.01830145724,0,0,54.64045769},{2.908809977,7.031042895,7.031042895,17.33889961}},  
		{{3,5,1},{0.006116563864,0,0,163.4904862},{0.4891799504,4.371128310,4.371128310,41.10299837}},  
		{{3,5,2},{0.006616538111,0,0,151.1364377},{0.3651006616,3.052885315,3.052885315,28.26647506}},  
		{{3,5,3},{0.006707654789,0,0,149.0834027},{0.3931294341,2.842608414,2.842608414,23.09779377}},   
		{{3,5,4},{0.006728999836,0,0,148.6104955},{0.5993894364,3.384526960,3.384526960,20.77951660}},   
		{{3,5,5},{0.006735197728,0,0,148.4737405},{1.211334334,4.736292366,4.736292366,19.34434178}},   
		{{4,4,1},{0.01457026611,0,0,68.63292631},{0.2908828098,4.345545292,4.345545292,68.35661377}},   
		{{4,4,2},{0.01704018565,0,0,58.68480665},{0.3037687351,4.091394666,4.091394666,58.39807810}},   
		{{4,4,3},{0.01786299882,0,0,55.98164171},{0.5144998408,5.248526095,5.248526095,55.48500487}},   
		{{4,4,4},{0.01815627185,0,0,55.07738640},{1.136861997,7.768112607,7.768112607,53.95868068}},   
		{{4,4,5},{0.01826288385,0,0,54.75586487},{2.845419020,12.11441024,12.11441024,51.92870873}},   
		{{4,5,1},{0.005880900822,0,0,170.0419766},{0.1776235646,3.703270403,3.703270403,82.83929956}},   
		{{4,5,2},{0.006516315367,0,0,153.4609582},{0.1614757642,3.062493331,3.062493331,64.27506601}},   
		{{4,5,3},{0.006669305620,0,0,149.9406470},{0.2325392198,3.533894886,3.533894886,58.00489516}},   
		{{4,5,4},{0.006714711578,0,0,148.9267243},{0.4594637279,4.948898406,4.948898406,55.48119228}},   
		{{4,5,5},{0.006729922034,0,0,148.5901315},{1.088048993,7.599643723,7.599643723,53.99994405}},   
		{{5,5,1},{0.005357482394,0,0,186.6548364},{0.1072621385,4.360053487,4.360053487,186.5529318}},   
		{{5,5,2},{0.006265884262,0,0,159.5943937},{0.1120609215,4.107607496,4.107607496,159.4885987}},   
		{{5,5,3},{0.006568528959,0,0,152.2410887},{0.1898792271,5.279451994,5.279451994,152.0577780}},   
		{{5,5,4},{0.006676414030,0,0,149.7810045},{0.4196694046,7.853991626,7.853991626,149.3680115}},   
		{{5,5,5},{0.006715666026,0,0,148.9055585},{1.050491565,12.42286422,12.42286422,147.8617826}}   };	
		
		return mm;
	}

}
