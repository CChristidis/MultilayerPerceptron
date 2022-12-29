package main.java.dataset;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class PointGenerator {
	private List<double[]> trainData = new ArrayList<double[]>();  
	private List<Integer> trainDataClasses = new ArrayList<Integer>();
	
	private List<double[]> testData = new ArrayList<double[]>();
	private List<Integer> testDataClasses = new ArrayList<Integer>();
	
	PointClassifier pointClassifier = new PointClassifier();
	
	private double getRandomDouble(int min, int max) {
		Random randomDouble = new Random();
		return randomDouble.nextDouble() * (max - min) + min;
	}
	
	
	
	private double[] generateRandomPoint(int min, int max) {
		double x = getRandomDouble(-1, 1);
		double y = getRandomDouble(-1, 1);
		
		double[] point = {x, y};
		
		return point;
	}
	
	
	
	private int calculatePatternClass(double[] point) {
		if (pointClassifier.isClass1(point)) return 1;
		
		else if (pointClassifier.isClass2(point)) return 2;		
		
		return 3;
		
	}
	
	public void classifyPointFromFile(String path) {
		
	}
	
	public void generatePoints(String type) {
		int dataClass = 0;
		for (int i = 0; i < 4000; i ++) {
			
			double[] point = generateRandomPoint(-1, 1);
			dataClass = calculatePatternClass(point);
			
			
			if (type.equalsIgnoreCase("train")) {
				trainData.add(point);
				trainDataClasses.add(dataClass);
			}
			
			else {
				testData.add(point);
				testDataClasses.add(dataClass);
			}
		}
	}
	
	public List<Integer> getTestDataClasses() {
		return testDataClasses;
	}

	public List<double[]> getTrainData() {
		return trainData;
	}

	public List<Integer> getTrainDataClasses() {
		return trainDataClasses;
	}

	public List<double[]> getTestData() {
		return testData;
	}

	


	
	
	
	
	

}
