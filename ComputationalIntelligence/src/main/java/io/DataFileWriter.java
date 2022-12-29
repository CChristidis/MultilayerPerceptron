package main.java.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataFileWriter {
	public void write(String fileName, List<double[]> points, List<Integer> pointClasses) throws IOException { 
		
		  FileWriter dataFileWriter = new FileWriter(fileName + ".txt");
		  
		  dataFileWriter.write("x, y, class \n");
		  for (int i = 0; i < points.size(); i ++) 
			  dataFileWriter.write(points.get(i)[0] + "," + points.get(i)[1] + "," + pointClasses.get(i) + "\n");
		  
		  
		  dataFileWriter.close();
			    
		}

}
