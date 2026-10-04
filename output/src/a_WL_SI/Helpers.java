package a_WL_SI;

import java.util.HashSet;

public class Helpers {

	
	public static boolean usesThreeGenerators(String str){
		HashSet<Character> answer = new HashSet<Character>();
		for (int i = 0;i < str.length(); i++){
		     answer.add(Character.toLowerCase(str.charAt(i)));
		     
		}
		if(answer.size()==3) return true;
		return false;
		
	}
}
