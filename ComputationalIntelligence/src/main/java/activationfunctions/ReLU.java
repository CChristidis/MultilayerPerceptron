package main.java.activationfunctions;

public class ReLU extends ActivationFunctionStrategy {

	@Override
	public float activateOutput(float output) {
		return (float) Math.max(0, output);
	}

	@Override
	public float derivative(float output) {
		if (output > 0) return 1;
		
		return 0;
	}
	

}
