package main.java.mlp;

import java.util.ArrayList;
import java.util.Arrays;

import main.java.mlp.neuron.Neuron;


public class Layer {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	private ArrayList<Double> layerOutputs = new ArrayList<Double>();
	


	
	
	
	
	public void append(Neuron neuron) {
		neurons.add(neuron);
	}
	
	public void setNextLayerForAllNeurons(Layer nextLayer) {
		for (Neuron neuron: neurons) 
			neuron.setNextLayer(nextLayer);
	}
	
	
	
	public ArrayList<Double> getLayerOutputs() {
		return layerOutputs;
	}

	public void setLayerOutputs(ArrayList<Double> layerOutputs) {
		this.layerOutputs = layerOutputs;
	}

	
	
	public ArrayList<Neuron> getNeurons(){
		return neurons;
	}

}
