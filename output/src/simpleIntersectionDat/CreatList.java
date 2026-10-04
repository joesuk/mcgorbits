package simpleIntersectionDat;
/* DO NOT RUN this code when the databases have already been generated!
package simpleIntersection;
*/
import java.util.*;
import java.io.*;

public class CreatList implements Serializable {
	   private static final long serialVersionUID = 1L;
	   
	public static void main (String [] args) throws IOException {
		List<ArrayList<Integer>> testlist1 = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> testlist2 = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> testlist3 = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> testlist4 = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> testlist5 = new ArrayList<ArrayList<Integer>>();
		
		List<Integer> consider = Arrays.asList(-1,-2,-3,-4,1,2,3,4);
		
		for (int i0: consider) { testlist1.add(new ArrayList<Integer> (Arrays.asList(i0)));}
		
		for (int i0: consider) {
		for (int i1: consider) {
			testlist2.add(new ArrayList<Integer> (Arrays.asList(i0,i1)));
		}}
		
		for (int i0: consider) {
		for (int i1: consider) {
		for (int i2: consider) {
			testlist3.add(new ArrayList<Integer> (Arrays.asList(i0,i1,i2)));
		}}}
		
		for (int i0: consider) {
		for (int i1: consider) {
		for (int i2: consider) {
		for (int i3: consider) {
			testlist4.add(new ArrayList<Integer> (Arrays.asList(i0,i1,i2,i3)));	
		}}}}
		
		for (int i0: consider) {
		for (int i1: consider) {
		for (int i2: consider) {
		for (int i3: consider) {
		for (int i4: consider) {
			testlist5.add(new ArrayList<Integer> (Arrays.asList(i0,i1,i2,i3,i4)));
		}}}}}
		
		FileOutputStream fos1 = new FileOutputStream("n1.tmp");
		ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
		oos1.writeObject(testlist1);
		oos1.close();
		
		FileOutputStream fos2 = new FileOutputStream("n2.tmp");
		ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
		oos2.writeObject(testlist2);
		oos2.close();
		
		FileOutputStream fos3 = new FileOutputStream("n3.tmp");
		ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
		oos3.writeObject(testlist3);
		oos3.close();
		
		FileOutputStream fos4 = new FileOutputStream("n4.tmp");
		ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
		oos4.writeObject(testlist4);
		oos4.close();
		
		FileOutputStream fos5 = new FileOutputStream("n5.tmp");
		ObjectOutputStream oos5 = new ObjectOutputStream(fos5);
		oos5.writeObject(testlist5);
		oos5.close();
	}
	
}
