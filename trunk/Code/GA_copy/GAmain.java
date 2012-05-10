import java.util.*;
import java.*;
import java.io.*;

public class GAmain {

    private static GA GeneticAlgorithm;
    private static long startTime;
    public static void main(String[] args) {
        timerStart();
        GeneticAlgorithm = new GA();
        GeneticAlgorithm.run_noprint(10000,1000);
        GeneticAlgorithm.finalOutput();
        System.out.println("Total Time: "+ timerStop());
        
    }
    
    public static void timerStart() {
        startTime = System.currentTimeMillis();
    }
    
    public static long timerStop() {
        return System.currentTimeMillis()-startTime;
    }
}
