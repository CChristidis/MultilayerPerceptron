package main.java.mlp.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import main.java.mlp.Layer;


public abstract class Neuron {
	protected ArrayList<Float> weights = new ArrayList<>();
	protected int neuronId; // neuron's index in layer. Useful for correct weight retrieval in backpropagation.
	protected float bias;
	protected List<Float> inputs;
	protected float output;
	protected float delta; 	// neuron's error. Used for backpropagation.
	protected float learningRate;
	protected Layer previousLayer;
	protected Layer nextLayer;
	
	
	public abstract void calculateDelta();
	public abstract void calculateOutput();

	
	


	public float getRandomFloat(int min, int max) {
		Random randomFloat = new Random();
		return randomFloat.nextFloat() * (max - min) + min;
	}
	
	
	public void initWeights() {
		for (int i = 0; i < inputs.size(); i ++) 
			weights.add(getRandomFloat(-1, 1));	
	}
	
	
	public void initBias() {
		bias = getRandomFloat(-1, 1);
	}
	
	
	
	
	public Neuron(int neuronId, List<Float> inputs, float learningRate) {
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
		calculateDelta(); 
		
		List<Neuron> previousLayerNeurons = previousLayer.getNeurons();

		for (int i = 0; i < previousLayerNeurons.size(); i ++) {
			Neuron currentNeuron = previousLayerNeurons.get(i);						
			
			float previousOutput = currentNeuron.getOutput();  						// y(h-1)j
			float partialDerivative = this.delta * previousOutput;  				// ä(h)i * y(h-1)j
			float amountSubtractedFromWeight = learningRate * partialDerivative;    // ç * dE/dwi
			float newWeight = this.weights.get(i) - amountSubtractedFromWeight; 	// w(t+1) = w(t) - ç*dE/dwi
			
			this.weights.set(i, newWeight);
			this.bias -= learningRate * this.delta;

		}

		
	}
	
	



	public float getBias() {
		return bias;
	}





	public List<Float> getInputs() {
		return inputs;
	}





	public float getOutput() {
		return output;
	}





	public void setBias(float bias) {
		this.bias = bias;
	}





	public void setInputs(List<Float> inputs) {
		this.inputs = inputs;
	}





	public void setOutput(float output) {
		this.output = output;
	}





	public ArrayList<Float> getWeights() {
		return weights;
	}


	public int getNeuronId() {
		return neuronId;
	}


	public void setWeights(ArrayList<Float> weights) {
		this.weights = weights;
	}


	public void setNeuronId(int neuronId) {
		this.neuronId = neuronId;
	}
	
	public float getDelta() {
		return delta;
	}
	
	public Layer getPreviousLayer() {
		return previousLayer;
	}
	
	public void setDelta(float delta) {
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
