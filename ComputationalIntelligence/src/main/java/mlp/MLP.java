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
	private final int K;  // number of classes
	private final int d;  // number of categories(columns) for every data.
	private final int H1;	  // number of neurons on layer 1.
	private final int H2;	  // number of neurons on layer 2.
	private final int H3;     // number of neurons on layer 3.
	private final String activationFunc;	// activation function.
	private final double learningRate;
	
	private List<Layer> layers = new ArrayList<Layer>(Arrays.asList(new Layer(), new Layer(), 
			new Layer(), new Layer(), new Layer()));
	
	
	
	public MLP(int d, int H1, int H2, int H3, int K, String activationFunc, double learningRate) {
		this.d = d;
		this.H1 = H1;
		this.H2 = H2;
		this.H3 = H3;
		this.K = K;
		this.activationFunc = activationFunc;
		this.learningRate = learningRate;
	}
	
	
	
	
	private void setInputLayer(double[] data) {
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
	

	
	
	private void init(double[] data, int expectedClass) {
		final int[] numOfNeurons = {this.d, this.H1, this.H2, this.H3, this.K};
		
		for (int i = 1; i < numOfNeurons.length; i ++) {
			Layer currentHiddenLayer = new Layer();
			
			for (int j = 0; j < numOfNeurons[i]; j ++) {
				Neuron currentHiddenLayerNeuron;
				
				if (i == numOfNeurons.length - 1) {
					int expectedOutput = 0;
					if (j == expectedClass - 1) expectedOutput = 1;
	
					currentHiddenLayerNeuron = NeuronFactory.getNeuron(j, layers.get(i - 1).getLayerOutputs(), 
							"logistic", learningRate, expectedOutput);
				}
				else {
					currentHiddenLayerNeuron = NeuronFactory.getNeuron(j, layers.get(i - 1).getLayerOutputs(), 
							activationFunc, learningRate);
				}
				
				currentHiddenLayerNeuron.setPreviousLayer(layers.get(i-1));
				currentHiddenLayer.append(currentHiddenLayerNeuron);
				
				ArrayList<Double> tempOutputs = currentHiddenLayer.getLayerOutputs();
				tempOutputs.add(currentHiddenLayerNeuron.getOutput());
				currentHiddenLayer.setLayerOutputs(tempOutputs);
			}
			
			if (i > 1) layers.get(i-1).setNextLayerForAllNeurons(currentHiddenLayer); // set H as all (H-1) neurons' next layer
			
			layers.set(i, currentHiddenLayer);
			
		}	

	}
	
	
	
	
	
	private void forwardPass(double[] data, int y, String type) {
		final int[] numOfNeurons = {d, H1, H2, H3, K};
		
		for (int i = 1; i < numOfNeurons.length; i ++) {
			
			Layer currentLayer = layers.get(i);
			Layer previousLayer = layers.get(i-1);
			ArrayList<Double> tempOutputs = new ArrayList<Double>();
			
			for (int j = 0; j < numOfNeurons[i]; j ++) {
				Neuron currentNeuron = currentLayer.getNeurons().get(j);
				
				currentNeuron.setInputs(previousLayer.getLayerOutputs());
				currentNeuron.calculateOutput();
				
			
				tempOutputs.add(currentNeuron.getOutput());	
				currentLayer.setLayerOutputs(tempOutputs);
	
				if (type.equalsIgnoreCase("train")) {
					if (i == numOfNeurons.length - 1 && j == y - 1) 
						((OutputLayerNeuron) currentNeuron).setExpectedOutput(1);
					else if (i == numOfNeurons.length - 1 && j != y - 1)
						((OutputLayerNeuron) currentNeuron).setExpectedOutput(0);
				}
			}	
		}
	}
	
	
	
	private void backprop() {
		final int[] numOfNeurons = {d, H1, H2, H3, K};
	
		for (int i = 4; i >= 1; i --) {
			Layer currentLayer = layers.get(i);
	
			for (int j = 0; j < numOfNeurons[i]; j ++) {
				Neuron currentNeuron = currentLayer.getNeurons().get(j);
				
				double delta = currentNeuron.calculateDelta();
				currentNeuron.setDelta(delta);
				currentNeuron.updateWeights();
			}
		}
		
		
	}
	
	
	private boolean checkIfTerminate(double oldSME, double SME, double threshold, int epoch) {
		return (Math.abs((oldSME - SME)) < threshold) && epoch >= 700;
	}
	
	
	/*
	 * X: The input data.
	 * y: The target values (class labels).
	 */
	public void fit (List<double[]> X, List<Integer> y, double threshold) {
		boolean isFirstPass = true;
		double SME = Double.POSITIVE_INFINITY;
		double oldSME = Double.NEGATIVE_INFINITY;
		
		
		int epoch = 1;
		
		while (!checkIfTerminate(oldSME, SME, threshold, epoch)) {
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
					forwardPass(currentData, currentClass, "train");
				
	
				ArrayList<Double> tempOutputs = new ArrayList<Double>();
				for (Neuron neuron: layers.get(4).getNeurons()) 
					tempOutputs.add(neuron.getOutput());
				
				
				
				double output = Collections.max(tempOutputs);
				int classification = (tempOutputs.indexOf(output) + 1);
				
				outputClasses.add(classification);
				outputs.add(tempOutputs);
				
				
				backprop();
				
			}
			
			oldSME = SME;
		
			Score metricCalculator = new Score(y, outputClasses, outputs);
			SME = metricCalculator.calculateSME();
			double accuracy = metricCalculator.calculateAccuracy();
			System.out.println("Epoch = " + epoch + ", SME = " + SME + ", accuracy = " + accuracy);
			
			epoch ++;
			
		}
	}
	
	
	
	
	
	
	
	public void predict (List<double[]> X, List<Integer> y) {
		List<Integer> outputClasses = new ArrayList<Integer>();
		List<List<Double>> outputs = new ArrayList<List<Double>>();
		
		for (int i = 0; i < X.size(); i ++) {
			double[] currentData = X.get(i);
			int currentClass = y.get(i);
			
			
			setInputLayer(currentData);
			forwardPass(currentData, currentClass, "test");
		
			
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
		metricCalculator.printPredictionMap();
		System.out.println("Prediction metrics:  SME = " + SME + ", Accuracy = " + accuracy);
	}

}
