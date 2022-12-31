package main.java.activationfunctions;

public class ReLU extends ActivationFunctionStrategy {

	@Override
	public double activateOutput(double output) {
		return (double) Math.max(0, output);
	}

	@Override
	public double derivative(double output) {
		if (output > 0) return (double) 1;
		if (output < 0) return (double) 0;
		
		return (Double) null;
	}
	

}
