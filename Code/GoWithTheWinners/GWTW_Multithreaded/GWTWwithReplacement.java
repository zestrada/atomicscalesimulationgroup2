import java.io.*;

public class GWTWwithReplacement extends Thread { 

    private int ID;
    private SA simulatedAnnealing;
    private int steps;
    private int output;

    public GWTWwithReplacement(int steps, int output, int ID, SA simulatedAnnealing) {
	this.ID = ID;
	this.steps = steps;
	this.output = output;
	this.simulatedAnnealing = simulatedAnnealing;
    }

    public void run() {	
	simulatedAnnealing.runSilentWithReplacement(steps,output);
    }

    public SA getSA() {
	return simulatedAnnealing;
    }
}