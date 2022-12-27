package main.java.mlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import main.java.metrics.Score;
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
	
	
	private boolean checkIfTerminate(double oldSME, double SME, double offset) {
		return (oldSME - SME) < offset;
	}
	/*
	 * X: The input data.
	 * y: The target values (class labels).
	 */
	public void fit (List<double[]> X, List<Integer> y, double offset) {
		boolean isFirstPass = true;
		double SME = Double.POSITIVE_INFINITY;
		
		for (int epoch = 0; epoch < 700; epoch ++) {
			
			List<Integer> outputClasses = new ArrayList<Integer>();
			List<List<Double>> outputs = new ArrayList<List<Double>>();
			
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
				

				ArrayList<Double> tempOutputs = new ArrayList<Double>();
				for (Neuron neuron: layers.get(4).getNeurons()) 
					tempOutputs.add(neuron.getOutput());
				
				
				
				double output = Collections.max(tempOutputs);
				int classification = (tempOutputs.indexOf(output) + 1);
				
				outputClasses.add(classification);
				outputs.add(tempOutputs);
				
				
				backprop();
				
			}
			
			Score metricCalculator = new Score(y, outputClasses, outputs);
			double oldSME = SME;
			SME = metricCalculator.calculateSME();
			double accuracy = metricCalculator.calculateAccuracy();
			
			System.out.println("Epoch = " + epoch + ", SME = " + SME + ", accuracy = " + accuracy);
			
			if (checkIfTerminate(oldSME, SME, offset)) break;
			
		}
	}
	
	
	
	
	
	
	
	public void predict (List<double[]> X, List<Integer> y) {
		final int[] numOfNeurons = {d, H1, H2, H3, K};
		
		List<Integer> outputClasses = new ArrayList<Integer>();
		List<List<Double>> outputs = new ArrayList<List<Double>>();
		
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
			
			
			
			double output = Collections.max(tempOutputs);
			int classification = (tempOutputs.indexOf(output) + 1);
			
			outputClasses.add(classification);
			outputs.add(tempOutputs);
			}
		
		Score metricCalculator = new Score(y, outputClasses, outputs);
		double SME = metricCalculator.calculateSME();
		double accuracy = metricCalculator.calculateAccuracy();
		System.out.println("Prediction metrics:  SME = " + SME + ", Accuracy = " + accuracy);

	}

}
