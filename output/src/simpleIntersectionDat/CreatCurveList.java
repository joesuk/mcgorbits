//DO NOT RUN this code when the databases have already been generated!
package simpleIntersectionDat;

import java.util.*;
import java.io.*;

public class CreatCurveList {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		String n = "4";
		String filenameClean = "n" + n + "clean.tmp";
		String filenameNormal = "n" + n + ".tmp";
		String filenameCurve = "n" + n + "CurvePants.tmp";
		
		/* Create and clean word lists 
		FileInputStream fis = new FileInputStream(filenameNormal);
		ObjectInputStream ois = new ObjectInputStream(fis);
		List<ArrayList<Integer>> list = (List<ArrayList<Integer>>) ois.readObject();
		ois.close();
		
		List<ArrayList<Integer>> newlist = CleanList.clean1(list);
		
		FileOutputStream fos = new FileOutputStream(filenameClean);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(newlist);
		oos.close();
		
		System.out.println(newlist);
		*/
		
		/* Bad code for easy cases
		for (int j =0; j < 4; j++){
			if (CleanList.lexi1(a, CleanList.permute(a,j)) == 0 && CleanList.lexi1(b, CleanList.permute(b,j)) < 0){
				write = 0; break;
			}
		}
		*/
		
		//Create clean curve lists
		/**/
		FileInputStream fisa = new FileInputStream(filenameClean);
		ObjectInputStream oisa = new ObjectInputStream(fisa);
		List<ArrayList<Integer>> lista = (List<ArrayList<Integer>>) oisa.readObject();
		oisa.close();
		
		FileInputStream fisb = new FileInputStream(filenameNormal);
		ObjectInputStream oisb = new ObjectInputStream(fisb);
		List<ArrayList<Integer>> listb = (List<ArrayList<Integer>>) oisb.readObject();
		oisb.close();
		
		ArrayList<CurvePants> CurveList = new ArrayList<CurvePants>();
		for (ArrayList<Integer> a: lista){
		for (ArrayList<Integer> b: listb){
			int write = 1;
			int a1 = a.get(0); int a2 = a.get(1); int a3 = a.get(2); int a4 = a.get(3);
			if (a1 == a2 && a2 == a3 && a3 == a4){
				for (int j = 0; j < 4; j++){
					if (CleanList.lexi1(b, CleanList.permute(b, j)) < 0){ write = 0; break;}
				}
			} else if (a1 == a3 && a2 == a4){
				if (CleanList.lexi1(b, CleanList.permute(b, 2)) < 0){ write = 0;}
			} 
			if (write == 1) {CurveList.add(new CurvePants(a,b));}
		}
		}
		
		for (CurvePants x: CurveList){
			x.setlongword();
			x.setIntersectNum();
		}
		
		Collections.sort(CurveList);
				
		FileOutputStream fos = new FileOutputStream(filenameCurve);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(CurveList);
		oos.close();
		
		System.out.println(CurveList.size());
	
	}
}
