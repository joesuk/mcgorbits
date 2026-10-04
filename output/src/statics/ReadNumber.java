package statics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ReadNumber {
	

	
	


	



	   public static int reading () {

		   int  number1 =0;

	      //  prompt the user to enter their name

	      System.out.print("Enter number: ");



	      //  open up standard input

	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));



	      String number = null;



	      //  read the number from the command-line; need to use try/catch with the

	      //  readLine() method

	      try {

	         number = br.readLine();
	          number1 = Integer.parseInt(number);

	      } catch (IOException ioe) {

	         System.out.println("IO error trying to read your name!");

	         System.exit(1);

	      }

	      return number1;

	    //  System.out.println("Thanks for the name, " + userName);



	   }



	


}
