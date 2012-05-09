public class GWTWmain {
  
    private static Surface surface;
    private static GWTW[] g = new GWTW[16];
    private static SA sa = new SA();
    private static double[] energyArray = new double[16];
    private static double energy = 0.0;
    private static int[] index = new int[16];
    private static int[] result = new int[8];
    private static int lowIndex;
    

    public static void main(String[] args) {
	//gwtwInit();
	
	for(int i = 0; i < 16; i++) {
	    g[i] = new GWTW(i,1);
	    energyArray[i] = 0;
	    index[i] = i;
	}
	for(int step = 0; step < 10; step++) {
	    for(int i = 0; i < 16; i++) {
		energyArray[i] = g[i].getEnergyWithReplacement();
		index[i] = i;
	    }      
	    lowestIndex();
	    eightWorst();
	    sa = g[lowIndex].getSA();
	    energy = energyArray[lowIndex];
	    for(int j = 0; j < 8; j++) {
		g[result[j]].setSA(sa);
	    }	    
	    System.out.println("Steps: " + step + "\tEnergy: " + energy + "\tReplicate: " + lowIndex);
	}
	for(int step = 10; step < 20; step++) {
	    for(int i = 0; i < 16; i++) {
		energyArray[i] = g[i].getEnergy();
		index[i] = i;
	    }      
	    lowestIndex();
	    eightWorst();
	    sa = g[lowIndex].getSA();
	    energy = energyArray[lowIndex];
	    for(int j = 0; j < 8; j++) {
		g[result[j]].setSA(sa);
	    }	    
	    System.out.println("Steps: " + step + "\tEnergy: " + energy + "\tReplicate: " + lowIndex);
	}
	System.out.println("Final energy: " + energy + "\tReplicate: " + lowIndex);
	sa = g[lowIndex].getSA();
	sa.finalOutput();
	System.out.print("DONE\n");
    }

    private static void eightWorst() {
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
	double val = energyArray[0];
	for(int i = 1; i < 16; i++) {
	    if(val > energyArray[i]) {
		val = energy;
		lowIndex = i;
	    }
	}
    }

    public static void gwtwInit() {
	//surface = new Surface();
    }
    
}
