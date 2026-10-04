package statics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ReadSentence {
	

	
	


	



	   public static String read () {



	      //  prompt the user to enter their name

	      System.out.print("Enter text: ");



	      //  open up standard input

	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));



	      String userName = null;



	      //  read the username from the command-line; need to use try/catch with the

	      //  readLine() method

	      try {

	         userName = br.readLine();

	      } catch (IOException ioe) {

	         System.out.println("IO error trying to read your name!");

	         System.exit(1);

	      }

	      return userName;

	    //  System.out.println("Thanks for the name, " + userName);



	   }



	


}
