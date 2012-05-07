import java.util.*;
import java.*;
import java.io.*;

public class SAmain {
  
    private static Random rng;
    private static Surface surface;
    private static Surface oldSurface;
    private static int N;

  public static void main(String[] args) {
      System.out.println("Init");
      SAInit();
      System.out.println("Running");
      SARun();
      SAFinal();
  }

  public static void SAInit() {
      TSPInOut tsp = new TSPInOut();
      rng = new Random();
      surface = tsp.readData("../common/test.input");
      N = surface.getN();
      System.out.println("There are: " + N + " particles in simulation");
      for(int i = 0; i < (N-3); i++) {
	  for(int j = i+1; j < i+2;j++) {
	      surface.connect(i,j);
	      System.out.println("Connecting " + i + " and " + j);
	  }
      }
      System.out.println("All " + N + " particles have been interconnected");
  }

  private static void SARun() {
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
		  /*
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
		      } */
	  }
	  System.out.println("Step: " + i + "\tEnergy: " + surface.getEnergy() + "\tV: " + surface.maxVertex());
      }
  }
    
    private static void SAFinal() {
	writeTrajectory();
	writeConnection();
	System.out.print("Final Energy: " + surface.getEnergy() + "\n");
	System.out.print("DONE\n");
    }

    private static void writeTrajectory() {
	double[] x = surface.getXCoords();
	double[] y = surface.getYCoords();
	try {
	    FileWriter fw = new FileWriter("surface.xyz");
	    String s = new String(N + "\n\n");
	    fw.write(s,0,s.length());
	    for(int i = 0; i < N; i++) {
		s = new String("C " + x[i] + " " + y[i] + " 0\n");
		fw.write(s,0,s.length());
	    }
	    fw.flush();
	    fw.close();
	} catch(Exception e) {
	    System.out.println("ERROR IN FILEWRITER");
	}
    }

    private static void writeConnection() {
	try {
	    boolean[][] con = surface.getConnection();    
	    FileWriter fw = new FileWriter("connection.dat");
	    String s = Arrays.deepToString(con);
	    fw.write(s,0,s.length());
	    fw.flush();
	    fw.close();
	} catch(Exception e) {
	}
    }
}
