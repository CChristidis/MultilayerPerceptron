package main.java.mlp.neuron;

import java.util.Arrays;
import java.util.List;

import main.java.activationfunctions.ActivationFunction;
import main.java.activationfunctions.ActivationFunctionFactory;
import main.java.mlp.Layer;

public class HiddenLayerNeuron extends Neuron {
	
	
	private ActivationFunction activationFunc;
	
	
	public HiddenLayerNeuron(int neuronId, List<Float> inputs, String activationFunc, float learningRate) {
		super(neuronId, inputs, learningRate);
		this.activationFunc = ActivationFunctionFactory.getActivationFunction(activationFunc);
		calculateOutput();
	}

	



	public float calculateNextLayerWeightDelta() {
		float sum = 0;
		List<Neuron> nextLayerNeurons = nextLayer.getNeurons();
		
		for (int i = 0; i < nextLayerNeurons.size(); i ++) {
			Neuron currentNeuron = nextLayerNeurons.get(i);
			
			sum += currentNeuron.getWeights().get(neuronId) * currentNeuron.delta;  // wj,i
		}
		return sum;
	}

	
	
	public void calculateDelta() {
		this.delta = activationFunc.derivative(output) * calculateNextLayerWeightDelta() ;
		
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
	
	
	public ActivationFunction getActivationFunc() {
		return activationFunc;
	}


	public float getDelta() {
		return delta;
	}


	public void setActivationFunc(ActivationFunction activationFunc) {
		this.activationFunc = activationFunc;
	}


	public void setDelta(float delta) {
		this.delta = delta;
	}
	
	
	public Layer getNextLayer() {
		return nextLayer;
	}
	
	
	// will be used after initializing the next layer
	public void setNextLayer(Layer nextLayer) {
		this.nextLayer = nextLayer;
	}
	
	
	@Override
	public String toString() {
		return "HiddenLayerNeuron [inputs=" + inputs + ", output=" + output + ", nextLayer=" + nextLayer + ", activationFunc=" + activationFunc + ", weights="
				+ weights + ", neuronId=" + neuronId + ", bias=" + bias + ", delta=" + delta 
				+ ", learningRate=" + learningRate + ", previousLayer=" + previousLayer + "]";
	}
	  
	
	
}
