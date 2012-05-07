import java.util.*;
import java.*;

public class SAmain {
  
    private static Random rng;
    private static Surface surface;
    private static Surface oldSurface;
    private static int N;

  public static void main(String[] args) {
      SAInit();
      SARun();
  }

  public static void SAInit() {
      TSPInOut tsp = new TSPInOut();
      rng = new Random();
      surface = tsp.readData("../common/test.input");
      N = surface.getN();
      System.out.println(N);
      for(int i = 0; i < N; i++) {
	  for(int j = i+1; j < N;j++) {
	      surface.connect(i,j);
	  }
      }
  }

  private static void SARun() {
      double d1,d2;
      double energy,oldEnergy;
      int x,y,x1,y1;
      oldEnergy = energy = surface.getEnergy();
      System.out.print("Energy: " + surface.getEnergy() + "\n");
      for(int i = 0; i < 1000000; i++) {
	  for(int j = 0; j < 1000; j++) {
	      x = rng.nextInt(N);
	      if(surface.hasMoreMaxVertex(x)) {
		  surface.minBind(x);
	      } else {
		  y = rng.nextInt(N);
		  d1 = surface.getDist(x,y);
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
		  for(int k = 0; k < 1000; k++) {
		      x1 = rng.nextInt(N);
		      y1 = rng.nextInt(N);
		      d2 = surface.getDist(x1,y1);
		      if(d2 < d1) {
			  surface.swapConnection(x,y,x1,y1);
		      }
		  }
	      }
	  }
	  System.out.println("Step: " + (i*1000) + "\tEnergy: " + surface.getEnergy() + "\tV: " + surface.maxVertex());
      }
      System.out.print("Final Energy: " + surface.getEnergy() + "\n");
      System.out.print("DONE\n");
  }
}
