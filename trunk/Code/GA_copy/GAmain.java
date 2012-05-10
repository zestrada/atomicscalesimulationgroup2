import java.util.*;
import java.*;
import java.io.*;

public class GAmain {

    private static GA GeneticAlgorithm;
    
    public static void main(String[] args) {
	GeneticAlgorithm = new GA();
	GeneticAlgorithm.run_noprint(1000,10);
	GeneticAlgorithm.finalOutput();
    }    
}
