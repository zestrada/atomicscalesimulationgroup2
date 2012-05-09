import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA("../common/square.input",50);
	simulatedAnnealing.setTemperature(5);
	//simulatedAnnealing.runWithReplacement(2000000,100000);
	//simulatedAnnealing.setTemperature(50);
	//simulatedAnnealing.run(2000000,100000);
	simulatedAnnealing.finalOutput();
    }    
}
