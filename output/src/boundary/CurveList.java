package boundary;
import java.util.*;
import java.io.*;
//This class read and write word lists

public final class CurveList {
	public SurfaceWord sw;
	public ArrayList<Word> list = new ArrayList<Word>();
	int index;
	/**
	 * The constructor of the CurveList
	 * 
	 * Java have type erasure feature that requires
	 * us to input the class of Curve and CyclicWords
	 * in order to actually use these classes in reflections
	 * 
	@param  s  a SurfaceWord associated to the list
	 *
	@param  t  The class of the Curve
	 *
	@param  c  The class of the CyclicWord
	 *
	@return The newly constructed CurveList              
	 */
	public CurveList(SurfaceWord s){
		sw = s;
		list = new ArrayList<Word>();
		index = 0;
	}

	public CurveList(ArrayList<Curve> a){
		sw = a.get(0).sw;
		list = new ArrayList<Word>();
		for(int i=0;i<a.size();i++){
			this.add(a.get(i));
		}
		index = 0;
	}
	public ArrayList<Curve> toArrayList(){
		ArrayList<Curve> a = new ArrayList<Curve>();
		for(int i=0;i<list.size();i++){
			a.add(this.get(i));
		}
		return a;
	}
	/**
	 * Populate the CurveList by reading from a file
	 * 
	@param  filename  The name of the file        
	 */
	public void readList(String filename){
	      try{
	    	  Scanner in = new Scanner(new FileReader(new File(filename)));
	    	  while(in.hasNext()){
	    		  list.add(Word.fromString(in.next(), sw.length()));
	    	  }
	      }catch (Exception e){//Catch exception if any
	    	  System.err.println("Error: " + e.getMessage());
	      }
	}
	/**
	 * Save the CurveList as a file
	 * 
	@param  filename  The name of the file        
	 */	
	public void writeList(String filename){
	      try{
	    	    // Create file 
	    	    FileWriter fstream = new FileWriter(filename);
	    	    BufferedWriter out = new BufferedWriter(fstream);
	    		for(int i=0;i<list.size();i++){
	    			CyclicWord t = new CyclicWord(list.get(i));
	    			out.write(t.toString());
	    			out.write("\n");
	    		}
	    	    out.close();
	     }catch (Exception e){//Catch exception if any
	    	      System.err.println("Error: " + e.getMessage());
	   	 }
	}
	/**
	 * Return if there exist the next curve
	 *
	@return True if there is a next curve, false if there isn't    
	 */
	public boolean hasNext(){
		if(index>= list.size()){
			return false;
		}
		return true;
	}
	/**
	 * Return the next curve
	 *
	@return the next curve  
	 */	
	public Curve next() {
		if(index<list.size()){
			CyclicWord c = new CyclicWord(list.get(index));
			index++;
			Curve t = new Curve(c,sw);
			return t;
		}
		return null;
	}
	
	/*public int[] get(int i){
		return list.get(i);
	}*/
	/**
	 * Return the amount of curves in the CurveList
	 *
	@return the amount of curves in the CurveList
	 */	
	public int size(){
		return list.size();
	}
	public int maxLength(){
		int max = 0;
		for(int i=0;i<list.size();i++){
			max = Math.max(max, list.get(i).length());
		}
		return max;
	}
	/**
	 * Add another curve by it's array representation
	 * 
	 @param  p the curve's array representation
	 */	
	public void add(Word p){
		list.add(p);
	}
	
	public Curve get(int i){
		CyclicWord cw = new CyclicWord(list.get(i));
		return new Curve(cw,sw);
	}
	/**
	 * Add another curve by it's array representation
	 * 
	 @param  c the curve   
	 */	
	public void add(Curve c){
		list.add(c.baseword);
	}
	/**
	 * Add all the curves in another CurveList into the current CurveList
	 * 
	 @param  c the CurveList
	 */	
	public void addAll(CurveList c){
		while(c.hasNext()){
			add(c.next());
		}
	}
	public void add(CyclicWord c){
		list.add(c);
	}
	public CurveList sample(int m){
		//randomly pick m curves
		//pick random m elemnt from n items
		//from Programming Pearls by Jon Bentley 
		//O(m) time, O(n) space
		int[] x = new int[size()];
		for(int i=0;i<size();i++){
			x[i] = i;
		}
		Random r = new Random();
		for(int i=0; i<m; i++){
			int j = r.nextInt(size()-1-i)+i;
			//exchange the values
			x[i] ^= x[j];
			x[j] ^= x[i];
			x[i] ^= x[j];
		}
		CurveList sample = new CurveList(sw);
		//pick the items
		for(int i=0;i<m;i++){
			sample.add(this.get(x[i]));
		}
		return sample;
	}
	public CurveList sortedCurveList(){
		ArrayList<Curve> a = new ArrayList<Curve>();
		for(int i=0;i<list.size();i++){
			a.add(this.get(i));
		}
		Collections.sort(a);
		CurveList c = new CurveList(this.sw);
		for(int i=0;i<a.size();i++){
			c.add(a.get(i));
		}
		return c;
	}
	public CurveList sortedCurveList(Comparator<Curve> comp){
		ArrayList<Curve> a = new ArrayList<Curve>();
		for(int i=0;i<list.size();i++){
			a.add(this.get(i));
		}
		Collections.sort(a,comp);
		CurveList c = new CurveList(this.sw);
		for(int i=0;i<a.size();i++){
			c.add(a.get(i));
		}
		return c;
	}
	/**
	 * Reset, so next() start from 0th curve
	 */	
	public void reset(){
		index=0;
	}
	/**
	 * Return the string representation of CurveList
	 */	
	public String toString(){
		if (this.size()==0) return "";
		String s = "[";
		for(int i=0;i<this.size()-1;i++){
			s += this.get(i).toString() + ", ";
		}
		s += this.get(this.size()-1).toString() + "]";
		return s;
	}
}
