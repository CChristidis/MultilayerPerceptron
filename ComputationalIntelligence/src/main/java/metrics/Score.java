package main.java.metrics;

import java.util.List;

public class Score {
	private List<Integer> expected;
	private List<Integer> predicted;
	private List<List<Double>> outputs;

	
	public Score(List<Integer> expected, List<Integer> predicted, List<List<Double>> outputs) {
		this.expected = expected;
		this.predicted = predicted;
		this.outputs = outputs;
	}
	
	

	public double calculateSME() {
		double SME = 0;
		
		for (int i = 0; i < expected.size(); i ++) { 
			int classOfPattern = expected.get(i);
			
			for (int j = 0; j < outputs.get(i).size(); j ++) {
				double outputLayerNeuronOutput = outputs.get(i).get(j); 
				
				if (j == classOfPattern - 1) SME += Math.pow(classOfPattern - outputLayerNeuronOutput, 2);
				
				else SME += Math.pow(-outputLayerNeuronOutput, 2);	
			}
		}
		
		return 0.5 * SME;
	}
	
	
	
	public double calculateAccuracy() {
		int accuratePredictions = 0;
		
		for (int i = 0; i < predicted.size(); i ++) if (expected.get(i) == predicted.get(i)) accuratePredictions ++;
	
		return (double) accuratePredictions / predicted.size();
	}
	
	
	
	

}
