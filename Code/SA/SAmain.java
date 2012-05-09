import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static SA simulatedAnnealing;

    public static void main(String[] args) {
	simulatedAnnealing = new SA();
	simulatedAnnealing.run(1000000,10000);
	simulatedAnnealing.run(1000000,10000);
	simulatedAnnealing.finalOutput();
    }    
}
