import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA();
	simulatedAnnealing.setTemperature(50);
	simulatedAnnealing.runWithReplacement(2000000,10000);
	simulatedAnnealing.setTemperature(50);
	simulatedAnnealing.run(5000000,100000);
	simulatedAnnealing.finalOutput();
    }    
}
