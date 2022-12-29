import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.dataset.PointGenerator;
import main.java.io.DataFileReader;
import main.java.io.DataFileWriter;
import main.java.mlp.MLP;

public class Main {

	public static void main(String[] args) throws IOException {
		
		DataFileWriter fileWriter = new DataFileWriter();
		DataFileReader fileReader = new DataFileReader();
		
		List<double[]> trainData = new ArrayList<double[]>();
		List<Integer> trainDataClasses = new ArrayList<Integer>();
		List<double[]> testData = new ArrayList<double[]>();
		List<Integer> testDataClasses = new ArrayList<Integer>();
		
		
		
		try {
			String trainDataPath = args[0];
			String testDataPath = args[1];
			
			
			
			trainData = fileReader.parseData(trainDataPath);
			trainDataClasses = fileReader.parseClasses(trainDataPath);
			
			testData = fileReader.parseData(testDataPath);
			testDataClasses = fileReader.parseClasses(testDataPath);
			
			System.out.println("Train file \"" + args[0] + "\" successfully loaded.");
			System.out.println("Test file \"" + args[1] + "\" successfully loaded.\n");
		}
		catch (FileNotFoundException | ArrayIndexOutOfBoundsException exception) {
			System.out.println("No file provided or file not found.");
			System.out.println("Random points generated and saved in working directory: "
					+ System.getProperty("user.dir") + ".\n");
			
			PointGenerator pg = new PointGenerator();
			
			pg.generatePoints("train");
			pg.generatePoints("test");
			
			trainData = pg.getTrainData();
			trainDataClasses = pg.getTrainDataClasses();
			
			testData = pg.getTestData();
			testDataClasses = pg.getTestDataClasses();
			
			fileWriter.write("train", trainData, trainDataClasses);
			fileWriter.write("test", testData, testDataClasses);	
		}
		
		

		
		//double learningRate = 0.001;
		//MLP mlp = new MLP(2, 20, 24, 24, 3, "logistic", learningRate);
		
		//double learningRate = 0.001;
		//MLP mlp = new MLP(2, 6, 8, 9, 3, "relu", learningRate);
		
		// MLP mlp = new MLP(2, 17, 18, 18, 3, "hyperbolic", learningRate, 1);
		
		double threshold = 1.0;  // if 1.0, then only run for 700 epochs.
		double learningRate = 0.001;
		int maxBatchSize = trainData.size();
		
		MLP mlp = new MLP(2, 17, 18, 18, 3, "hyperbolic", learningRate, 400);
		
		
		mlp.fit(trainData, trainDataClasses, threshold);
		mlp.predict(testData, testDataClasses);

	}

}
