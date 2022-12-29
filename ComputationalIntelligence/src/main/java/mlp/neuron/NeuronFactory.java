package main.java.mlp.neuron;

import java.util.List;

public class NeuronFactory {
	
	
	public static Neuron getNeuron(int neuronId, List<Double> inputs, double learningRate) {
		return new InputLayerNeuron(neuronId, inputs, learningRate);
	}
	
	
	public static Neuron getNeuron(int neuronId, List<Double> inputs, String activationFunc, double learningRate) {
		return new HiddenLayerNeuron(neuronId, inputs,  activationFunc, learningRate);
	}
	
	
	public static Neuron getNeuron(int neuronId, List<Double> inputs, String activationFunc, double learningRate, double expectedOutput) {
		return new OutputLayerNeuron(neuronId, inputs,  activationFunc, learningRate, expectedOutput);
	}
	
	
	
}

	
	


