package main.java.mlp.neuron;

import java.util.Arrays;
import java.util.List;

import main.java.activationfunctions.ActivationFunction;
import main.java.activationfunctions.ActivationFunctionFactory;
import main.java.mlp.Layer;

public class HiddenLayerNeuron extends Neuron {
	
	
	private ActivationFunction activationFunc;
	
	
	public HiddenLayerNeuron(int neuronId, List<Double> inputs, String activationFunc, double learningRate) {
		super(neuronId, inputs, learningRate);
		this.activationFunc = ActivationFunctionFactory.getActivationFunction(activationFunc);
		
		calculateOutput();
		
	}

	



	public double calculateNextLayerWeightDelta() {
		double sum = 0;
		List<Neuron> nextLayerNeurons = nextLayer.getNeurons();
		
		for (int i = 0; i < nextLayerNeurons.size(); i ++) {
			Neuron currentNeuron = nextLayerNeurons.get(i);
			sum +=  currentNeuron.getWeights().get(neuronId) * currentNeuron.getDelta();  // wj,i
		}
		
	
		return sum;
	}

	
	
	public double calculateDelta() {
		//System.out.println("============== HIDDEN LAYER NEURON DELTA: =================");
		//System.out.println("Before = " + this.delta);
		//System.out.println("After = " + (activationFunc.derivative(output) * calculateNextLayerWeightDelta()));
		return activationFunc.derivative(u) * calculateNextLayerWeightDelta();
		
	}

	
	public void calculateOutput() {
		output = bias;
		
		
		for (int i = 0; i < inputs.size(); i ++) 
			output += weights.get(i) * inputs.get(i);  // neuron's overall input
		
		this.u = output;
		
		
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


	public double getDelta() {
		return delta;
	}


	public void setActivationFunc(ActivationFunction activationFunc) {
		this.activationFunc = activationFunc;
	}


	public void setDelta(double delta) {
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
		return "HiddenLayerNeuron [inputs=" + inputs + ", u= " + u + ", output=" + output + ", weights="
				+ weights + ", neuronId=" + neuronId + ", bias=" + bias + ", delta=" + delta + "]";
	}
	  
	
	
}
