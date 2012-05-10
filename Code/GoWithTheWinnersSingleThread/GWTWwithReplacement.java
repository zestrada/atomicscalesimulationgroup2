import java.io.*;

public class GWTWwithReplacement extends Thread { 

    private int ID;
    private SA simulatedAnnealing;
    private double energy;
   
    public GWTWwithReplacement(int ID, double temperature) {
	this.ID = ID;
	simulatedAnnealing = new SA();
	simulatedAnnealing.setTemperature(temperature);
    }

    public void setID(int ID) {
	this.ID = ID;
    }

    public void run() {
	try {
	    //System.out.println(ID);
	    simulatedAnnealing.runSilentWithReplacement(10000,10000);
	    energy = simulatedAnnealing.getEnergy();
	    System.out.println(ID + " " + energy);
	} catch (Exception e) {
	    System.out.println("Child interrupted.");
	}
    }
}