package main.java.activationfunctions;

public class HyperbolicTangent extends ActivationFunctionStrategy {

	@Override
	public float activateOutput(float output) {
		return (float) Math.tanh(output);
	}
	
	public float derivative(float output) {
		return (float) (1 - Math.pow(activateOutput(output), 2));
	}

}
