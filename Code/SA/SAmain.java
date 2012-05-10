import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA("../common/sheared.input",50,0);
	//simulatedAnnealing.finalOutput();
	simulatedAnnealing.setTemperature(5);
	for(int i = 0; i < 10; i++) {
	    simulatedAnnealing.runWithReplacement(500000,100000);
	    simulatedAnnealing.run(500000,100000);
	}
	simulatedAnnealing.finalOutput();
    }    
}
