package simpleIntersectionDat;

import java.util.*;
import java.io.*;

public class CleanList {
	
	public static int lexi1 (ArrayList<Integer> a, ArrayList<Integer> b){
		int ans = 0;
		for (int i = 0; i < a.size();i++){
			int currentA = a.get(i);
			int currentB = b.get(i);
			if (currentA > currentB) {ans = 1; break;}
			else if (currentA < currentB) {ans = -1; break;}
		}
		return ans;
	}
	
	public static int lexi2 (CurvePants c1, CurvePants c2){
		ArrayList<Integer> a1 = c1.getA(); ArrayList<Integer>a2 = c2.getA();
		int ans = lexi1(a1,a2);
		if (ans == 0){
			ArrayList<Integer> b1 = c2.getB(); ArrayList<Integer> b2 = c2.getB();
			ans = lexi1(b1,b2);
		}
		return ans;
	}
	
	public static ArrayList<Integer> permute (ArrayList<Integer> list, int n){
		ArrayList<Integer> perm = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++){
			int j = (i + n)%list.size();
			int current = list.get(j);
			perm.add(current);
		}
		return perm;
	}
	
	public static List<ArrayList<Integer>> clean1 (List<ArrayList<Integer>> biglist){
		List<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> checked = new ArrayList<Integer>();
		for (int j = 0; j < biglist.size(); j++){
			if (!checked.contains(j)){
				ArrayList<Integer> current = biglist.get(j);
				int tempMax = 0;
				for (int k = 0; k < current.size(); k++){
					ArrayList<Integer> currentpermute = permute(current,k);
					if (lexi1(currentpermute,permute(current,tempMax)) == 1){tempMax = k;}
					for (ArrayList<Integer> search: biglist){
						if (lexi1(currentpermute, search) == 0) {checked.add(biglist.indexOf(search));}
					}
				}
				ans.add(permute(current,tempMax));
				
			}
		}
		return ans;
	}
	


}
