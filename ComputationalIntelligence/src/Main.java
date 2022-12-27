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
		//MLP mlp = new MLP(20, 24, 24, "logistic", learningRate);
		
		//double learningRate = 0.001;
		//MLP mlp = new MLP(6, 8, 9, "relu", learningRate);
		
		//double learningRate = 0.001;
		//MLP mlp = new MLP(17, 18, 18, "hyperbolic", learningRate);
		
		double learningRate = 0.0001;
		MLP mlp = new MLP(17, 18, 18, "hyperbolic", learningRate);
		
		double offset = 0.001;
		
		mlp.fit(trainData, trainDataClasses, offset);
		mlp.predict(testData, testDataClasses);

	}

}
