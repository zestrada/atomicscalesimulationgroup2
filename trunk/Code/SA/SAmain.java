import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA("../common/test.input",50,0);
	//simulatedAnnealing.finalOutput();
	simulatedAnnealing.setTemperature(100);
	for(int i = 0; i < 40; i++) {
	    simulatedAnnealing.runWithReplacement(100000,25000);
	    simulatedAnnealing.run(100000,25000);
	    simulatedAnnealing.setTemperature(100 - 2.5*(i+1));
	}
	simulatedAnnealing.finalOutput();
    }    
}
