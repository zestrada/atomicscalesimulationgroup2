import java.io.*;

public class GWTW extends Thread { 

    private int ID;
    private SA simulatedAnnealing;
    private double energy;
   
    public GWTW(int ID, double temperature) {
	this.ID = ID;
	simulatedAnnealing = new SA("",temperature,ID);
	simulatedAnnealing.setTemperature(temperature);
    }

    public double getEnergyWithReplacement() {	
	simulatedAnnealing.runSilentWithReplacement(1000,1000);
	energy = simulatedAnnealing.getEnergy();
	return energy;
    }

    public double getEnergy() {	
	simulatedAnnealing.runSilent(1000,1000);
	energy = simulatedAnnealing.getEnergy();	
	return energy;
    }

    public SA getSA() {
	return simulatedAnnealing;
    }

    public void setSA(SA obj) {
	simulatedAnnealing = new SA(obj);
    }
}