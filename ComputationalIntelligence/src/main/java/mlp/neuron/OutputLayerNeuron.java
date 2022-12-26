package main.java.mlp.neuron;


import java.util.List;

import main.java.activationfunctions.ActivationFunction;
import main.java.activationfunctions.ActivationFunctionFactory;
import main.java.mlp.Layer;

public class OutputLayerNeuron extends Neuron {
	private float expectedOutput; // t
	private ActivationFunction activationFunc;
	private float delta;
	
	


	


	public OutputLayerNeuron(int neuronId, List<Float> inputs, String activationFunc, float learningRate, float expectedOutput) {
		super(neuronId, inputs, learningRate);
		this.activationFunc = ActivationFunctionFactory.getActivationFunction(activationFunc);
		this.expectedOutput = expectedOutput;
		calculateOutput();
	}
	


	public void calculateDelta() {
		this.delta = -activationFunc.derivative(output) * (expectedOutput - output);
	
	}
	
	public void calculateOutput() {
		output = bias;
		
		for (int i = 0; i < inputs.size(); i ++) 
			output += weights.get(i) * inputs.get(i);  // neuron's overall input
		
		output = activationFunc.activateOutput(output);
		
	}
	
	
	public Layer getPreviousLayer() {
		return previousLayer;
	}




	public void setPreviousLayer(Layer previousLayer) {
		this.previousLayer = previousLayer;
	}
	
	
	public float getExpectedOutput() {
		return expectedOutput;
	}


	public ActivationFunction getActivationFunc() {
		return activationFunc;
	}


	public float getDelta() {
		return delta;
	}


	public void setExpectedOutput(float expectedOutput) {
		this.expectedOutput = expectedOutput;
	}


	public void setActivationFunc(ActivationFunction activationFunc) {
		this.activationFunc = activationFunc;
	}


	public void setDelta(float delta) {
		this.delta = delta;
	}
	
	
	@Override
	public String toString() {
		return "OutputLayerNeuron [expectedOutput=" + expectedOutput + ", activationFunc=" + activationFunc + ", delta="
				+ delta + ", weights=" + weights + ", neuronId=" + neuronId + ", bias=" + bias + ", inputs=" + inputs
				+ ", output=" + output + ", learningRate=" + learningRate + ", previousLayer=" + previousLayer
				+ ", nextLayer=" + nextLayer + "]";
	}


}
