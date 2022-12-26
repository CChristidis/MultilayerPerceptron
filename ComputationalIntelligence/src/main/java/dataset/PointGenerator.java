package main.java.dataset;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class PointGenerator {
	private List<float[]> trainData = new ArrayList<float[]>();  // point i class == trainData.get(i)[2]
	private List<Integer> trainDataClasses = new ArrayList<Integer>();
	
	private List<float[]> testData = new ArrayList<float[]>();

	
	
	public static float generateRandomFloat(float min, float max) {
	    Random rand = new Random();
	    
	    return rand.nextFloat() * (max - min) + min;

	}
	
	public float[] generateRandomPoint(int min, int max) {
		float x = generateRandomFloat(-1, 1);
		float y = generateRandomFloat(-1, 1);
		
		float[] point = {x, y};
		
		return point;
	}
	
	
	
	public void initTrainData() {
		PointClassifier pointClassifier = new PointClassifier();
		int dataClass = 0;
		
		for (int i = 0; i < 4000; i ++) {
			
			float[] point = generateRandomPoint(-1, 1);
			
			if (pointClassifier.isClass1(point)) 
				dataClass = 1;
			
			else if (pointClassifier.isClass2(point)) 
				dataClass = 2;
			
			else
				dataClass = 3;
			
			 
			float[] classifiedPoint = {point[0], point[1]};
			trainData.add(classifiedPoint);
			trainDataClasses.add(dataClass);
		}	
	}
	
	
	public void initTestData() {
		for (int i = 0; i < 4000; i ++) {
			float[] point = generateRandomPoint(-1, 1);
			testData.add(point);
		}
	}
	
	
	
	
	public List<float[]> getTrainData() {
		return trainData;
	}

	public List<Integer> getTrainDataClasses() {
		return trainDataClasses;
	}

	public List<float[]> getTestData() {
		return testData;
	}

	public void setTrainData(List<float[]> trainData) {
		this.trainData = trainData;
	}

	public void setTrainDataClasses(List<Integer> trainDataClasses) {
		this.trainDataClasses = trainDataClasses;
	}

	public void setTestData(List<float[]> testData) {
		this.testData = testData;
	}


	
	
	
	
	

}
