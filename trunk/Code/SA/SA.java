import java.util.*;
import java.*;
import java.io.*;

public class SA {
    
    private Random rng;
    private Surface surface, oldSurface;
    private int N;
    private double temperature;
    private long numSteps;
    private TSPInOut tsp;

    //Base constructor
    public SA() {
	SAInit();
    }

    //Constructor
    public SA(double temperature) {
	SAInit(temperature);
    }     

    //Constructor 
    public SA(String filename, double temperature) {
	SAInit(filename,temperature);
    }     

    //Copy constructor
    public SA(SA obj) {
	this.rng = obj.getRNG();
	this.surface = new Surface(obj.getSurface());
	this.oldSurface = new Surface(obj.getOldSurface());
	this.N = obj.getN();
	this.temperature = obj.getTemperature();
	this.numSteps = obj.getNumSteps();
	this.tsp = obj.getTsp();
    }

    public TSPInOut getTsp() {
	return tsp;
    }

    public void setTsp(TSPInOut tsp) {
	this.tsp = tsp;
    }

    public double getEnergy() {
	return surface.getEnergy();
    }

    public Random getRNG() {
	return rng;
    }

    public Surface getSurface() {
	return surface;
    }

    public Surface getOldSurface() {
	return oldSurface;
    }

    public int getN() {
	return N;
    }

    public double getTemperature() {
	return temperature;
    }

    public double setTemperature(double temperature) {
	this.temperature = temperature;
	return temperature;
    }

    public long getNumSteps() {
	return numSteps;
    }

    private void preProcessor() {
	int[] distIndex;
	for(int i = 0; i < N; i++) {
	    distIndex = surface.getShortestDistance(i);
	    for(int j = 0; j < 3; j++) {
		//System.out.println("Connecting: " + i + " & " + distIndex[j] + " " + surface.getDist(i,distIndex[j]));
		surface.connectUnsafe(i,distIndex[j]);
	    } 
	}
    }

    private void SAInit() {
	tsp = new TSPInOut();
	rng = new Random();
	surface = tsp.readData("../common/test.input");
	N = surface.getN();
	System.out.println("Initializing simulation");
	System.out.println("There are: " + N + " particles in simulation");
	temperature = 1.0;
	numSteps = 0;
	preProcessor();
    }

    private void SAInit(double temperature) {
	tsp = new TSPInOut();
	rng = new Random();
	surface = tsp.readData("../common/test.input");
	N = surface.getN();
	System.out.println("There are: " + N + " particles in simulation");
	int[] distIndex;
	this.temperature = temperature;
	numSteps = 0;
	preProcessor();
    }

    private void SAInit(String filename, double temperature, int id) {
	tsp = new TSPInOut(id);
	rng = new Random();
	surface = tsp.readData(filename);
	N = surface.getN();
	System.out.println("There are: " + N + " particles in simulation");
	int[] distIndex;
	int missingVertex;
	this.temperature = temperature;
	numSteps = 0;
	preProcessor();
    }
    
    private void finishRun(long i) {
	tsp.outputEnergy();
	surface.writeConnection(numSteps);
    }

    public void run() {
	int x0,y0,x1,y1;
	double energy,oldEnergy,delta;
	oldEnergy=surface.getEnergy();
	for(int i = 0; i < 1000; i++) {
	    for(int j = 0; j < 1000; j++) {
		do {
		    x0 = rng.nextInt(N);
		    y0 = rng.nextInt(N);
		} while(x0 == y0);
		do {
		    x1 = rng.nextInt(N);
		    y1 = rng.nextInt(N);
		} while(x1 == y1);
		oldSurface=new Surface(surface);
		surface.swapConnectionUnsafe(x0,y0,x1,y1);
		energy=surface.getEnergy();
		delta=(energy-oldEnergy);
		//Calculate deltaE
		if(delta > 0) {
		    //If deltaE positive, calculate metropolis
		    if(rng.nextDouble() < Math.exp(-delta/temperature)) {
			//Metropolis accepted
			oldEnergy = energy;
		    } else {
			//Metropolis rejected
			surface=oldSurface;
		    }
		} else {
		    oldEnergy = energy;
		}
		//Increment temperature (after a 1M steps, the temperature will be 0.36 of the original)
		//temperature*=0.999999;
		temperature*=0.99999999;
	    }
	    numSteps += 1000;
	    tsp.recordEnergy(surface.getEnergy());
	    System.out.println("Step: " + numSteps + "\tEnergy: " + surface.getEnergy() + "\tmaxV: " + surface.maxVertex() + "\tminV: " + surface.minVertex() + "\t" + temperature);
	}
	finishRun(numSteps);
    }

    public void runSilent() {
	int x0,y0,x1,y1;
	double energy,oldEnergy,delta;
	oldEnergy=surface.getEnergy();
	for(int i = 0; i < 1000; i++) {
	    for(int j = 0; j < 1000; j++) {
		do {
		    x0 = rng.nextInt(N);
		    y0 = rng.nextInt(N);
		} while(x0 == y0);
		do {
		    x1 = rng.nextInt(N);
		    y1 = rng.nextInt(N);
		} while(x1 == y1);
		oldSurface=new Surface(surface);
		surface.swapConnectionUnsafe(x0,y0,x1,y1);
		energy=surface.getEnergy();
		delta=(energy-oldEnergy);
		//Calculate deltaE
		if(delta > 0) {
		    //If deltaE positive, calculate metropolis
		    if(rng.nextDouble() < Math.exp(-delta/temperature)) {
			//Metropolis accepted
			oldEnergy = energy;
		    } else {
			//Metropolis rejected
			surface=oldSurface;
		    }
		} else {
		    oldEnergy = energy;
		}
		//Increment temperature (after a 1M steps, the temperature will be 0.36 of the original)
		//temperature*=0.999999;
		temperature*=0.99999999;
	    }
	    numSteps += 1000;
	    tsp.recordEnergy(surface.getEnergy());
	}
	finishRun(numSteps);
    }

    public void run(int step, int output) {
	int x0,y0,x1,y1;
	int tmpStep = step/output;
	double energy,oldEnergy,delta;
	oldEnergy=surface.getEnergy();
	for(int i = 0; i < tmpStep; i++) {
	    System.gc();
	    for(int j = 0; j < output; j++) {
		do {
		    x0 = rng.nextInt(N);
		    y0 = rng.nextInt(N);
		} while(x0 == y0);
		do {
		    x1 = rng.nextInt(N);
		    y1 = rng.nextInt(N);
		} while(x1 == y1);
		oldSurface=new Surface(surface);
		surface.swapConnectionUnsafe(x0,y0,x1,y1);
		energy=surface.getEnergy();
		delta=(energy-oldEnergy);
		//Calculate deltaE
		if(delta > 0) {
		    //If deltaE positive, calculate metropolis
		    if(rng.nextDouble() < Math.exp(-delta/temperature)) {
			//Metropolis accepted
			oldEnergy = energy;
		    } else {
			//Metropolis rejected
			surface=oldSurface;
		    }
		} else {
		    oldEnergy = energy;
		}
		//Increment temperature (after a 1M steps, the temperature will be 0.36 of the original)
		temperature*=0.999999;
	    }
	    numSteps += (output);
	    tsp.recordEnergy(surface.getEnergy());
	    System.out.println("Step: " + numSteps + "\tEnergy: " + surface.getEnergy() + "\tmaxV: " + surface.maxVertex() + "\tminV: " + surface.minVertex() + "\t" + temperature);
	}
	finishRun(numSteps);
    }

    public void runSilent(int step, int output) {
	int x0,y0,x1,y1;
	int tmpStep = step/output;
	double energy,oldEnergy,delta;
	oldEnergy=surface.getEnergy();
	for(int i = 0; i < tmpStep; i++) {
	    System.gc();
	    for(int j = 0; j < output; j++) {
		do {
		    x0 = rng.nextInt(N);
		    y0 = rng.nextInt(N);
		} while(x0 == y0);
		do {
		    x1 = rng.nextInt(N);
		    y1 = rng.nextInt(N);
		} while(x1 == y1);
		oldSurface=new Surface(surface);
		surface.swapConnectionUnsafe(x0,y0,x1,y1);
		energy=surface.getEnergy();
		delta=(energy-oldEnergy);
		//Calculate deltaE
		if(delta > 0) {
		    //If deltaE positive, calculate metropolis
		    if(rng.nextDouble() < Math.exp(-delta/temperature)) {
			//Metropolis accepted
			oldEnergy = energy;
		    } else {
			//Metropolis rejected
			surface=oldSurface;
		    }
		} else {
		    oldEnergy = energy;
		}
		//Increment temperature (after a 1M steps, the temperature will be 0.36 of the original)
		temperature*=0.999999;
	    }
	    numSteps += (output);
	    tsp.recordEnergy(surface.getEnergy());
	}
	finishRun(numSteps);
    }

    public void runWithReplacement(int step, int output) {
	int x0,y0,flag;
	int tmpStep = step/output;
	double energy,oldEnergy,delta;
	oldEnergy=surface.getEnergy();
	for(int i = 0; i < tmpStep; i++) {
	    System.gc();
	    //surface.testVertices();
	    //surface.printVertices();
	    for(int j = 0; j < output; j++) {
		flag = rng.nextInt(2);
		do {
		    x0 = rng.nextInt(N);
		    y0 = rng.nextInt(N);
		} while(x0 == y0);
		oldSurface=new Surface(surface);
		if(flag == 0) {
		    surface.connectUnsafe(x0,y0);
			} else { 
		    surface.disconnect(x0,y0);
		}
		energy=surface.getEnergy();
		delta=(energy-oldEnergy);
		//Calculate deltaE
		if(delta > 0) {
		    //If deltaE positive, calculate metropolis
		    if(rng.nextDouble() < Math.exp(-delta/temperature)) {
			//Metropolis accepted
			oldEnergy = energy;
		    } else {
			//Metropolis rejected
			surface=oldSurface;
		    }
		} else {
		    oldEnergy = energy;
		}
		//Increment temperature (after a 1M steps, the temperature will be 0.36 of the original)
		//temperature*=0.999999;
		temperature*=0.99999999;
	    }
	    numSteps += (output);
	    tsp.recordEnergy(surface.getEnergy());
	    System.out.println("Step: " + numSteps + "\tEnergy: " + surface.getEnergy() + "\tmaxV: " + surface.maxVertex() + "\tminV: " + surface.minVertex() + "\t" + temperature);
	}
	finishRun(numSteps);
    }

    public void runSilentWithReplacement(int step, int output) {
	int x0,y0,flag;
	int tmpStep = step/output;
	double energy,oldEnergy,delta;
	oldEnergy=surface.getEnergy();
	for(int i = 0; i < tmpStep; i++) {
	    System.gc();
	    for(int j = 0; j < output; j++) {
		flag = rng.nextInt(2);
		do {
		    x0 = rng.nextInt(N);
		    y0 = rng.nextInt(N);
		} while(x0 == y0);
		oldSurface=new Surface(surface);
		if(flag == 0) {
		    surface.connectUnsafe(x0,y0);
			} else { 
		    surface.disconnect(x0,y0);
		}
		energy=surface.getEnergy();
		delta=(energy-oldEnergy);
		//Calculate deltaE
		if(delta > 0) {
		    //If deltaE positive, calculate metropolis
		    if(rng.nextDouble() < Math.exp(-delta/temperature)) {
			//Metropolis accepted
			oldEnergy = energy;
		    } else {
			//Metropolis rejected
			surface=oldSurface;
		    }
		} else {
		    oldEnergy = energy;
		}
		//Increment temperature (after a 1M steps, the temperature will be 0.36 of the original)
		//temperature*=0.999999;
		temperature*=0.99999999;
	    }
	    numSteps += (output);
	    tsp.recordEnergy(surface.getEnergy());
	}
	finishRun(numSteps);
    }

    public void finalOutput() {
	//surface.getAngles();
	surface.writeTrajectory();
	surface.writeConnection();
	System.out.print("Final Energy: " + surface.getEnergy() + "\n");
	System.out.print("DONE\n");
    }

    public void finalOutput(String s) {
	//surface.getAngles();
	surface.writeTrajectory();
	surface.writeConnection(s);
	System.out.print("Final Energy: " + surface.getEnergy() + "\n");
	System.out.print("DONE\n");
    }

}