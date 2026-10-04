package boundary;
import java.util.*;

public class IntersectionStatistic {
	
	public static int[] intersectionCount(MultiCurveIterator m){
		HashMap<Integer,Integer> counts = new HashMap<Integer, Integer>();
		while(m.hasNext()){
			MultiCurve c = m.next();
			int in = c.intersections();
			if(counts.containsKey(in)){
				counts.put(in, counts.get(in)+1);
			}else{
				counts.put(in, 1);
			}
		}
		Integer[] t = new Integer[counts.keySet().size()];
		counts.keySet().toArray(t);
		int max = 0;
		for(int i=0;i<t.length;i++){
			max = Math.max(t[i],max);
		}
		int[] r = new int[max+1];
		for(int i=0;i<t.length;i++){
			r[t[i]] = counts.get(t[i]);
		}
		m.reset();
		return r;
	}
	
	public static int maxIntersection(MultiCurveIterator m){
		int max = 0;
		while(m.hasNext()){
			max = Math.max(max,m.next().intersections());
		}
		m.reset();
		return max;
	}
}
