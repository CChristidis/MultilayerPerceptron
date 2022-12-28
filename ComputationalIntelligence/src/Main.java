import java.util.List;

import main.java.dataset.PointGenerator;
import main.java.mlp.MLP;

public class Main {

	public static void main(String[] args) {
		PointGenerator pg = new PointGenerator();
		pg.generatePoints("train");
		pg.generatePoints("test");
		
		List<double[]> trainData = pg.getTrainData();
		List<Integer> trainDataClasses = pg.getTrainDataClasses();
		
		List<double[]> testData = pg.getTestData();
		List<Integer> testDataClasses = pg.getTestDataClasses();
		
		
		
		//double learningRate = 0.001;
		//MLP mlp = new MLP(2, 20, 24, 24, 3, "logistic", learningRate);
		
		//double learningRate = 0.001;
		//MLP mlp = new MLP(2, 6, 8, 9, 3, "relu", learningRate);
		
		
		double threshold = 0.001;
		
		double learningRate = 0.001;
		MLP mlp = new MLP(2, 17, 18, 18, 3, "hyperbolic", learningRate);
		
		
		mlp.fit(trainData, trainDataClasses, threshold);
		mlp.predict(testData, testDataClasses);

	}

}
