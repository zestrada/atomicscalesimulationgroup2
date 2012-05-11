import java.util.*;
import java.*;
import java.io.*;
import java.lang.Math;
public class GA {
  
    private static Surface surface;
    private static int Pop_n = 32;
    private static GApopulation[] g = new GApopulation[Pop_n];
    private static double[] energyArray = new double[Pop_n];
    private static double energy = 0.0;
    private static Random rng = new Random();
    private static int N = 0;
    private static int MaxIndex = 0;
    private static int MinIndex = 0;
    

    public void run(int step, int output) {
	
    //initialize population
        for(int i = 0; i < Pop_n; i++) {
            g[i] = new GApopulation(i);
            writeSurface(i);
            energyArray[i] = g[i].getEnergy();
        }
        
        System.out.println("Initialized Population");
        
        N = g[0].getN();
        int tmpStep = step/output;
        int numSteps = 0;
        
        
        //run loop
        for(int k = 0; k < tmpStep; k++) {
            energy = 0;
            for(int j = 0; j < output; j++) {
                int[] parents = new int[2];

                //calculate system energies
                for(int i = 0; i < Pop_n; i++) {
                    energyArray[i] = g[i].getEnergy();
                    System.out.println("Index: " + i + "\tEnergy: " + energyArray[i] );
                    //System.out.println("g: " + g[i] );
                }
                
                //select parents and run one reproduction cycle
                parents = SelectParents(energyArray);
                GApopulation[] child =Crossover(parents);
                System.out.println("Child 0 Energy: " + child[0].getEnergy() );
                System.out.println("Child 1 Energy: " + child[1].getEnergy() );
                double mut_prob = 0.1;
                for(int l = 0; l<Pop_n; l++){
                    double b = rng.nextDouble();
                    //select for mutation with probability 0.05
                    if(b<mut_prob){
                        System.out.println("Mutating: " +l);
                        Mutate(l);
                    }
                }
                
                // replace least fit parents with children
                double maxE = maxEnergy();
                if(maxE>child[0].getEnergy()){
                    g[MaxIndex] = child[0];
                    energyArray[MaxIndex] = child[0].getEnergy();
                    System.out.println("Child 1 replaces: " + MaxIndex);
                }
                maxE = maxEnergy();
                if(maxE>child[1].getEnergy()){
                    g[MaxIndex] = child[1];
                    energyArray[MaxIndex] = child[1].getEnergy();
                    System.out.println("Child 2 replaces: " + MaxIndex);
                }
                for(int i = 0; i < Pop_n; i++) {
                    energyArray[i] = g[i].getEnergy();
                    System.out.println("Index: " + i + "\tEnergy: " + energyArray[i] );
                }
            
            }
            numSteps += (output);
            double minE = minEnergy();
            System.out.println("Step: " + numSteps + "\tEnergy: " + minE + "\tIndex: " + MinIndex);
        }
	    
	}
	
    public void run_noprint(int step, int output) {
        
        //initialize population
        for(int i = 0; i < Pop_n; i++) {
            g[i] = new GApopulation(i);
            energyArray[i] = g[i].getEnergy();
        }
        
        System.out.println("Initialized Population");
        
        N = g[0].getN();
        int tmpStep = step/output;
        int numSteps = 0;
        
        
        //run loop
        energy = 0;
        for(int k = 0; k < tmpStep; k++) {

            for(int j = 0; j < output; j++) {
                int[] parents = new int[2];
                
                //calculate system energies
                for(int i = 0; i < Pop_n; i++) {
                    energyArray[i] = g[i].getEnergy();
                    //System.out.println("Index: " + i + "\tEnergy: " + energyArray[i] );
                    //System.out.println("g: " + g[i] );
                }
                
                //select parents and run one reproduction cycle
                parents = SelectParents(energyArray);
                GApopulation[] child =Crossover(parents);
                //System.out.println("Child 0 Energy: " + child[0].getEnergy() );
                //System.out.println("Child 1 Energy: " + child[1].getEnergy() );
                double mut_prob = 0.15;
                for(int l = 0; l<Pop_n; l++){
                    double b = rng.nextDouble();
                    //select for mutation with probability 0.05
                    if(b<mut_prob){
                        //System.out.println("Mutating: " +l);
                        Mutate(l);
                    }
                }
                
                // replace least fit parents with children
                double maxE = maxEnergy();
                if(maxE>child[0].getEnergy()){
                    g[MaxIndex] = child[0];
                    energyArray[MaxIndex] = child[0].getEnergy();
                    //System.out.println("Child 1 replaces: " + MaxIndex);
                }
                maxE = maxEnergy();
                if(maxE>child[1].getEnergy()){
                    g[MaxIndex] = child[1];
                    energyArray[MaxIndex] = child[1].getEnergy();
                    //System.out.println("Child 2 replaces: " + MaxIndex);
                }
                for(int i = 0; i < Pop_n; i++) {
                    energyArray[i] = g[i].getEnergy();
                    //System.out.println("Index: " + i + "\tEnergy: " + energyArray[i] );
                }
            
                numSteps++;
                if((numSteps%100) == 0){
                    
                    double minE = minEnergy();
                    System.out.println("Step: " + numSteps + "\tEnergy: " + minE + "\tIndex: " + MinIndex);
                }
            }
            double minE = minEnergy();        
            //System.out.println("Old Energy:" + energy + "New Energy:" + minE);
            if(Math.abs(minE - energy)<0.01){
                System.out.println("Convergence Achieved in : " + numSteps + " steps");
                break;
            }
            else{energy = minE;}
        }
	    
	}

    public double minEnergy() {
        double MinE = energyArray[0];
        for(int i = 1; i < Pop_n; i++) {
            if(MinE > energyArray[i]) {
                MinE = energyArray[i];
                MinIndex = i;
            }
        }
        return MinE;
    }
    
    public double maxEnergy() {
        double MaxE = -1.0;
        for(int i = 0; i < Pop_n; i++) {
            if(MaxE < energyArray[i]) {
                MaxE = energyArray[i];
                MaxIndex = i;
            }
        }
        return MaxE;
    }
    
    private int[] SelectParents(double[] energyArray) {
        int[] parents = new int[2];
        double total = 0;
        double[] prob = new double[Pop_n];
        for(int k = 0; k < Pop_n; k++) {
            total = total + (1/energyArray[k]);
        }
        for(int k = 0; k < Pop_n; k++) {
            prob[k] = (1/energyArray[k])/total;
        }
        
        //select first parent
        double a = rng.nextDouble();
        double i = 0;
        int j = 0;
        while(i < a) {
            i = i+prob[j];
            j++;
        } 
        parents[0] = j-1;
        
        //select second parent
        a = rng.nextDouble();
        i = 0;
        j = 0;
        while(i < a) {
            i = i+prob[j];
            j++;
        } 
        parents[1] = j-1;
        return parents;
    }
    
    
    private GApopulation[] Crossover(int[] parents) {
        //initialize children
        GApopulation[] child = new GApopulation[2];
        for(int i = 0; i < 2; i++) {
            child[i] = new GApopulation(i);
        }
        
        Surface s1 = g[parents[0]].getSurface();
        Surface s2 = g[parents[1]].getSurface();
        
        //select a column X to perform crossover
        int i1 = rng.nextInt(N);

        //interchange connections between parents
        for(int i=0;i<N;i++){
            boolean tmp = s1.connected(i,i1);
            boolean tmp1 = s2.connected(i,i1);
                
            if(s2.connected(i,i1)) {s1.connectUnsafe(i,i1);}else{s1.disconnect(i,i1);}
            if(tmp) {s2.connectUnsafe(i,i1);}else{s2.disconnect(i,i1);}
            if(s1.hasMoreMaxVertex(i)){
                //reduce farthest connection of i
                double dist = 0.0;
                int index = 0;
                int j = 0;
                while(j < N){
                    if(s1.connected(i,j)&& s1.getDistance(i,j)>dist){
                        dist = s1.getDistance(i,j);
                        index = j;
                    }
                    j++;
                }
                //System.out.print("DisConnecting: "+index+"\n");
                s1.disconnect(index,i);
            }
            if(s2.hasMoreMaxVertex(i)){
                //reduce farthest connection of i
                double dist = 0.0;
                int index = 0;
                int j = 0;
                while(j < N){
                    if(s1.connected(i,j)&& s1.getDistance(i,j)>dist){
                        dist = s1.getDistance(i,j);
                        index = j;
                    }
                    j++;
                }
                //System.out.print("DisConnecting: "+index+"\n");
                s1.disconnect(index,i);

            }
        }
        child[0].setSurface(s1);
        child[1].setSurface(s2);
        return child;
    }
    
    private void Mutate(int l) {
                int j1 = rng.nextInt(N);
                int j2 = rng.nextInt(N);
                if(j1>j2){
                    int tmpj = j1;
                    j1 = j2;
                    j2 = tmpj;
                }
                Surface s = g[l].getSurface();
                double tmp1 = g[l].getEnergy();
                //make a connection between i and one of it's nearest neighbour
                for(int i = j1; i < j2; i++) {
                    int [] distIndex = s.getShortestDistance(i);
                    int a = rng.nextInt(distIndex.length/2);
                    /*double dist = 500.0;
                    int a = 0;
                    int j = 0;
                    while(j < N){
                        if(s.getDistance(i,j)<dist && s.getDistance(i,j)>0){
                            dist = s.getDistance(i,j);
                            a = j;
                        }
                        j++;
                    }*/
                    double tmp = s.getEnergy();
                    //System.out.print("Energy Before: "+tmp+"\n");
                    s.connectUnsafe(distIndex[a],i);
                    //s.connectUnsafe(a,i);
                    if(s.hasMoreMaxVertex(i)){
                        //reduce farthest connection of i
                        double dist = 0.0;
                        int index = 0;
                        int j = 0;
                        while(j < N){
                            if(s.connected(i,j)&& s.getDistance(i,j)>dist){
                                dist = s.getDistance(i,j);
                                index = j;
                            }
                            j++;
                        }
                        //System.out.print("DisConnecting: "+index+"\n");
                        s.disconnect(index,i);
                        //System.out.print("Energy After: "+s.getEnergy()+"\n");
                        if(s.getEnergy()>tmp){
                            s.disconnect(distIndex[a],i);
                            s.connectUnsafe(index,i);
                            //System.out.print("Disconnected");
                        }
                    }
                }
        //System.out.print("Energy at Before set: "+g[l].getEnergy()+"\tand:"+g[0].getEnergy()+"\n");
        g[l].setSurface(s);
        //System.out.print("Energy at After set: "+g[l].getEnergy()+"\tand:"+g[0].getEnergy()+"\n");
        //System.out.print("Mutation: Initial Energy"+ tmp1 + "Final Energy" +g[l].getEnergy()+"\n");
        }
    
    public void finalOutput() {
        Surface surface = g[MinIndex].getSurface();
        String traj = "Random_4.xyz";
        String conn = "Random_connection_4.dat";
        surface.writeTrajectory_name(traj);
        surface.writeConnection_name(conn);
        System.out.print("Final Energy: " + surface.getEnergy() + "\n");
        System.out.print("MaxVertex: " + surface.maxVertex() + "\tMinVertex: " + surface.minVertex() +"\n");
        System.out.print("DONE\n");
    }
    
    public void writeSurface(int i) {
        Surface surface = g[i].getSurface();
        String traj = "HoneycombInputSurface" + i;
        String conn = "HoneycombInputConn" + i;
        surface.writeTrajectory_name(traj);
        surface.writeConnection_name(conn);
        System.out.println("Surface Saved\n");
    }
}
