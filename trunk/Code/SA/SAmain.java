import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA();
	simulatedAnnealing.setTemperature(100);
	simulatedAnnealing.runWithReplacement(10000000,100000);
	simulatedAnnealing.run(10000000,100000);
	simulatedAnnealing.finalOutput();
    }    
}
