package examplesOriginal;
import java.util.*;
import boundary.*;

public class Algorithms {
	public static ArrayList<CurveList> equivalentClassByHyperbolicLength(CurveList cl, double epsilon){
		double[] len = new double[cl.size()];
		boolean[] checked = new boolean[cl.size()];
		ArrayList<ArrayList<Curve>> curve = new ArrayList<ArrayList<Curve>>();
		
		for(int i=0;i<cl.size();i++){
			len[i] = cl.get(i).hyperbolicLength();
		}
		
		for(int i=0;i<cl.size();i++){
			ArrayList<Curve> c = new ArrayList<Curve>();
			c.add(cl.get(i));
			for(int j=i+1;j<cl.size();j++){
				if(!checked[j]){
					if(Math.abs(len[i]-len[j])<epsilon){
						c.add(cl.get(j));
						checked[j] = true;
					}
				}
			}
			curve.add(c);
		}
		ArrayList<CurveList> r = new ArrayList<CurveList>();
		for(int i=0;i<curve.size();i++){
			r.add(new CurveList(curve.get(i)));
		}
		return r;
	}
	
	public static ArrayList<CurveList> equivalentClass(CurveList cl, CurveList scl){
		//Let there be set of equivalent classes E_1 to E_n, where n is the size of scl
		//E_k is the set of equivalent class, defined as b, c \in cl is a equivalent class in E_k
		//iff i(c, s) = i(b, s) for all s \in first k element of scl. 
		ArrayList<ArrayList<Curve>> curve = new ArrayList<ArrayList<Curve>>();
		curve.add(new ArrayList<Curve>());
		for(int i=0;i<cl.size();i++){
			curve.get(0).add(cl.get(i));
		}
		for(int i=0;i<scl.size();i++){
			ArrayList<ArrayList<Curve>> new_curve = new ArrayList<ArrayList<Curve>>();
			for(int j=0;j<curve.size();j++){
				if(curve.get(j).size()>1){
					HashMap<Integer, ArrayList<Curve>> h = new HashMap<Integer, ArrayList<Curve>>();
					ArrayList<Integer> key = new ArrayList<Integer>();
					for(int k=0;k<curve.get(j).size();k++){
						int t = curve.get(j).get(k).intersection(scl.get(i));
						if(!h.containsKey(t)){
							h.put(t, new ArrayList<Curve>());
							key.add(t);
						}
						h.get(t).add(curve.get(j).get(k));
					}
					for(int k=0;k<key.size();k++){
						new_curve.add(h.get(key.get(k)));
					}
					
				}else{
					new_curve.add(curve.get(j));
				}
			}
			curve = new_curve;
		}
		ArrayList<CurveList> r = new ArrayList<CurveList>();
		for(int i=0;i<curve.size();i++){
			r.add(new CurveList(curve.get(i)));
		}
		return r;
	}
	
	public static ArrayList<CurveList> equivalentClass(SurfaceWord sw, int m, int k){
		CurveList cl = new CurveList(sw);
		//build the list of curves of length k
		PrimitiveDoublyCyclicWord cw = new PrimitiveDoublyCyclicWord(new int[k], sw.length());
		Curve c = new Curve(cw,sw);
		c.initialize();
		while(c.length()==k){
			cl.add(c);
			c = c.next();
		}
		//build the list of simple curves up to length m
		cw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(cw, sw);
		sc.initialize();
		CurveList scl = new CurveList(sw);
		while(sc.length()!=m+1){
			scl.add(sc);
			sc = sc.next();
		}
		return equivalentClass(cl,scl);
	}
}
