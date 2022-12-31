package main.java.mlp.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import main.java.mlp.Layer;


public abstract class Neuron {
	protected ArrayList<Double> weights = new ArrayList<>();
	protected int neuronId; 	// neuron's index in layer. Useful for correct weight retrieval in backpropagation.
	protected double bias;
	protected List<Double> inputs;
	protected double output;
	protected double delta; 	// neuron's error. Used for backpropagation.
	protected double learningRate;
	protected Layer previousLayer;
	protected Layer nextLayer;
	protected double u;
	
	
	public abstract double calculateDelta();
	public abstract void calculateOutput();


	
	public double getRandomDouble(int min, int max) {
		Random randomDouble = new Random();
		return randomDouble.nextDouble() * (max - min) + min;
	}
	
	
	public void initWeights() {
		for (int i = 0; i < inputs.size(); i ++) weights.add(getRandomDouble(-1, 1));	
	}
	
	
	public void initBias() {
		bias = getRandomDouble(-1, 1);
	}
	
	
	public Neuron(int neuronId, List<Double> inputs, double learningRate) {
		this.inputs = inputs;
		this.neuronId = neuronId;
		this.learningRate = learningRate;
		initBias();
		initWeights();
		
	}
	

	private double calculatePartialDerivative(Neuron previousLayerNeuron) {
		double previousOutput = previousLayerNeuron.getOutput(); 	// y(h-1)j
		return this.delta * previousOutput;  					 	// dE/dw(h)ij = delta(h)i * y(h-1)j
	}
	
	
	/*
	 * Updates the weights of the neuron with gradient descent.
	 * 
	 */
	public void updateWeights() {
		List<Neuron> previousLayerNeurons = previousLayer.getNeurons();
		
		for (int i = 0; i < previousLayerNeurons.size(); i ++) {
			Neuron currentNeuron = previousLayerNeurons.get(i);	
			
			double newWeight = this.weights.get(i) - (this.learningRate * calculatePartialDerivative(currentNeuron)); // w(t+1) = w(t) - n*dE/dwi
			 	
			this.weights.set(i, (double) newWeight);
			this.bias -= learningRate * this.delta;
		
		}	
	}
	


	public double getOutput() {
		return output;
	}
	
	
	public ArrayList<Double> getWeights() {
		return weights;
	}
	

	public double getDelta() {
		return delta;
	}
	
	
	public void setOutput(double output) {
		this.output = output;
	}
	
	
	public void setInputs(List<Double> inputs) {
		this.inputs = inputs;
	}

	
	public void setDelta(double delta) {
		this.delta = delta;
	}
	
	public void setPreviousLayer(Layer previousLayer) {
		this.previousLayer = previousLayer;
	}
	
	
	public void setNextLayer(Layer nextLayer) {
		this.nextLayer = nextLayer;
	}

}
