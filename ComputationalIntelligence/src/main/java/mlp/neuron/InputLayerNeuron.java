package main.java.mlp.neuron;

import java.util.Arrays;
import java.util.List;

public class InputLayerNeuron extends Neuron{

	public InputLayerNeuron(int neuronId, List<Double> inputs, double learningRate) {
		super(neuronId, inputs, learningRate);
	}


	@Override
	public void calculateOutput() {}

	@Override
	public double calculateDelta() {return 0;}
	
	@Override
	public String toString() {
		return "InputLayerNeuron [neuronId=" + neuronId + ", inputs="
				+ Arrays.toString(inputs.toArray()) + ", output=" + output + ", learningRate=" + learningRate + "]";
	}

}
