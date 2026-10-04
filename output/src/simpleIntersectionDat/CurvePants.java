	package simpleIntersectionDat;

import java.util.*;
import java.io.*;

public class CurvePants implements Comparable<CurvePants>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Integer> a;
	private ArrayList<Integer> b;
	private int l1;
	private int l2;
	private int l3;
	private int w2;
	private int w3;
	private String longword;
		
	//Constructor
	public CurvePants (ArrayList<Integer> x, ArrayList<Integer> y){
		a = x; b = y;
		longword = "";
		l1 = 0; l2 = 0; l3 = 0; w2 = 0; w3 = 0; 
	}
	
	//Getters
	ArrayList<Integer> getA (){return a;}
	ArrayList<Integer> getB (){return b;}
	String getlongword() {return longword;}
		
	//Set correct values to longword and intersection numbers
	public void setlongword(){
		longword = ""; 
		for (int i = 0; i < a.size(); i++){
			int currentA = a.get(i);
			int currentB = b.get(i);
			if (currentA > 0) {for (int j = 0; j < currentA; j++){longword = longword + "a";}}
			if (currentA < 0) {for (int j = 0; j < -currentA; j++){longword = longword + "A";}}
			if (currentB > 0) {for (int j = 0; j < currentB; j++){longword = longword + "b";}}
			if (currentB < 0) {for (int j = 0; j < -currentB; j++){longword = longword + "B";}}
		}
	}
	//Only set intersection numbers AFTER setting longword
	public void setIntersectNum (){
		l1 = 0; l2 = 0;
		for (int i: a){l1 = l1 + Math.abs(i);}
		for (int i: b){l2 = l2 + Math.abs(i);}
		
		
		String newbasis1 = ""; String newbasis2 = ""; 
		for (int i = 0; i < longword.length(); i++){
			if (longword.charAt(i) == 'a') { newbasis1 = newbasis1 + "a"; newbasis2 = newbasis2 + "cB";}
			else if (longword.charAt(i) == 'b') { newbasis1 = newbasis1 + "Ac"; newbasis2 = newbasis2 + "b";}
			else if (longword.charAt(i) == 'A') { newbasis1 = newbasis1 + "A"; newbasis2 = newbasis2 + "bC";}
			else if (longword.charAt(i) == 'B') { newbasis1 = newbasis1 + "Ca"; newbasis2 = newbasis2 + "B";}
		}
		
		l3 = 0; w2 = 0;
		ArrayList<Integer> new1a = intersection.standardize(newbasis1,1).getA();
		ArrayList<Integer> new1c = intersection.standardize(newbasis1,1).getC();
		for (int i: new1a){l3 = l3 + Math.abs(i);}
		if (new1a.size() == 1){
			if (!new1a.contains(0) && !new1c.contains(0)) {w2 = 2;}
		} else {w2 = 2*new1a.size();}
		
		w3 = 0;
		ArrayList<Integer> new2b = intersection.standardize(newbasis2,1).getB();
		ArrayList<Integer> new2c = intersection.standardize(newbasis2,1).getC();
		if (new2b.size() == 1){
			if (!new2b.contains(0) && !new2c.contains(0)) {w3 = 2;}
		} else {w3 = 2*new2b.size();}
		
	}
	
	//Override comparator
	public int compareTo(CurvePants y){
		int ans = 0;
		if (this.l1 != y.l1) {ans = this.l1 - y.l1;}
		else if (this.l2 != y.l2) {ans = this.l2 - y.l2;}
		else if (this.l3 != y.l3) {ans = this.l3 - y.l3;}
		else if (this.w2 != y.w2) {ans = this.w2 - y.w2;}
		else {ans = this.w3 - y.w3;}
		return ans;
	}
	
	public int size (){return a.size();}
	
	public String shortword(){
		String ans = "";
		for (int i = 0; i < a.size(); i++){
			int currentA = a.get(i);
			int currentB = b.get(i);
						
			if (currentA > 1) {ans = ans + "a" + Integer.toString(currentA);}
			if (currentA == 1) {ans = ans + "a";}
			if (currentA == -1) {ans = ans + "A";}
			if (currentA < -1) {ans = ans + "A" + Integer.toString(-currentA);}
			
			if (currentB > 1) {ans = ans + "b" + Integer.toString(currentB);}
			if (currentB == 1) {ans = ans + "b";}
			if (currentB == -1) {ans = ans + "B";}
			if (currentB < -1) {ans = ans + "B" + Integer.toString(-currentB);}
		}
		return ans;
	}	
}
