package main.java.metrics;

import java.util.ArrayList;
import java.util.List;

public class Score {
	private List<Integer> expected;
	private List<Integer> predicted;
	private List<List<Double>> outputs;
	private List<String> predictionMap = new ArrayList<String>();
	
	public Score(List<Integer> expected, List<Integer> predicted, List<List<Double>> outputs) {
		this.expected = expected;
		this.predicted = predicted;
		this.outputs = outputs;
	}
	
	

	public double calculateSME() {
		double SME = 0;
		
		for (int i = 0; i < predicted.size(); i ++) { 
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
		
		for (int i = 0; i < predicted.size(); i ++) {
			if (expected.get(i) == predicted.get(i)) {
				predictionMap.add("+");
				accuratePredictions ++;
			}
			else predictionMap.add("-");
		}		
		
		return (double) accuratePredictions / predicted.size();
	}
	
	
	public void printPredictionMap() {
		System.out.println("\n\t===============================================================================");
		System.out.println("\t=========================== PREDICTION MAP ====================================");
		System.out.println("\t===============================================================================\n");
		System.out.print("\t\t| ");
		
		int distFrom30 = 0;
		for (int i = 0; i < predictionMap.size(); i ++) {
			if (i % 30 == 0 && i > 0) {
				System.out.println("| ");
				System.out.print("\t\t| ");
			}
			System.out.print(predictionMap.get(i) + " ");
			distFrom30 = 30 - i%30;
		}

		for (int i = 0; i < distFrom30*2 - 2; i ++)
			System.out.print(" ");
		System.out.println("|\n");
		
	}
	
	
	

}
