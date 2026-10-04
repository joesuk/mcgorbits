package statics;
import java.io.*;

import java.text.DecimalFormat;
import java.util.Date;




public class Sta1 {



	/**
	 * Returns the number of characters x (regardless capitalization) in s
	 * @param s
	 * @param x
	 * @return
	 */
	public static int countLetter(String s, Character x){
		if (s.length()==0) return 0;
		int answer=0;
		s=s.toLowerCase();
		x=Character.toLowerCase(x);
		for(int h =0 ; h < s.length() ; h++ ){

			if(x==s.charAt(h)) answer++;
		}

		return answer;
	}

	/**
	 * 
	 * //given an int[] yields a String to print it@param vector
	 * @param width
	 * @return
	 */
	public  static String toString(int[] vector, int width){
		DecimalFormat twoPlaces = new DecimalFormat("0.00");
		String answer="";
		String empty="";
		for(int h =1 ; h < width ; h++){
			empty = empty+" ";
		}        
		for(int i =vector.length-1 ; i >=0  ; i--){
			int aux = vector[i];
			if(aux==0){    
				answer=answer+" ";
			}    
		}
		return answer;
	}





	public static String convertTime(long timeInMillis) {
		//        

		// Get elapsed time in seconds
		Long elapsedTimeSec = (Long) (timeInMillis / 1000);

		// Get elapsed time in minutes
		Long elapsedTimeMin = (Long) (timeInMillis / (60 * 1000));

		// Get elapsed time in hours
		Long elapsedTimeHour = (Long) (timeInMillis / (60 * 60 * 1000));

		// Get elapsed time in days
		Long elapsedTimeDay = (Long) (timeInMillis / (24 * 60 * 60 * 1000));

		return elapsedTimeDay.toString() + " days "
		+ elapsedTimeHour.toString() + " hours " + elapsedTimeMin
		+ " minutes " + elapsedTimeSec.toString() + " sec "
		+ ((Long) timeInMillis).toString() + " millis";

	}



	public static void writeMatrix( int[][] matrix , int[][] matrix1, String filename, int heigh, String v, String w){

		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName );
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
			System.out.println( filename );

			String s1="In row i col j : number of cyc reduce dwords with j self intersections";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix[ i ].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow+"\n" );
				bufferedWriter.newLine();
			}

			System.out.println(  );
			System.out.println(  );
			s1="In row i col j : number of cyc reduced z words with j self intersections s.t. i(z,v)=i(z,w)";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix1[ i ].length ; j++ )
				{
					matrixRow += matrix1[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			System.out.println(  );
			System.out.println( );
			s1="In row i col j : proportion of cyc reduced z dwords with j self intersections s.t. i(z,v)=i(z,w)";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix1[ i ].length ; j++ )
				{
					if(matrix[i][j] !=0){
						double aux = (double) 100*matrix1[ i ][ j ]/matrix[i][j];
						int aux1 = (int) aux;
						matrixRow += aux1 + " ";
					}else matrixRow += 0+" ";

				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}




			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

	public static void writeMatrix(int heigh, int maxSelf, int[][] matrixp ,int[][] matrixq ,int[][] matrixr1 ,int[][] matrixr2, int[][] matrixTotal , String filename){
		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName );
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );    
			String st="In row i col j : number of cyc reduced dwords with j self intersections";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixTotal[ i ].length ; j++ ){
					matrixRow += matrixTotal[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			st="In row i col j : number of cyc reduced z words with j self intersections s.t. i(aabAB,v)=i(aaBab,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixp[ i ].length ; j++ ){
					matrixRow += matrixp[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}

			System.out.println(  );    
			st="In row i col j : proportion of cyc reduced z dwords with j self intersections s.t. i(aabAB,v)=i(aaBab,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixp[ i ].length ; j++ ){
					if(matrixp[i][j] !=0){
						double aux = (double) 100*matrixp[ i ][ j ]/matrixp[i][j];
						int aux1 = (int) aux;
						matrixRow += aux1 + " ";
					}else matrixRow += 0+" ";

				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}

			System.out.println(  );
			st="In row i col j : number of cyc reduced z words with j self intersections s.t. i(bbbbAba,v)=i(bbbbabA,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixq[ i ].length ; j++ ){
					matrixRow += matrixq[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			System.out.println(  );    
			st="In row i col j : proportion of cyc reduced z dwords with j self intersections s.t. i(bbbbAba,v)=i(bbbbabA,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixq[ i ].length ; j++ ){
					if(matrixq[i][j] !=0){
						double aux = (double) 100*matrixq[ i ][ j ]/matrixq[i][j];
						int aux1 = (int) aux;
						matrixRow += aux1 + " ";
					}else matrixRow += 0+" ";

				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}

			System.out.println(  );
			st="In row i col j : number of cyc reduced z words with j self intersections s.t. i(bbbbAba,v)=i(bbbbabA,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixr1[ i ].length ; j++ ){
					matrixRow += matrixr1[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			System.out.println(  );    
			st="In row i col j : proportion of cyc reduced z dwords with j self intersections s.t. i(bbbbAba,v)=i(bbbbabA,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixr1[ i ].length ; j++ ){
					if(matrixr1[i][j] !=0){
						double aux = (double) 100*matrixr1[ i ][ j ]/matrixr1[i][j];
						int aux1 = (int) aux;
						matrixRow += aux1 + " ";
					}else matrixRow += 0+" ";

				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}



			System.out.println(  );
			st="In row i col j : number of cyc reduced z words with j self intersections s.t. i(x1,v)=i(x2,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixr2[ i ].length ; j++ ){
					matrixRow += matrixr2[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			System.out.println(  );    
			st="In row i col j : proportion of cyc reduced z dwords with j self intersections s.t. i(x1,v)=i(x3,w)";
			System.out.println( st );
			bufferedWriter.write( st );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";
				for ( int j = 0 ; j < matrixr2[ i ].length ; j++ ){
					if(matrixr1[i][j] !=0){
						double aux = (double) 100*matrixr2[ i ][ j ]/matrixr2[i][j];
						int aux1 = (int) aux;
						matrixRow += aux1 + " ";
					}else matrixRow += 0+" ";

				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}



	public static void writeMatrix(int[] maxs, int[][] matrix , int[][] matrix1, String filename, int heigh, String v, String w){
		try
		{
			String fileName = filename+".txt";
			FileWriter fileWriter = new FileWriter( fileName );
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
			for(int i = 1 ; i < maxs.length ; i++){
				String matrixRow =" "+i+" "+maxs[i];
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();

			}
			System.out.println( filename );

			String s1="In row i col j : number of cyc reduce dwords with j self intersections";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix[ i ].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}

			System.out.println(  );
			s1="In row i col j : number of cyc reduced z words with j self intersections s.t. i(z,v)=i(z,w)";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix1[ i ].length ; j++ )
				{
					matrixRow += matrix1[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			System.out.println(  );

			s1="In row i col j : proportion of cyc reduced z dwords with j self intersections s.t. i(z,v)=i(z,w)";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix1[ i ].length ; j++ )
				{
					if(matrix[i][j] !=0){
						double aux = (double) 100*matrix1[ i ][ j ]/matrix[i][j];
						int aux1 = (int) aux;
						matrixRow += aux1 + " ";
					}else matrixRow += 0+" ";

				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}




			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}





	public static void fileMatrix0(int[] m, String filename, int heigh, String legend){
		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write( legend );
			bufferedWriter.newLine();
			for ( int i = 0 ; i <= heigh ; i++ ){
				String matrixRow=  i+" " + m[ i ];
				//    System.out.println( matrixRow );

				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();

			}
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

	public static void fileMatrix(int[] m, String filename, int heigh, String legend){
		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write( legend );
			bufferedWriter.newLine();
			for ( int i = 1 ; i <= heigh ; i++ ){
				String matrixRow=  i+" " + m[ i ];
				//    System.out.println( matrixRow );

				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();

			}
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

	public static void fileMatrix(int[] m, String filename,int minLength, int heigh, String legend){
		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write( legend );
			bufferedWriter.newLine();
			for ( int i =minLength ; i <= heigh ; i++ ){
				String matrixRow=  i+" " + m[ i ];
				//    System.out.println( matrixRow );

				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();

			}
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}






	public static void fileSentence( String filename,  String legend){
		try
		{
			String fileName = filename+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write( legend );
			bufferedWriter.newLine();

			bufferedWriter.flush();

			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

	public static void fileMatrixRow1( int[][] matrix , String filename, int lowerBound, int heigh, int width, String legend){
		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write(filename+ legend );
			bufferedWriter.newLine();
			for ( int i = lowerBound ; i < heigh ; i++ ){
				String matrixRow= i+" | ";

				for ( int j = 0 ; j < matrix[ i ].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
					//    System.out.println( matrixRow );
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();


			}
			bufferedWriter.write(filename+ legend );
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}



	public static void fileMatrix( int[][] matrix , String filename, int heigh, int width, String legend){
		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write(filename+ legend );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";

				for ( int j = 0 ; j < matrix[ i ].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
					//    System.out.println( matrixRow );
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();


			}
			bufferedWriter.write(filename+ legend );
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}



	public static void fileMatrix( int[][] matrix , String filename,  String legend, int heigh){
		try
		{
			String fileName = filename+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println( legend );
			bufferedWriter.write(filename+ legend );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ ){
				String matrixRow= "";

				for ( int j = 0 ; j < matrix[i].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
					//    System.out.println( matrixRow );
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();


			}
			bufferedWriter.write(filename+ legend );
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}


	public static void fileMatrix( int[][] matrix , String filename){
		try
		{
			String fileName = filename+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );


			//   bufferedWriter.write(filename+ legend );
			//   bufferedWriter.newLine();
			for ( int i = 1 ; i < matrix.length ; i++ ){
				String matrixRow= "";

				for ( int j = 0 ; j < matrix[i].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
					//    System.out.println( matrixRow );
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
				bufferedWriter.flush();


			}
			//      bufferedWriter.write(filename + legend );
			//      bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

	public static void fileMatrixSI_CL( int[][] matrix , String filename){
		try
		{
			String fileName = filename+".txt";
			FileWriter fileWriter = new FileWriter( fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

			System.out.println(  );
			//   bufferedWriter.write(filename+ legend );
			//   bufferedWriter.newLine();
		
			for ( int i = 0 ; i < matrix.length ; i++ ){
				String matrixRow= "";
				if(i==0) { 
					matrixRow="WL/SI \t ";
					for( int h = 0 ; h < matrix[0].length ; h++ ){
						matrixRow+= h + "\t ";
					}
					System.out.println( matrixRow );
					bufferedWriter.write( matrixRow );
					bufferedWriter.newLine();
					bufferedWriter.flush();
				}else{
					matrixRow=i+"\t";
					for ( int j = 0 ; j < matrix[i].length ; j++ ){
						matrixRow += matrix[ i ][ j  ] + "\t ";
					}
					
					System.out.println(matrixRow );
					bufferedWriter.write( matrixRow );
					bufferedWriter.newLine();
					bufferedWriter.flush();


				}
			}
			//      bufferedWriter.write(filename + legend );
			//      bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

	public static void writeMatrix( int[][] matrix , int[][] matrix1, String filename, int heigh){

		try
		{
			String fileName = filename+heigh+".txt";
			FileWriter fileWriter = new FileWriter( fileName );
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
			System.out.println( filename );

			String s1="In row i col j : number of cyc reduce dwords with j self intersections";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();
			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix[ i ].length ; j++ )
				{
					matrixRow += matrix[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow+"\n" );
				bufferedWriter.newLine();
			}

			System.out.println(  );
			System.out.println(  );
			s1="Proportions";
			System.out.println( s1 );
			bufferedWriter.write( s1 );
			bufferedWriter.newLine();

			for ( int i = 1 ; i < heigh ; i++ )
			{
				String matrixRow= "";

				for ( int j = 0 ; j < matrix1[ i ].length ; j++ )
				{
					matrixRow += matrix1[ i ][ j ] + " ";
				}
				System.out.println( matrixRow );
				bufferedWriter.write( matrixRow );
				bufferedWriter.newLine();
			}
			System.out.println(  );
			System.out.println( );


			bufferedWriter.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}

	}

}





