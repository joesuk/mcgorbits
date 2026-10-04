package statics;

public class DealWithStrings {
	
	public static int countChar(String haystack, char needle)
	{
	    int count = 0;
	    for (int i=0; i < haystack.length(); i++)
	    {
	        if (haystack.charAt(i) == needle)
	        {
	             count++;
	        }
	    }
	    return count;
	}
	
	
	public static int hom_a(String s){
		return countChar(s, 'a') - countChar(s, 'A');
		
	}
	public static int hom_b(String s){
		return countChar(s, 'b') - countChar(s, 'B');
		
	}
	
	public static boolean hom(String s1, String s2){
		boolean a1= Math.abs(countChar(s1, 'a')-countChar(s1,'A')) == Math.abs(countChar(s2, 'a')-countChar(s2,'A')) ;
		boolean b1= Math.abs(countChar(s1, 'b')-countChar(s1,'B')) == Math.abs(countChar(s2, 'B')-countChar(s2,'B')) ;
		return a1 & b1;
	}
	public static String invertB(String s){
		String answer="";
		for(int h = 0 ; h < s.length() ; h++)
			if(s.charAt(h)=='a' || s.charAt(h)== 'A'){
				answer=answer+s.charAt(h);
			}else if(s.charAt(h)=='b'){
				answer = answer+'B';
			}else answer = answer + 'b';
		return answer;
	}
	
	public static String invertA(String s){
		String answer="";
		for(int h = 0 ; h < s.length() ; h++)
			if(s.charAt(h)=='b' || s.charAt(h)== 'B'){
				answer=answer+s.charAt(h);
			}else if(s.charAt(h)=='a'){
				answer = answer+'A';
			}else answer = answer + 'a';
		return answer;
	}
	

	
}
