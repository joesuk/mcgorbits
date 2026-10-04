package boundary;

import java.util.Comparator;

public class SortByHyperbolicTrace implements Comparator<Curve>{
    public int compare(Curve c1, Curve c2) {
    	double c1h = c1.hyperbolicTrace();
    	double c2h = c2.hyperbolicTrace();
        if(c1h>c2h){
        	return 1;
        }else if(c1h<c2h){
        	return -1;
        }
        return 0;
    }
}