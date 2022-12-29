package main.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DataFileReader {

	public List<double[]> parseData(String path) throws FileNotFoundException, NumberFormatException {
		File fileObject = new File(path);
        Scanner fileReader = new Scanner(fileObject);
        List<double[]> dataSet = new ArrayList<>();
  
      
        while (fileReader.hasNextLine()) {
          String[] row = fileReader.nextLine().split(",");
        
          double[] data;
          try {
        	  data = new double[]{Double.parseDouble(row[0]), Double.parseDouble(row[1])};
        	  dataSet.add(data);
          }
          catch (NumberFormatException e){ // ignore header
        	  continue;
          }
        }
        
		fileReader.close();	
		return dataSet;
	}
	
	public List<Integer> parseClasses(String path) throws FileNotFoundException, NumberFormatException {
		File fileObject = new File(path);
        Scanner fileReader = new Scanner(fileObject);
        List<Integer> classSet = new ArrayList<>();
  
      
        while (fileReader.hasNextLine()) {
          String[] row = fileReader.nextLine().split(",");
          
          int data;
          try {
        	  data = Integer.parseInt(row[2]);
        	  classSet.add(data);
          }
          catch (NumberFormatException e){ // ignore header
        	  continue;
          }
        }
        
		fileReader.close();
		
		return classSet;
	}
}
