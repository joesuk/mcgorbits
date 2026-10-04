package boundary;

public class PrimitiveDoublyPalCyclicWord extends PrimitiveCyclicWord{
//	public PrimitiveDoublyCyclicWord(){
//	}
	public PrimitiveDoublyPalCyclicWord(int[] p, int b) {
		super(p, b);
	}
	public PrimitiveDoublyPalCyclicWord next(){
		PrimitiveCyclicWord v = super.next();
		while(true){
			if(v.isDoublyPalSmallest()){
				return new PrimitiveDoublyPalCyclicWord(v.word,base);
			}
			v = v.next();
		}
	}
}