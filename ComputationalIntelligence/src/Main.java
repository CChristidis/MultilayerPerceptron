import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.dataset.PointGenerator;
import main.java.mlp.MLP;

public class Main {

	public static void main(String[] args) {
		PointGenerator pg = new PointGenerator();
		pg.initTrainData();
		pg.initTestData();
		
		List<double[]> trainData = pg.getTrainData();
		List<Integer> trainDataClasses = pg.getTrainDataClasses();
		
		List<double[]> testData = pg.getTestData();
		List<Integer> testDataClasses = pg.getTestDataClasses();
		
		//for (int i = 0; i < trainData.size(); i ++) System.out.println(Arrays.toString(testData.get(i)) + ": " + testDataClasses.get(i));
		
		/*
		double[] f1 = {0.6860526383233705, 0.4402289746228842};
		int i1 = 2;
		
		double[] f2 = {-0.451826062661979, 0.1983054046334356};
		int i2 = 3;
		
		List<double[]> trainData = new ArrayList<double[]>();
		List<Integer> trainDataClasses = new ArrayList<Integer>();
		
		trainData.add(f1);
		trainData.add(f2);
		trainDataClasses.add(i1);
		trainDataClasses.add(i2);
		*/
		double learningRate = 0.1;
		MLP mlp = new MLP(26, 28, 29, "logistic", learningRate);
		mlp.fit(trainData, trainDataClasses);
		mlp.predict(testData, testDataClasses);

	}

}
