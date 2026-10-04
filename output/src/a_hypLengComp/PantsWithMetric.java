package a_hypLengComp;

import hypLengthComp.LengthSamples;
import boundary.SurfaceWord;
import boundary.Word;

public class PantsWithMetric {
	double lA;
	double lB;
	double lC;
	SurfaceWord sw ;
	double cte ;
	double mat1[][];
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


	
	
	public PantsWithMetric(double la, double lb, double lc, int wl){
		lA=la;
		lB=lb;
		lC=lc;
		sw= new SurfaceWord("aAbB");
		 mat1 = LengthSamples.computeMatrix(la,lb,lc);
		sw.setHyperbolicMatrix(mat1);
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
		return lA+"\t"+lB+"\t"+lC+"\t"+aux[0]+"\t"+aux[1]+"\t"+aux[2]+"\t"+this.maxHL;
	}
	public double[][] getHist() {
		return hist;
	}

	public String giveLengthBC(){
		return lA+"\t"+lB+"\t"+lC;
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
    	statics.FileStuff.fileMatrix(hist, filename+"_"+lA+"_"+lB+"_"+lC	);
    }



	public double[][] getMat1() {
		return mat1;
	}

}
