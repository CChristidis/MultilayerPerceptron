package main.java.activationfunctions;

public abstract class ActivationFunctionStrategy implements ActivationFunction {

	public abstract double activateOutput(double output);
	public abstract double derivative(double output);


}
