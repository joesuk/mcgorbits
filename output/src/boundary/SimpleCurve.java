package boundary;

public class SimpleCurve extends Curve {
	public SimpleCurve(PrimitiveCyclicWord c, SurfaceWord s) {
		super(c, s);
	}
	public SimpleCurve next(){
		//Find the next Curve
		Curve next = super.next();
		while(true){
			//Test if it's simple
			if(next.selfIntersection(true)==0){
				return new SimpleCurve((PrimitiveCyclicWord) next.baseword, sw);
			}
			//If not, find the next Curve
			next = next.next();
		}
	}
}
