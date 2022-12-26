package main.java.mlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.mlp.neuron.HiddenLayerNeuron;
import main.java.mlp.neuron.Neuron;
import main.java.mlp.neuron.NeuronFactory;

public class MLP {
	private final int K = 3;  // number of classes
	private final int d = 2;  // number of categories(columns) for every data.
	private final int H1;	  // number of neurons on layer 1.
	private final int H2;	  // number of neurons on layer 2.
	private final int H3;     // number of neurons on layer 3.
	private final String activationFunc;	// activation function.
	private final float learningRate;
	
	private List<Layer> layers = new ArrayList<Layer>(Arrays.asList(new Layer(), new Layer(), 
			new Layer(), new Layer(), new Layer()));
	
	public MLP(int H1, int H2, int H3, String activationFunc, float learningRate) {
		this.H1 = H1;
		this.H2 = H2;
		this.H3 = H3;
		this.activationFunc = activationFunc;
		this.learningRate = learningRate;
	}
	
	
	
	
	public void setInputLayer(float[] data) {
		Layer inputLayer = new Layer();
		
		for (int i = 0; i < d; i ++) {
			List<Float> inputNeuronInput = new ArrayList<Float>(Arrays.asList(data[i]));
			float inputNeuronOutput = inputNeuronInput.get(0);
			
			Neuron inputLayerNeuron = NeuronFactory.getNeuron(i, inputNeuronInput, learningRate);
			inputLayerNeuron.setOutput(inputNeuronOutput);
			inputLayer.append(inputLayerNeuron);
			
			ArrayList<Float> tempOutputs = inputLayer.getLayerOutputs();
			tempOutputs.add(inputNeuronOutput);
			inputLayer.setLayerOutputs(tempOutputs);
		}
		
		layers.set(0, inputLayer);
	}
	
	
	public void init(float[] data, int expectedClass) {		
		Layer firstHiddenLayer = new Layer();
		for (int i = 0; i < H1; i ++) {
			Neuron firstHiddenLayerNeuron = NeuronFactory.getNeuron(i, layers.get(0).getLayerOutputs(), 
					activationFunc, learningRate);
			firstHiddenLayerNeuron.setPreviousLayer(layers.get(0));
			firstHiddenLayer.append(firstHiddenLayerNeuron);
			
			ArrayList<Float> tempOutputs = firstHiddenLayer.getLayerOutputs();
			tempOutputs.add(firstHiddenLayerNeuron.getOutput());
			firstHiddenLayer.setLayerOutputs(tempOutputs);
		}
		layers.set(1, firstHiddenLayer);
		
		
		Layer secondHiddenLayer = new Layer();
		for (int i = 0; i < H2; i ++) {
			Neuron secondHiddenLayerNeuron = NeuronFactory.getNeuron(i, layers.get(1).getLayerOutputs(), 
					activationFunc, learningRate);
			secondHiddenLayerNeuron.setPreviousLayer(layers.get(1));
			secondHiddenLayer.append(secondHiddenLayerNeuron);
			
			ArrayList<Float> tempOutputs = secondHiddenLayer.getLayerOutputs();
			tempOutputs.add(secondHiddenLayerNeuron.getOutput());
			secondHiddenLayer.setLayerOutputs(tempOutputs);
		}
		
		layers.get(1).setNextLayerForAllNeurons(secondHiddenLayer);  // set H2 as all H1 neurons' next layer 
		layers.set(2, secondHiddenLayer);
		
		
		Layer thirdHiddenLayer = new Layer();
		for (int i = 0; i < H3; i ++) {
			Neuron thirdHiddenLayerNeuron = NeuronFactory.getNeuron(i, layers.get(2).getLayerOutputs(), 
					activationFunc, learningRate);
			thirdHiddenLayerNeuron.setPreviousLayer(layers.get(2));
			thirdHiddenLayer.append(thirdHiddenLayerNeuron);
			
			ArrayList<Float> tempOutputs = thirdHiddenLayer.getLayerOutputs();
			tempOutputs.add(thirdHiddenLayerNeuron.getOutput());
			thirdHiddenLayer.setLayerOutputs(tempOutputs);
		}
		
		layers.get(2).setNextLayerForAllNeurons(thirdHiddenLayer);
		layers.set(3, thirdHiddenLayer);
		
		
		Layer outputLayer = new Layer();
		for (int i = 0; i < K; i ++) {
			int expectedOutput = 0;
			
			if (i == expectedClass - 1) expectedOutput = 1;
			
			Neuron outputLayerNeuron = NeuronFactory.getNeuron(i, layers.get(3).getLayerOutputs(), 
					activationFunc, learningRate, expectedOutput);
			
			outputLayerNeuron.setPreviousLayer(layers.get(3));
			outputLayer.append(outputLayerNeuron);
			
			ArrayList<Float> tempOutputs = outputLayer.getLayerOutputs();
			tempOutputs.add(outputLayerNeuron.getOutput());
			outputLayer.setLayerOutputs(tempOutputs);
		}
		
		layers.get(3).setNextLayerForAllNeurons(outputLayer);
		layers.set(4, outputLayer);
		
	
		
	}
	
	
	/*
	 * X: The input data.
	 * y: The target values (class labels).
	 */
	
	public void forwardPass(float[] data) {
		//System.out.println(Arrays.toString(data));
		final int[] numOfNeurons = {d, H1, H2, H3, K};
		
		for (int i = 1; i < numOfNeurons.length; i ++) {
			
			ArrayList<Float> tempOutputs = new ArrayList<Float>();
			for (int j = 0; j < numOfNeurons[i]; j ++) {
				//System.out.println("============================================================");
				//System.out.println(layers.get(i).getNeurons().get(j).getOutput());
				layers.get(i).getNeurons().get(j).setInputs(layers.get(i-1).getLayerOutputs());
				layers.get(i).getNeurons().get(j).calculateOutput();
				//System.out.println(layers.get(i).getNeurons().get(j).getOutput());
				
				tempOutputs.add(layers.get(i).getNeurons().get(j).getOutput());
				layers.get(i).setLayerOutputs(tempOutputs);

			}
			
		}
		
	}
	public void fit (List<float[]> X, List<Integer> y) {
		boolean isFirstPass = true;
		
		for (int epoch = 0; epoch < 700; epoch ++) {
			for (int i = 0; i < X.size(); i ++) {
				float[] currentData = X.get(i);
				
				setInputLayer(currentData);
				
				if (isFirstPass) { 
					init(currentData, y.get(i));
					isFirstPass = false; 
				}
				else 
					forwardPass(currentData);
				
				//for (Layer layer: layers) for (Neuron neuron: layer.getNeurons()) System.out.println(neuron.toString());
					
				
				// backprop
				// gradientDescent
				
				
				
				
			}
			
			
			
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void predict (ArrayList<float[]> X) {}

}
