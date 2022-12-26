package main.java.mlp;

import java.util.ArrayList;

import main.java.mlp.neuron.Neuron;


public class Layer {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	private ArrayList<Float> layerOutputs = new ArrayList<Float>();
	
	
	
	public void append(Neuron neuron) {
		neurons.add(neuron);
	}
	
	public void setNextLayerForAllNeurons(Layer nextLayer) {
		for (Neuron neuron: neurons) 
			neuron.setNextLayer(nextLayer);
	}
	
	
	
	public ArrayList<Float> getLayerOutputs() {
		return layerOutputs;
	}

	public void setLayerOutputs(ArrayList<Float> layerOutputs) {
		this.layerOutputs = layerOutputs;
	}

	
	
	public ArrayList<Neuron> getNeurons(){
		return neurons;
	}

}
