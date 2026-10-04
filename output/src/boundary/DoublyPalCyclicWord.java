package boundary;

public class DoublyPalCyclicWord extends CyclicWord{
	public DoublyPalCyclicWord(int[] p, int b) {
		super(p, b);
	}
	public DoublyPalCyclicWord next(){
		CyclicWord v = super.next();
		while(true){
			if(v.isDoublyPalSmallest()){
				return new DoublyPalCyclicWord(v.word, base);
			}
			v = v.next();
		}
	}	
}
