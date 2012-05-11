import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA("../common/squareRandom.input",50,0);
	//simulatedAnnealing.finalOutput();
	simulatedAnnealing.setTemperature(5);
	for(int i = 0; i < 10; i++) {
	    simulatedAnnealing.runWithReplacement(50000,10000);
	    simulatedAnnealing.run(50000,10000);
	}
	simulatedAnnealing.finalOutput();
    }    
}
