package main.java.dataset;

public class PointClassifier {
	
	
	public boolean isClass1(float[] point) {
		return (Math.pow(point[0] - 0.5, 2) + Math.pow(point[1] - 0.5, 2) < 0.2 && point[1] > 0.5) ||
			   (Math.pow(point[0] + 0.5, 2) + Math.pow(point[1] + 0.5, 2) < 0.2 && point[1] > -0.5) ||		   
			   (Math.pow(point[0] - 0.5, 2) + Math.pow(point[1] + 0.5, 2) < 0.2 && point[1] > -0.5) ||   
			   (Math.pow(point[0] + 0.5, 2) + Math.pow(point[1] - 0.5, 2) < 0.2 && point[1] > 0.5);
	}
	
	public boolean isClass2(float[] point) {
		return (Math.pow(point[0] - 0.5, 2) + Math.pow(point[1] - 0.5, 2) < 0.2 && point[1] < 0.5) ||
			   (Math.pow(point[0] + 0.5, 2) + Math.pow(point[1] + 0.5, 2) < 0.2 && point[1] < -0.5) ||		 
			   (Math.pow(point[0] - 0.5, 2) + Math.pow(point[1] + 0.5, 2) < 0.2 && point[1] < -0.5);
	}
	
	
	
	
}
