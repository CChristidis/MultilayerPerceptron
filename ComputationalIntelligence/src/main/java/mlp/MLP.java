package main.java.mlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import main.java.mlp.neuron.HiddenLayerNeuron;
import main.java.mlp.neuron.Neuron;
import main.java.mlp.neuron.NeuronFactory;
import main.java.mlp.neuron.OutputLayerNeuron;

public class MLP {
	private final int K = 3;  // number of classes
	private final int d = 2;  // number of categories(columns) for every data.
	private final int H1;	  // number of neurons on layer 1.
	private final int H2;	  // number of neurons on layer 2.
	private final int H3;     // number of neurons on layer 3.
	private final String activationFunc;	// activation function.
	private final double learningRate;
	
	private List<Layer> layers = new ArrayList<Layer>(Arrays.asList(new Layer(), new Layer(), 
			new Layer(), new Layer(), new Layer()));
	
	
	
	public MLP(int H1, int H2, int H3, String activationFunc, double learningRate) {
		this.H1 = H1;
		this.H2 = H2;
		this.H3 = H3;
		this.activationFunc = activationFunc;
		this.learningRate = learningRate;
	}
	
	
	
	
	public void setInputLayer(double[] data) {
		Layer inputLayer = new Layer();
		
		for (int i = 0; i < d; i ++) {
			List<Double> inputNeuronInput = new ArrayList<Double>(Arrays.asList(data[i]));
			double inputNeuronOutput = inputNeuronInput.get(0);
			
			Neuron inputLayerNeuron = NeuronFactory.getNeuron(i, inputNeuronInput, learningRate);
			inputLayerNeuron.setOutput(inputNeuronOutput);
			inputLayer.append(inputLayerNeuron);
			
			ArrayList<Double> tempOutputs = inputLayer.getLayerOutputs();
			tempOutputs.add(inputNeuronOutput);
			inputLayer.setLayerOutputs(tempOutputs);
		}
		
		layers.set(0, inputLayer);
	}
	
	
	public void init(double[] data, int expectedClass) {		
		Layer firstHiddenLayer = new Layer();
		for (int i = 0; i < H1; i ++) {
			Neuron firstHiddenLayerNeuron = NeuronFactory.getNeuron(i, layers.get(0).getLayerOutputs(), 
					activationFunc, learningRate);
			firstHiddenLayerNeuron.setPreviousLayer(layers.get(0));
			firstHiddenLayer.append(firstHiddenLayerNeuron);
			
			ArrayList<Double> tempOutputs = firstHiddenLayer.getLayerOutputs();
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
			
			ArrayList<Double> tempOutputs = secondHiddenLayer.getLayerOutputs();
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
			
			ArrayList<Double> tempOutputs = thirdHiddenLayer.getLayerOutputs();
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
			
			ArrayList<Double> tempOutputs = outputLayer.getLayerOutputs();
			tempOutputs.add(outputLayerNeuron.getOutput());
			outputLayer.setLayerOutputs(tempOutputs);
		}
		
		layers.get(3).setNextLayerForAllNeurons(outputLayer);
		layers.set(4, outputLayer);
		
	
		
	}
	
	
	
	public void forwardPass(double[] data, int y) {
		final int[] numOfNeurons = {d, H1, H2, H3, K};
		
		for (int i = 1; i < numOfNeurons.length; i ++) {
			
			
			ArrayList<Double> tempOutputs = new ArrayList<Double>();
			for (int j = 0; j < numOfNeurons[i]; j ++) {
				
				
				layers.get(i).getNeurons().get(j).setDelta(0);
			
				layers.get(i).getNeurons().get(j).setInputs(layers.get(i-1).getLayerOutputs());
				
				
				layers.get(i).getNeurons().get(j).calculateOutput();
				
				
				
				
				tempOutputs.add(layers.get(i).getNeurons().get(j).getOutput());
			
				
				layers.get(i).setLayerOutputs(tempOutputs);
	
				
				if (i == numOfNeurons.length - 1 && j == y - 1) 
					((OutputLayerNeuron) layers.get(i).getNeurons().get(j)).setExpectedOutput(1);
				else if (i == numOfNeurons.length - 1 && j != y - 1)
					((OutputLayerNeuron) layers.get(i).getNeurons().get(j)).setExpectedOutput(0);
				
	
				
		
				

			}
			
		}
		
	}
	
	
	
	public void backprop() {
		final int[] numOfNeurons = {d, H1, H2, H3, K};
	
		for (int i = 4; i >= 1; i --) {
			for (int j = 0; j < numOfNeurons[i]; j ++) {
				
			
				double delta = layers.get(i).getNeurons().get(j).calculateDelta();
				layers.get(i).getNeurons().get(j).setDelta(delta);
				
				
				layers.get(i).getNeurons().get(j).updateWeights();
			}
		}
		
		
	}
	
	public double calculateSME(List<int[]> o) {
		double SME = 0;
		
		
		for (int i = 0; i < o.size(); i ++) {
			//System.out.println(o.get(i)[1] - o.get(i)[0]);
			SME += Math.pow(o.get(i)[1] - o.get(i)[0], 2);	
		}
		System.out.println(SME);
		return (1/o.size())*SME;
	}
	
	/*
	 * X: The input data.
	 * y: The target values (class labels).
	 */
	public void fit (List<double[]> X, List<Integer> y) {
		boolean isFirstPass = true;
		double error = 10000;
		
		for (int epoch = 0; epoch < 700; epoch ++) {
			
			List<int[]> outputs = new ArrayList<int[]>();
			
			for (int i = 0; i < X.size(); i ++) {
				
				double[] currentData = X.get(i);
				int currentClass = y.get(i);
				
				setInputLayer(currentData);
			
				if (isFirstPass) { 
					init(currentData, currentClass);
					isFirstPass = false; 
				}
				else 
					forwardPass(currentData, currentClass);
				
				
				
				
				
				//for (Neuron neuron: layers.get(4).getNeurons()) 
					//tempOutputs.add(neuron.getOutput());
				
				//System.out.println(Arrays.toString(tempOutputs.toArray()));
				
				ArrayList<Double> tempOutputs = new ArrayList<Double>();
				for (Neuron neuron: layers.get(4).getNeurons()) 
					tempOutputs.add(neuron.getOutput());
				
				
				
				//System.out.println(Arrays.toString(tempOutputs.toArray()));
				int classification = (tempOutputs.indexOf(Collections.max(tempOutputs)) + 1);
				
				int[] o = {classification, currentClass};
				
				outputs.add(o);
				
				
				
				backprop();
				
				//System.out.println("==============================================");
				//for (Layer layer: layers) for (Neuron neuron: layer.getNeurons()) System.out.println(neuron.toString()); 
				
				
				
				
				//System.out.println();
				//outputs.add(tempOutputs.indexOf(Collections.max(tempOutputs)) + 1);
				
			}
			
			
			error = calculateSME(outputs);
			System.out.println("Epoch = " + epoch + ", Train SME = " + error);
			
		
			
			
		}
	}
	
	
	
	
	
	
	
	
	
	public double calculateAccuracy(List<Integer> expected, List<Integer> actual) {
		int accuratePredictions = 0;
		
		for (int i = 0; i < expected.size(); i ++) {
			System.out.println("expected: " +expected.get(i) + ", got: " + actual.get(i));
			if (expected.get(i) == actual.get(i)) accuratePredictions ++;
		}
		
		System.out.println("Accurate = " + accuratePredictions);
		
		return (double) accuratePredictions / 4000;
		
	}
	
	
	
	public void predict (List<double[]> X, List<Integer> y) {
		final int[] numOfNeurons = {d, H1, H2, H3, K};
		
		List<Integer> outputs = new ArrayList<Integer>();
		
		for (double[] data: X) {
			
			setInputLayer(data);
			
			for (int i = 1; i < numOfNeurons.length; i ++) {
				
				ArrayList<Double> tempOutputs = new ArrayList<Double>();
				
				for (int j = 0; j < numOfNeurons[i]; j ++) {
					layers.get(i).getNeurons().get(j).setInputs(layers.get(i-1).getLayerOutputs());
					layers.get(i).getNeurons().get(j).calculateOutput();
					
					tempOutputs.add(layers.get(i).getNeurons().get(j).getOutput());
					layers.get(i).setLayerOutputs(tempOutputs);
					
					}
				}
			
			ArrayList<Double> tempOutputs = new ArrayList<Double>();
			for (Neuron neuron: layers.get(4).getNeurons()) 
				tempOutputs.add(neuron.getOutput());
			
			
			
			//System.out.println(Arrays.toString(tempOutputs.toArray()));
			int classification = (tempOutputs.indexOf(Collections.max(tempOutputs)) + 1);
			
			
			
			outputs.add(classification);
			}
		
		System.out.println("Accuracy = " + calculateAccuracy(y, outputs));
		//System.out.println("SME = " + calculateSME(y, outputs));
		//for (int output: outputs) System.out.println(output);
		
	}

}
