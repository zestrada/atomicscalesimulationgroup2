import java.util.*;
import java.*;
import java.io.*;

public class GAmain {

    private static GA GeneticAlgorithm;
    
    public static void main(String[] args) {
	GeneticAlgorithm = new GA();
	GeneticAlgorithm.run(1000,50);
	GeneticAlgorithm.finalOutput();
    }    
}
