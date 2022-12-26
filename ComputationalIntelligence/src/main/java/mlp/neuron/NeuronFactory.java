package main.java.mlp.neuron;

import java.util.List;

public class NeuronFactory {
	
	
	public static Neuron getNeuron(int neuronId, List<Float> inputs, float learningRate) {
		return new InputLayerNeuron(neuronId, inputs, learningRate);
	}
	
	
	public static Neuron getNeuron(int neuronId, List<Float> inputs, String activationFunc, float learningRate) {
		return new HiddenLayerNeuron(neuronId, inputs,  activationFunc, learningRate);
	}
	
	
	public static Neuron getNeuron(int neuronId, List<Float> inputs, String activationFunc, float learningRate, float expectedOutput) {
		return new OutputLayerNeuron(neuronId, inputs,  activationFunc, learningRate, expectedOutput);
	}
	
	
	
}

	
	


