import java.util.*;
import java.*;
import java.io.*;

public class SA {
    
    private Random rng;
    private Surface surface, oldSurface;
    private int N;
    private double temperature;
    private int numSteps;

    //Base constructor
    public SA() {
	System.out.println("Init");
	SAInit();
	System.out.println("Running");
	SARun3();
	SAFinal();
    }

    //Super constructor
    public SA(int i) {
	System.out.println("Init");
	SAInit();
	System.out.println("Running");
	SARun3();
	SAFinal();
    }     

    //Copy constructor
    public SA(SA obj) {
	this.rng = obj.getRNG();
	this.surface = new Surface(obj.getSurface());
	this.oldSurface = new Surface(obj.getOldSurface());
	this.N = obj.getN();
	this.temperature = obj.getTemperature();
	this.numSteps = obj.getNumSteps();
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

    public int getNumSteps() {
	return numSteps;
    }

    private  void SATest() {
	System.out.println("\nStarting Test1");
	oldSurface=surface;
	System.out.println(oldSurface.equals(surface));
	oldSurface=new Surface(surface);
	System.out.println(oldSurface.equals(surface));
    }

    private  void SATest2() {
	System.out.println("\nStarting Test2");
	oldSurface=new Surface(surface);
	System.out.println(surface.getEnergy());
	surface.swapConnection(0,1,0,2);
	System.out.println(surface.getEnergy());
	System.out.println(oldSurface.getEnergy());
    }

  private  void SAInit() {
      TSPInOut tsp = new TSPInOut();
      rng = new Random();
      surface = tsp.readData("../common/test.input");
      N = surface.getN();
      System.out.println("There are: " + N + " particles in simulation");
      int[] distIndex;
      int missingVertex;
      temperature = 1.0;
      for(int i = 0; i < N; i++) {
	  distIndex = surface.getShortestDistance(i);
	  //missingVertex = surface.missingVertex(i);
	  for(int j = 0; j < 3; j++) {
	      System.out.println("Connecting: " + i + " & " + distIndex[j] + " " + surface.getDist(i,distIndex[j]));
	      surface.connectUnsafe(i,distIndex[j]);
	  } 
      }
  }

    private  void SARun3() {
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
		surface.swapConnection(x0,y0,x1,y1);
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
	    System.out.println("Step: " + i + "\tEnergy: " + surface.getEnergy() + "\tV: " + surface.maxVertex() + "\t" + temperature);
	}
    }


    /*
      private  void SARun() {
      double d1,d2;
      double energy,oldEnergy;
      int x,y,x1,y1;
      oldEnergy = energy = surface.getEnergy();
      System.out.print("Energy: " + surface.getEnergy() + "\n");
      for(int i = 0; i < 100; i++) {
	  for(int j = 0; j < 100; j++) {
	      x = rng.nextInt(N);
	      y = rng.nextInt(N);
	      d1 = surface.getDist(x,y);
	      //for(x1 = 0; x1 < (N-1); x1++) {
	      //for(y1 = (x1+1); y1 < N; y1++) {
		      x1 = rng.nextInt(N);
		      y1 = rng.nextInt(N);
		      d2 = surface.getDist(x1,y1);
		      if(d2 < d1) {
			  try {oldSurface = (Surface)surface.clone();} catch(Exception e) {}
			  surface.swapConnection(x,y,x1,y1);
			  energy = surface.getEnergy();
			  if((energy-oldEnergy) > 0) {
			      surface = oldSurface;
			  } else {
			      oldEnergy = energy;
			  }
		      }
		      //}
		      //}
		      
		    x = rng.nextInt(N);
		    if(surface.hasMaxVertex(x)) {
		    surface.minBind(x);
		    } else {
		    y = rng.nextInt(N);
		    
		    try {
		  oldSurface = (Surface)surface.clone();
		  } catch(Exception e) {
		      System.err.println(e);
		  } 
		  surface.flipConnection(x,y);
		  energy = surface.getEnergy();
		  if((energy-oldEnergy) > 0 && (rng.nextDouble() > 0.5)) { 
		      surface = oldSurface;
		  } else {
		      oldEnergy = energy;
		      } 
	  }
	  System.out.println("Step: " + i + "\tEnergy: " + surface.getEnergy() + "\tV: " + surface.maxVertex());}
} 
*/
/* 
    private  void SARun() {
	  double energy,oldEnergy;
	  int x,y,missingVertex;
	  int[] listOfShortestDistance;
	  oldEnergy = energy = surface.getEnergy();
	  System.out.print("Energy: " + surface.getEnergy() + "\n");
	  for(int i = 0; i < 0; i++) {
	      for(int j = 0; j < N; j++) {
		  x = j;
		  missingVertex = surface.missingVertex(x);
		  if(missingVertex > 0) {
		      listOfShortestDistance = surface.getShortestFreeDistance(x);
		      for(int k = 0; k < missingVertex; k++) {
			  if(k < listOfShortestDistance.length && x == listOfShortestDistance[k]) {
			      missingVertex++;
			  } else {
			      System.out.println("Connecting: " + x + " , " +  listOfShortestDistance[k] + " " + surface.getDist(x,listOfShortestDistance[k]));
			      surface.connect(x,listOfShortestDistance[k]);
			  }
		      }
		  } else {
		      listOfShortestDistance = surface.getShortestFreeDistance(x);
		      for(int k = -missingVertex; k > 0; k--) {
			      System.out.println("Disconnecting: " + x + " , " +  listOfShortestDistance[k] + " " + surface.getDist(x,listOfShortestDistance[k]));
			      // System.out.println("Disconnecting: " + x + " , " +  listOfShortestDistance[k] + " " + k + " " + missingVertex);
			  surface.disconnect(x,listOfShortestDistance[k]);
		      }
		  }	  
	      }
	      System.out.println("Step: " + i + "\tEnergy: " + surface.getEnergy() + "\tV: " + surface.maxVertex());
	      
	  }
    }
*/  
/*    
    private  void SARun2() {
	double energy,oldEnergy,d1,d2;
	int x,y,x1,y1,missingVertex;
	int[] listOfShortestDistance;
	oldEnergy = energy = surface.getEnergy();
	System.out.print("Energy: " + surface.getEnergy() + "\n");
	for(int i = 0; i < 10000; i++) {
	    x = rng.nextInt(N);
	    y = rng.nextInt(N);
	    d1 = surface.getDist(x,y);
	    for(int j = 0; j < 10000; j++) {
		x1 = rng.nextInt(N);
		y1 = rng.nextInt(N);
		d2 = surface.getDist(x1,y1);
		if(d2 < d1) {
		    try {oldSurface = (Surface)surface.clone();} catch(Exception e) {}
		    surface.swapConnection(x,y,x1,y1);
		    energy = surface.getEnergy();
		    if((energy-oldEnergy) > 0) {
			surface = oldSurface;
		    } else {
			oldEnergy = energy;
		    }
		}
		      
	      }
	  System.out.println("Step: " + i + "\tEnergy: " + surface.getEnergy() + "\tV: " + surface.maxVertex());     	      
	  }
    }
  */  
    private  void SAFinal() {
	surface.writeTrajectory();
	surface.writeConnection();
	System.out.print("Final Energy: " + surface.getEnergy() + "\n");
	System.out.print("DONE\n");
    }
}