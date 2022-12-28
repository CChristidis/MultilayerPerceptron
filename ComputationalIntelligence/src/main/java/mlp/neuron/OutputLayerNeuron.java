package main.java.mlp.neuron;


import java.util.List;

import main.java.activationfunctions.ActivationFunction;
import main.java.activationfunctions.ActivationFunctionFactory;
import main.java.mlp.Layer;

public class OutputLayerNeuron extends Neuron {
	private double expectedOutput; // t
	private ActivationFunction activationFunc;
	private double delta;


	public OutputLayerNeuron(int neuronId, List<Double> inputs, String activationFunc, double learningRate, double expectedOutput) {
		super(neuronId, inputs, learningRate);
		this.activationFunc = ActivationFunctionFactory.getActivationFunction(activationFunc);
		this.expectedOutput = expectedOutput;
		calculateOutput();
		
	}
	

	public double calculateDelta() {
		return -activationFunc.derivative(this.u) * (expectedOutput - this.output);
	}
	
	public void calculateOutput() {
		this.output = bias;
		
		
		for (int i = 0; i < inputs.size(); i ++) 
			this.output += weights.get(i) * inputs.get(i);  // neuron's overall input
		
		this.u = this.output;
		
		this.output = activationFunc.activateOutput(this.output);
		
	}
	
	

	
	public double getDelta() {
		return delta;
	}
	
	
	public void setPreviousLayer(Layer previousLayer) {
		this.previousLayer = previousLayer;
	}

	
	public void setExpectedOutput(double expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}


	@Override
	public String toString() {
		return "OutputLayerNeuron [expectedOutput=" + expectedOutput + ",u=" + u + ",inputs=" + inputs + ", delta="
				+ delta + ", weights=" + weights  + ",bias=" + bias + ", output=" + output + "]";
	}
	

}
