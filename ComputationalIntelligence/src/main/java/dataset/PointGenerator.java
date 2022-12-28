package main.java.dataset;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class PointGenerator {
	private List<double[]> trainData = new ArrayList<double[]>();  
	private List<Integer> trainDataClasses = new ArrayList<Integer>();
	
	private List<double[]> testData = new ArrayList<double[]>();
	private List<Integer> testDataClasses = new ArrayList<Integer>();

	
	public double getRandomDouble(int min, int max) {
		Random randomDouble = new Random();
		return randomDouble.nextDouble() * (max - min) + min;
	}
	
	
	
	public double[] generateRandomPoint(int min, int max) {
		double x = getRandomDouble(-1, 1);
		double y = getRandomDouble(-1, 1);
		
		double[] point = {x, y};
		
		return point;
	}
	
	
	
	public void generatePoints(String type) {
		PointClassifier pointClassifier = new PointClassifier();
		int dataClass = 0;
		
		for (int i = 0; i < 4000; i ++) {
			
			double[] point = generateRandomPoint(-1, 1);
			
			if (pointClassifier.isClass1(point)) 
				dataClass = 1;
			
			else if (pointClassifier.isClass2(point)) 
				dataClass = 2;
			
			else
				dataClass = 3;
			
			 
			double[] classifiedPoint = {point[0], point[1]};
			
			if (type.equalsIgnoreCase("train")) {
				trainData.add(classifiedPoint);
				trainDataClasses.add(dataClass);
			}
			
			else {
				testData.add(classifiedPoint);
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
