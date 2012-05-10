import java.io.*;

public class GWTWmain {
  
    private static Surface surface;
    private static GWTW[] g = new GWTW[16];
    private static GWTWwithReplacement[] gwr = new GWTWwithReplacement[16];
    private static SA[] sa = new SA[16];
    private static double[] energyArray = new double[16];
    private static double energy = 0.0;
    private static int[] index = new int[16];
    private static int lowIndex;
    private static String structure = new String("../../common/test.input");
    private static double temperature = 50;
    private static int steps = 100000;

    public static void main(String[] args) {
	gwtwInit();	
	for(int step = 0; step < 20; step++) {
	    gwrRun();
	    gwrWait();
	    lowestIndex();
	    sortEnergy();
	    System.out.println("Step: " + step + " Lowest energy: " + sa[lowIndex].getEnergy() + " in replicate" + lowIndex);
	    gWait();
	}
	for(int i = 0; i < 16; i++) {
	    sa[i].finalOutput("Thread_" + i);
	}
	System.out.print("DONE\n");
    }
    
    private static void gwrRun() {
	for(int i = 0; i < 16; i++) {
	    gwr[i] = new GWTWwithReplacement(steps,steps,i,sa[i]);
	    gwr[i].start();
	}      
    }

    private static void gRun() {
	for(int j = 0; j < 16/2; j++) {
	    g[index[j]] = new GWTW(steps,steps,j,sa[lowIndex]);
	}
	for(int j = 16/2; j < 16; j++) {
	    g[index[j]] = new GWTW(steps,steps,j,sa[index[j]]);
	}
    }

    private static void gWait() {
	for(int i = 0; i < 16; i++) {
	    try {
		g[i].join();
	    } catch(Exception e) {}
	}
    }

    private static void gwrWait() {
	for(int i = 0; i < 16; i++) {
	    try {
		gwr[i].join();
	    } catch(Exception e) {}
	}
    }

    private static void sortEnergy() {
	double tmpE;
	int tmpI;
	for(int i = 0; i < 16; i++) {
	    for(int j = 0; j < 16; j++) {
		if(energyArray[i] < energyArray[j]) {
		    tmpE = energyArray[i];
		    energyArray[i] = energyArray[j];
		    energyArray[j] = tmpE;
		    tmpI = index[i];
		    index[i] = index[j];
		    index[j] = tmpI;
		}
	    }
	}
    }
    
    private static void lowestIndex() {
	energyArray[0] = sa[0].getEnergy();
	double val = energyArray[0];
	for(int i = 1; i < 16; i++) {
	    energyArray[i] = sa[i].getEnergy();
	    if(val > energyArray[i]) {
		val = energyArray[i];
		lowIndex = i;
	    }
	}
    }

    public static void gwtwInit() {
	for(int i = 0; i < 16; i++) {
	    sa[i] = new SA(structure,temperature);
	}
    }
    
}
