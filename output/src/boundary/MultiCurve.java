package boundary;
import java.util.*;
public class MultiCurve {
	SurfaceWord sw;
	public ArrayList<Curve> curves;
	/**
	 * Constructor
	 *                            
	 * Construct a MultiCurve from a list of Curves
	@param c the list of curves
	 *
	@return the new MultiCurve
	 */
	public MultiCurve(ArrayList<Curve> c){
		curves = c;
		sw = c.get(0).sw;
	}
	/**
	 * Return the size of the multicurve
	 *                            
	 * The size of the multicurve is the amount of components.
	@return the size
	 */
	public int size(){
		return curves.size();
	}
	/**
	 * Return the length of the multicurve
	 *                            
	 * The length of the multicurve is the sum of the length
	 * of each component.
	@return the length
	 */
	public int length(){
		int sum=0;
		for(int i=0;i<curves.size();i++){
			sum+=curves.get(i).length();
		}
		return sum;
	}
	/**
	 * Return the string representation of the multicurve
	 *                            
	@return the string representation
	 */
	public String toString(){
		return curves.toString();
	}
	/**
	 * Return the amount of intersections of the multicurve
	 *                            
	@return the amount of intersections of the multicurve   
	 */
	public int intersections(){
		int sum = 0;
		if(curves.size()==1){
			return curves.get(0).selfIntersection();
		}
		for(int i=0;i<curves.size();i++){
			Curve p = curves.get(i);
			for(int j=i+1;j<curves.size();j++){
				Curve p1 = curves.get(j);
				sum += p.intersection(p1);
			}
			sum+= p.selfIntersection();
		}
		return sum;
	}
	/**
	 * Return max(i(a,b)) for each curve
	 *                            
	@return the maximum amount of intersections          
	 */
	public int maxMutualIntersections(){
		int max = 0;
		for(int i=0;i<size();i++){
			for(int j=i+1;j<size();j++){
				max = Math.max(max,curves.get(i).intersection(curves.get(j)));
			}
		}
		return max;
	}
}
