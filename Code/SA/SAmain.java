import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA("../common/squareRandom.input",50,0);
	//simulatedAnnealing.finalOutput();
	simulatedAnnealing.setTemperature(0);
	for(int i = 0; i < 50; i++) {
	    simulatedAnnealing.runWithReplacement(100000,20000);
	    simulatedAnnealing.run(100000,20000);
	}
	simulatedAnnealing.finalOutput();
    }    
}
