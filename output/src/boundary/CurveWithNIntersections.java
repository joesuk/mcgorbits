package boundary;

public class CurveWithNIntersections extends Curve{
	int intersections;
	//This is the standard constructor, CurveList can only use the following
	public CurveWithNIntersections(CyclicWord c, SurfaceWord s) {
		super(c, s);
		intersections = 0;
	}
	public CurveWithNIntersections(CyclicWord c, SurfaceWord s, int n) {
		super(c, s);
		setIntersections(n);
	}
	public int getIntersections(){
		return intersections;
	}
	public void setIntersections(int n){
		intersections = n;
	}
	public CurveWithNIntersections next(){
		//Find the next Curve
		Curve next = super.next();
		while(true){
			//Test if it have n intersections
			if(next.selfIntersection()==intersections){
				return new CurveWithNIntersections((CyclicWord) next.baseword, sw, intersections);
			}
			//If not, find the next Curve
			next = next.next();
		}
	}
}
