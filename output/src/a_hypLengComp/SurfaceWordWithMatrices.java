package a_hypLengComp;

import hypLengthComp.LengthSamples;
import a_torusPantsHL.HLSI;
import a_torusPantsHL.Parametrizations;
import boundary.SurfaceWord;
import boundary.Word;

public class SurfaceWordWithMatrices {
	
	SurfaceWord sw ;
	double cte ;
	double[][] hist;
	double maxHL;
	double[] lengths = new double[3];
	int maxSI;
	
	
	public SurfaceWord getSw() {
		return sw;
	}



	public void setSw(SurfaceWord sw) {
		this.sw = sw;
	}


	
	
	public SurfaceWordWithMatrices(double sa, double sb, double sc, double sa1,double sb1, double sc1, int wl){
		
		sw= new SurfaceWord("aAbB");
		double[][] mat1 = {{sa,sb,sc, (sb*sc+1)/sa },{sa1,sb1,sc1, (sb1*sc1+1)/sa1 }};
		sw.setHyperbolicMatrix(mat1);
		double la=Parametrizations.singleCurveLength("a", sw);
		double lb=Parametrizations.singleCurveLength("b", sw);
		double lc=Parametrizations.singleCurveLength("ab", sw);
		cte = Math.min(la, Math.min(lb,lc/2));
	     maxSI =(wl*wl/4+wl/2)+2;
		hist = new double[maxSI][3];
		 
		for(int h=0 ; h < maxSI ; h++){
			hist[h][0]=h;
			hist[h][1]=0;
			hist[h][2]=-1;
		}
		
		maxHL= wl*cte;
		
		
	}


	public String giveData(){
		double[] aux = this.stats();
		double la=Parametrizations.singleCurveLength("a", sw);
		double lb=Parametrizations.singleCurveLength("b", sw);
		double lc=Parametrizations.singleCurveLength("ab", sw);
		return la+"\t"+lb+"\t"+lc+"\t"+aux[0]+"\t"+this.maxHL;
	}
	public double[][] getHist() {
		return hist;
	}


	public void setHist(double[][] hist) {
		this.hist = hist;
	}


	public double getMaxHL() {
		return maxHL;
	}


	public void setMaxHL(double maxHL) {
		this.maxHL = maxHL;
	}

    public  double[] stats(){
    	
		double m=0;
		double m2=0;
		double total =0;
     
		for(int h =0 ; h< maxSI ; h++){
			
			m=m+hist[h][0]*hist[h][1];
			m2=m2+hist[h][0]*hist[h][0]*hist[h][1];
			total = total+hist[h][1];
		}
		
		double mean =m/total;
		double var=m2/total-mean*mean;
		//System.out.println(mean);
		double[]  answer={mean/(this.maxHL*this.maxHL), var/(this.maxHL*this.maxHL*this.maxHL),total};
		
		return answer;
		
    }
	 
    public void fileHist(String filename){
    	double la=Parametrizations.singleCurveLength("a", sw);
		double lb=Parametrizations.singleCurveLength("b", sw);
		double lc=Parametrizations.singleCurveLength("ab", sw);
    	statics.FileStuff.fileMatrix(hist, filename+"_"+la+"_"+lb+"_"+lc	);
    }



	public String giveLengthBC() {
		double la=Parametrizations.singleCurveLength("a", sw);
		double lb=Parametrizations.singleCurveLength("b", sw);
		double lc=Parametrizations.singleCurveLength("ab", sw);
		
		return la+"_"+lb+"_"+lc;
	}

}
