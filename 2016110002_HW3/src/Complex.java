
public class Complex {
	double real;
	double imag;
	
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
		
	public void multiMinus() {
		this.real *= -1;
		this.imag *= -1;
	}
	
	public static Complex sum(Complex c1, Complex c2) {
		return new Complex(c1.real+c2.real, c1.imag+c2.imag);
	}
	
	public static Complex multi(Complex c1, Complex c2) {
		double real = c1.real * c2.real - c1.imag * c2.imag;
		double imag = c1.real * c2.imag + c1.imag * c2.real;
		return new Complex(real, imag);
	}
	
	public void roundBySeventhDecimal() {
		this.real = Math.round(this.real * Math.pow(10, 7)) / Math.pow(10, 7);
		this.imag = Math.round(this.imag * Math.pow(10, 7)) / Math.pow(10, 7);
	}
	
	@Override
	public String toString() {
		return String.format("%f + %fi", this.real, this.imag);
	}
}
