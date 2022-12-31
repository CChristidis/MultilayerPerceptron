package main.java.activationfunctions;

public class ActivationFunctionFactory {
	public static  ActivationFunctionStrategy getActivationFunction (String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty())
	        return null;
		
	    if (name.equalsIgnoreCase("relu"))
	        return new ReLU();
	    
	    if (name.equalsIgnoreCase("logistic"))
	        return new Logistic();
	    
	    if (name.equalsIgnoreCase("hyperbolic"))
	        return new HyperbolicTangent();
	    
	    else 
	    	throw new IllegalArgumentException();
		
	}

}
