package boundary;
import java.util.*;
public class MultiCurveIterator {
	ArrayList<ArrayList<CyclicWord>> c;
	int partition_index;
	ArrayList<Integer> vector;
	ArrayList<ArrayList<Integer>> partitions;
	ArrayList<Integer> base;
	SurfaceWord sw;
	int length;
	public MultiCurveIterator(){
	}
	public void reset(){
		partition_index = -1;
		nextPartition();
	}
	public MultiCurveIterator(CurveList a, int n, SurfaceWord s, ArrayList<ArrayList<Integer>> part){
		length = n;
		sw = s;
		c = new ArrayList<ArrayList<CyclicWord>>();
		for(int i=0;i<n+1;i++){
			c.add(new ArrayList<CyclicWord>());
		}
		for(int i=0;i<a.size();i++){
			int[] t = a.get(i).baseword.word;
			CyclicWord o = new CyclicWord(t, a.sw.length());
			//System.out.println(o);
			if(t.length<n+1){
				c.get(t.length).add(o);
			}
			
		}
		//Only use valid partitions
		partitions = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<part.size();i++){
			if(isValidPartition(part.get(i))){
				partitions.add(part.get(i));
			}
		}
		partition_index = -1;
		nextPartition();
		//for(int i=0;i<10;i++){
		//	System.out.println(partitions.get(i));
		//}
		//System.out.println(partitions.get(1));
	}
	
	public boolean isValidPartition(ArrayList<Integer> b){
		ArrayList<Integer> a = base(b);
		for(int i=0;i<a.size();i++){
			if(a.get(i)<1){
				return false;
			}
		}
		return true;
	}
	
	public MultiCurve next(){
		if(vector!=null){
			ArrayList<Curve> curves = new ArrayList<Curve>();
			ArrayList<Integer> partition = partitions.get(partition_index);
			//System.out.println(partition);
			//System.out.println(vector);
			//System.out.println(base);
			for(int i=0;i<vector.size();i++){
				curves.add(new Curve(c.get(partition.get(i)).get(vector.get(i)), sw));
			}
			addOne();
			return new MultiCurve(curves);
		}
		return null;
	}
	public ArrayList<Integer> base(ArrayList<Integer> p){
		ArrayList<Integer> b = new ArrayList<Integer>();
		for(int i=0;i<p.size();i++){
			//if(c.get(p.get(i)).size()==0){
			//	return null;
			//}
			b.add(c.get(p.get(i)).size());
		}
		
		for(int i=p.size()-1;i>0;i--){
			if(p.get(i)==p.get(i-1)){
				b.set(i-1,b.get(i)-1);
			}
		}
		return b;
	}
	
	public boolean hasNext(){
		if(vector==null){
			return false;
		}
		return true;
	}
	
	public void addOne(){
		if(vector==null){
			return;
		}
		//Loop in the current partition.
		ArrayList<Integer> partition = partitions.get(partition_index);
		
		//
		int t = vector.size()-1;
		vector.set(t, vector.get(t)+1);
		while(base.get(t)<= vector.get(t)){
			if(t==0){
				nextPartition();
				return;
			}
			t=t-1;
			vector.set(t, vector.get(t)+1);
		}
		
		//we have located the point, such that we 'zero' everything beyond
		
		for(int i=t+1;i<partition.size();i++){
			if(partition.get(i-1)==partition.get(i)){
				vector.set(i, vector.get(i-1)+1);
			}else{
				vector.set(i, 0);
			}
		}
		
	}

	public void nextPartition(){
		partition_index++;
		if(partition_index<partitions.size()){
			ArrayList<Integer> partition = partitions.get(partition_index);
			base = base(partition);
			vector = new ArrayList<Integer>();
			for(int i=0;i<partition.size();i++){
				vector.add(0);
			}
			//System.out.println(vector.size());
			for(int i=1;i<partition.size();i++){
				if(partition.get(i-1)==partition.get(i)){
					vector.set(i, vector.get(i-1)+1);
				}else{
					vector.set(i, 0);
				}
			}
		}else{
			vector = null;
		}
	}
	
}
