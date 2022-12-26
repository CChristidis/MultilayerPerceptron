package main.java.activationfunctions;

public abstract class ActivationFunctionStrategy implements ActivationFunction {

	public abstract float activateOutput(float output);
	public abstract float derivative(float output);


}
