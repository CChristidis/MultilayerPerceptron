package main.java.activationfunctions;

public class HyperbolicTangent extends ActivationFunctionStrategy {

	@Override
	public double activateOutput(double output) {
		return (double) Math.tanh(output);
	}
	
	public double derivative(double output) {
		return (double) (1 - Math.pow(activateOutput(output), 2));
	}

}
