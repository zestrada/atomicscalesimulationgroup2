import java.io.*;
import java.util.*;

public class GApopulation { 

    private int ID;
    private Surface surface;
    private int N;
    private static Random rng = new Random();
    
    public GApopulation(int ID) {
	this.ID = ID;
	GAInit();
    }
    
    public double getEnergy() {
        return surface.getEnergy();
    }
    
    public Surface getSurface() {
        return new Surface(surface);
    }
    
    public void setSurface(Surface s) {
        surface = s;
    }
    
    public int getN() {
        return N;
    }
    
    private void GAInit() {
        TSPInOut tsp = new TSPInOut();
        tsp.disableStdout();
        surface = tsp.readData("../GA_copy/smallsquare.dat");
        N = surface.getN();
        //System.out.println("Initializing simulation");
        //System.out.println("There are: " + N + " particles in simulation");
        preProcessor();
    }
    
    private void GAInit( Surface s) {
        TSPInOut tsp = new TSPInOut();
        surface = s;
        N = surface.getN();
        //System.out.println("Initializing simulation");
        //System.out.println("There are: " + N + " particles in simulation");
        //preProcessor();
    }
    
    private void preProcessor() {
        int[] distIndex;
        for(int i = 0; i < N; i++) {
            distIndex = surface.getShortestDistance(i);
            int a = rng.nextInt(3);
            int tmp = distIndex.length+1;
            int i1 = tmp;
            for(int j = 0; j < 3; j++) {
                //System.out.println("Connecting: " + i + " & " + distIndex[j] + " " + surface.getDist(i,distIndex[j]));
                while (i1==tmp){
                    i1 = rng.nextInt(distIndex.length);
                }
                surface.connectUnsafe(i,distIndex[i1]);
                tmp = i1;
            } 
        }
    }
    
}