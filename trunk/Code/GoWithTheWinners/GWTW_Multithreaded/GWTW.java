import java.io.*;

public class GWTW extends Thread { 

    private int ID;
    private SA simulatedAnnealing;
    private int steps;
    private int output;

    public GWTW(int steps, int output, int ID, SA simulatedAnnealing) {
	this.ID = ID;
	this.steps = steps;
	this.output = output;
	this.simulatedAnnealing = simulatedAnnealing;
    }

    public void run() {	
	simulatedAnnealing.runSilentWithReplacement(steps,output);
    }

}