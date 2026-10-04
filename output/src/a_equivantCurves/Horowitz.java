package a_equivantCurves;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class Horowitz {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{


		for(int n= 1 ; n < 15 ; n++){
			String z="aAbB"; 
			String dir ="Horowitz/";
			statics.CreateDirectory.create(dir);
			SurfaceWord sw = new SurfaceWord(z);
			String filename = dir+"equivalentC"+n+"_"+ z;
			char[][] ncurves=new char[(int)Math.pow(2,n)][lcurve(n)];
			int[][] wn=allp(n);
			for(int i=0;i<wn.length;i++)
			{
				ncurves[i]=makeclass(wn[i]);
			}
			printchar(ncurves, sw, filename, dir+"StudyConjectureHorowitz");

		}
	}




	public static boolean printchar(char a[][], SurfaceWord swPants, String filename, String filename1){
		boolean answer = true;
		int si1=0;
		int si;
		int sit=0;
		int sit1=0;
		SurfaceWord swTorus = new SurfaceWord("abAB");
		for(int i=0;i<a.length;i++){

			String s1="";
			for(int j=0;j < a[i].length;j++){
				s1 = s1 + a[i][j];
			}


			Word ww1 = new Word( Word.fromString(s1, swPants.length()));
			Word w1 = ww1.cyclicallyReduce();
			Curve c1 = new Curve(w1, swPants);
			Curve ctorus = new Curve(w1, swTorus);
			si = c1.selfIntersection();
			sit =ctorus.selfIntersection();
			
			if(i==0){
				si1 = si;
				sit1=sit;}
			else{
			//	System.out.println(si1==si);
				if(si1!= si || sit1 != sit){
					statics.FileStuff.fileSentence(filename1, "Found Counterexample" );
				}else{
					double n = Math.log(a.length)/Math.log(2);
					String sent ="n= "+ n+" Counterexample NOT found. SI of class"+si1+" (in pants) and "+sit+ " (in torus). Word length is "+c1.length();
					System.out.println(sent);
					statics.FileStuff.fileSentence(filename1, sent );
				}
			}


			String sentence = w1.toString()+"\t"+si;
			//System.out.println(sentence+"\n");
			statics.FileStuff.fileSentence(filename, sentence);

		}
		return answer;
	}





	public static int[][] allp(int n)
	{
		int[][] wnn=new int[(int)Math.pow(2,n)][n];
		for(int i=0;i<(int)Math.pow(2,n);i++)
		{
			for(int j=0;j<n;j++)
			{
				wnn[i][j]=1;
			}
		}
		int l=1;
		for(int i=1;i<=n;i++)
		{
			int[][] pos=getp(n,i);
			for(int j=0;j<com(n,i);j++)
			{
				for(int k=0;k<i;k++)
				{
					wnn[l][pos[j][k]-1]=-1;
				}
				l++;
			}
		}
		return wnn;
	}
	public static int[][] getp(int l,int m)
	{
		int[] iArr=new int[l];
		int k=0;
		int[][] lm=new int[com(l,m)][m];
		int num=0;
		int pos=0;
		int ppi=0;
		for(;;){
			if(num==l){
				if(pos==1){
					break;
				}
				pos-=2;
				ppi=iArr[pos];
				num=ppi;
			}
			iArr[pos++]=++num;
			if(pos!=m){
				continue;
			}
			for(int i=0;i<pos;i++){
				lm[k][i]=iArr[i];
				if(i==pos-1)
				{
					k++;
				}
			}
		}
		return lm;
	}
	public static char[] inv(char w0[])
	{
		char w[];
		w=new char[w0.length];
		int i;
		int l=w0.length-1;
		for(i=0;i<w.length;i++)
		{
			switch(w0[l-i])
			{
			case'a':
				w[i]='A';
				break;
			case'A':
				w[i]='a';
				break;
			case'b':
				w[i]='B';
				break;
			case'B':
				w[i]='b';
				break;
			default:
				System.out.println("Nothing!");
			}
		}
		return w;
	}
	public static char[] makeclass(int[] wwn)
	{
		// TODO Auto-generated method stub

		int i,j;
		char[] wold={'a'};
		char[] wnew = new char[1];
		for(i=0;i<wwn.length;i++)
		{
			char b2i[]=new char[2*i+2];
			for(j=0;j<2*i+2;j++)
			{
				b2i[j]='b';
			}
			char b2i1[]=new char[2*i+1];///
			for(j=0;j<2*i+1;j++)
			{
				b2i1[j]='b';
			}
			if(wwn[i]==1)
			{
				wnew=merge(inv(wold),b2i,wold,b2i1,inv(wold),b2i,wold);
				wold=wnew;
			}
			else
			{
				wnew=merge(wold,b2i,inv(wold),b2i1,wold,b2i,inv(wold));
				wold=wnew;
			}
		}
		char[] wn=wnew;
		return wn;
	}
	final static public char[] merge(final char[] ...arrays )
	{
		int size = 0;
		for ( char[] a: arrays )
			size += a.length;

		char[] res = new char[size];

		int destPos = 0;
		for ( int i = 0; i < arrays.length; i++ ) {
			if ( i > 0 ) destPos += arrays[i-1].length;
			int length = arrays[i].length;
			System.arraycopy(arrays[i], 0, res, destPos, length);
		}

		return res;
	}
	public static int lcurve(int n)
	{
		int m=1;
		for(int i=1;i<n+1;i++)
		{
			m=3*m+6*n-1;
		}
		return m;
	}

	public static int com(int a,int b)
	{
		return fact(a)/fact(b)/fact(a-b);
	}
	public static int fact(int a)
	{
		int aa=1;
		for(int i=1;i<a+1;i++)
		{
			aa*=i;
		}
		return aa;
	}

}
