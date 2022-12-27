package main.java.mlp.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import main.java.mlp.Layer;


public abstract class Neuron {
	protected ArrayList<Double> weights = new ArrayList<>();
	protected int neuronId; // neuron's index in layer. Useful for correct weight retrieval in backpropagation.
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
		for (int i = 0; i < inputs.size(); i ++) 
			weights.add(getRandomDouble(-1, 1));	
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
	


	
	/*
	 * Updates the weights of the neuron with gradient descent.
	 * 
	 */
	public void updateWeights() {
		//this.delta = calculateDelta(hey);
		

		List<Neuron> previousLayerNeurons = previousLayer.getNeurons();
		
		
		
		for (int i = 0; i < previousLayerNeurons.size(); i ++) {
			//System.out.println("+=========================================================+");
			Neuron currentNeuron = previousLayerNeurons.get(i);						
			
			double previousOutput = currentNeuron.getOutput();  						// y(h-1)j
			//System.out.println("y(h-1)j = " + previousOutput);
			
			double partialDerivative = this.delta * previousOutput;  				// ä(h)i * y(h-1)j
			//System.out.println("ä(h)i * y(h-1)j = " + partialDerivative);
			
			double amountSubtractedFromWeight = learningRate * partialDerivative;    // ç * dE/dwi
			//System.out.println("ç * dE/dwi = " + amountSubtractedFromWeight);
			
			double newWeight = this.weights.get(i) - amountSubtractedFromWeight; 	// w(t+1) = w(t) - ç*dE/dwi
			//System.out.println("w(t+1) = " + newWeight);
			this.weights.set(i, (double)newWeight);
		
			this.bias -= learningRate * this.delta;

		}

		
	}
	
	



	public double getBias() {
		return bias;
	}





	public List<Double> getInputs() {
		return inputs;
	}





	public double getOutput() {
		return output;
	}





	public void setBias(double bias) {
		this.bias = bias;
	}





	public void setInputs(List<Double> inputs) {
		this.inputs = inputs;
	}





	public void setOutput(double output) {
		this.output = output;
	}





	public ArrayList<Double> getWeights() {
		return weights;
	}


	public int getNeuronId() {
		return neuronId;
	}


	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}


	public void setNeuronId(int neuronId) {
		this.neuronId = neuronId;
	}
	
	public double getDelta() {
		return delta;
	}
	
	public Layer getPreviousLayer() {
		return previousLayer;
	}
	
	public void setDelta(double delta) {
		this.delta = delta;
	}
	
	public void setPreviousLayer(Layer previousLayer) {
		this.previousLayer = previousLayer;
	}
	
	
	public Layer getNextLayer() {
		return nextLayer;
	}
	public void setNextLayer(Layer nextLayer) {
		this.nextLayer = nextLayer;
	}
	
	
	
	
	
	
	

}
