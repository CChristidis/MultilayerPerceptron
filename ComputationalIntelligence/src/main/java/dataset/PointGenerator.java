package main.java.dataset;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class PointGenerator {
	private List<double[]> trainData = new ArrayList<double[]>();  // point i class == trainData.get(i)[2]
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
	
	
	
	public void initTrainData() {
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
			trainData.add(classifiedPoint);
			trainDataClasses.add(dataClass);
		}	
	}
	
	
	public void initTestData() {
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
			testData.add(classifiedPoint);
			testDataClasses.add(dataClass);
		}	
	}
	
	
	
	
	public List<Integer> getTestDataClasses() {
		return testDataClasses;
	}

	public void setTestDataClasses(List<Integer> testDataClasses) {
		this.testDataClasses = testDataClasses;
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

	public void setTrainData(List<double[]> trainData) {
		this.trainData = trainData;
	}

	public void setTrainDataClasses(List<Integer> trainDataClasses) {
		this.trainDataClasses = trainDataClasses;
	}

	public void setTestData(List<double[]> testData) {
		this.testData = testData;
	}


	
	
	
	
	

}
