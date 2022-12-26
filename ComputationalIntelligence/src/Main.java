import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.dataset.PointGenerator;
import main.java.mlp.MLP;

public class Main {

	public static void main(String[] args) {
		PointGenerator pg = new PointGenerator();
		pg.initTrainData();
		
		List<float[]> trainData = pg.getTrainData();
		List<Integer> trainDataClasses = pg.getTrainDataClasses();
		

		//for (int i = 0; i < trainData.size(); i ++) System.out.println(Arrays.toString(trainData.get(i)) + ": " + trainDataClasses.get(i));
		
		
		float learningRate = 0.1f;
		MLP mlp = new MLP(2, 3, 3, "logistic", learningRate);
		mlp.fit(trainData, trainDataClasses);

	}

}
