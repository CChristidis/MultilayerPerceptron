import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
			
			trainData = fileReader.parseData(trainDataPath);
			trainDataClasses = fileReader.parseClasses(trainDataPath);
			
			System.out.println("Train file \"" + args[0] + "\" successfully loaded.");
		}
		catch (FileNotFoundException | ArrayIndexOutOfBoundsException exception) {
			System.out.println("Train file not provided or not found.");
			System.out.println("Random points for train generated and saved in working directory: "
					+ System.getProperty("user.dir") + ".\n");
			
			PointGenerator pg = new PointGenerator();
			
			pg.generatePoints("train");
			
			trainData = pg.getTrainData();
			trainDataClasses = pg.getTrainDataClasses();
			
			fileWriter.write("train", trainData, trainDataClasses);	
		}
		
		
		try {
			String testDataPath = args[1];
			
			testData = fileReader.parseData(testDataPath);
			testDataClasses = fileReader.parseClasses(testDataPath);
			
			System.out.println("Test file \"" + args[1] + "\" successfully loaded.\n");
		}
		catch (FileNotFoundException | ArrayIndexOutOfBoundsException exception) {
			System.out.println("Test file not provided or not found.");
			System.out.println("Random points for test generated and saved in working directory: "
					+ System.getProperty("user.dir") + ".\n");
			
			PointGenerator pg = new PointGenerator();
			
			pg.generatePoints("test");
			
			testData = pg.getTestData();
			testDataClasses = pg.getTestDataClasses();

			fileWriter.write("test", testData, testDataClasses);	
		}
		
		
		Scanner ui = new Scanner(System.in);
		
		System.out.println("Give number of neurons for the first hidden layer: (e.g. 5) ");
		int firstHLNeurons = ui.nextInt();
		System.out.println("Give number of neurons for the second hidden layer: (e.g. 5) ");
		int secondHLNeurons = ui.nextInt();
		System.out.println("Give number of neurons for the third hidden layer: (e.g 5)");
		int thirdHLNeurons = ui.nextInt();
		System.out.println("Give activation function for hidden layers: logistic, hyperbolic or relu");
		String clean = ui.nextLine();
		String activationFunction = ui.nextLine();
		System.out.println("Give initial learning rate: (e.g. 0.001)");
		double learningRate = (double) ui.nextDouble();
		System.out.println("Give batch size: (min is 1 for serial update, max is 4000 for global update)");
		int batchSize = ui.nextInt();
		System.out.println("Give fit ending SME threshold: (e.g. 0.1)");
		double threshold = ui.nextDouble();
		
		int inputLayerNeurons = 2;
		int outputLayerNeurons = 3;
		MLP mlp = new MLP(inputLayerNeurons, firstHLNeurons, secondHLNeurons, thirdHLNeurons, outputLayerNeurons, 
				activationFunction, learningRate, batchSize);
		
		mlp.fit(trainData, trainDataClasses, threshold);
		mlp.predict(testData, testDataClasses);

	}

}
