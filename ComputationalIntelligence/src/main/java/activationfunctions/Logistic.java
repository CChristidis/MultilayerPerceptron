package main.java.activationfunctions;

public class Logistic extends ActivationFunctionStrategy {

	@Override
	public double activateOutput(double output) {
		return (double) (1 / (1+Math.exp(-output)));
	}

	@Override
	public double derivative(double output) {
		return activateOutput(output) * (1 - activateOutput(output));
	}

}
