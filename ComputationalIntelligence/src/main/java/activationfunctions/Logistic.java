package main.java.activationfunctions;

public class Logistic extends ActivationFunctionStrategy {

	@Override
	public float activateOutput(float output) {
		return (float) (1/(1 + Math.exp(-output)));
	}

	@Override
	public float derivative(float output) {
		return activateOutput(output) * (1 - activateOutput(output));
	}

}
