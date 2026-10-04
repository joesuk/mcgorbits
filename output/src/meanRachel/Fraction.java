package meanRachel;

public class Fraction {
	
	private long numerator;
	private long denominator;
	
	public Fraction() {
		numerator = 0;
		denominator = 1;
	}
	
	public Fraction(long n, long d) {
		numerator = n;
		denominator = d;
	}
	
	public String printStr() {
		String rep = "";
		rep += getN();
		rep += "/";
		rep += getD();
		return rep;
	}
	
	public long getN() {
		return numerator;
	}
	
	public long getD() {
		return denominator;
	}
	
	public void add(Fraction f) {
		long p = f.getN();
		long q = f.getD();
		long newD = lcm(denominator, q);
		long newN = (newD/denominator*numerator) + (newD/q*p);
		denominator = newD;
		numerator = newN;
		reduce();
	}
	
	public void subtract(Fraction f) {
		long p = f.getN();
		long q = f.getD();
		long newD = lcm(denominator, q);
		long newN = (newD/denominator*numerator) - (newD/q*p);
		denominator = newD;
		numerator = newN;
		reduce();
	}
	
	public void multiply(Fraction f) {
		long p = f.getN();
		long q = f.getD();
		numerator *= p;
		denominator *= q;
		reduce();
	}
	
	public void divide(Fraction f) {
		long p = f.getN();
		long q = f.getD();
		numerator *= q;
		denominator *= p;
		reduce();
	}
	
	public void add(long p, long q) {
		long newD = lcm(denominator, q);
		long newN = (newD/denominator*numerator) + (newD/q*p);
		denominator = newD;
		numerator = newN;
		reduce();
	}
	
	public void subtract(long p, long q) {
		long newD = lcm(denominator, q);
		long newN = (newD/denominator*numerator) - (newD/q*p);
		denominator = newD;
		numerator = newN;
		reduce();
	}
	
	public void multiply(long p, long q) {
		numerator *= p;
		denominator *= q;
		reduce();
	}
	
	public void divide(long p, long q) {
		numerator *= q;
		denominator *= p;
		reduce();
	}
	
	private void reduce() {
		long g = gcd(numerator, denominator);
		numerator /= g;
		denominator /= g;
	}
	
	private long gcd(long a, long b) {
		if (a > b) {
			long temp = a;
			a = b;
			b = temp;
		}
		if (a == 0) {
			return b;
		}
		return (gcd(b%a, a));
	}
	
	private long lcm(long a, long b) {
		return (a/gcd(a,b)*b);
	}
	
}
