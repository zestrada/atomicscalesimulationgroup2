import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA();
	simulatedAnnealing.setTemperature(100);
	simulatedAnnealing.runWithReplacement(200000,10000);
	//simulatedAnnealing.run(2000000,100000);
	simulatedAnnealing.finalOutput();
    }    
}
