public class ACOMain {

  private static int numAnts = 16;
  private static int numThreads = numAnts;
  private static Pheromone pheromone;
  private static Ant[] ants;
  private static double[] solutions; //energy of solution for each ant
  private static int numSteps=200; //number of steps to run ACO for

  //Ant System parameters
  private static double alpha;
  private static double beta;
  private static double Q=100.0;
  private static final int stepsPerUpdate=10;
  //This evaporation rate is (1-rho) as is common in the literature
  private static double evap;
  private static double initPher=1.0; //initial value for pheromone matrix

  private static final boolean useEnergy = true;
  
  //Here, we use the original Ant System

  public static void main(String[] args) throws java.lang.CloneNotSupportedException {
    int bestAnt=0;
    AntThread[] threads = new AntThread[numAnts];
    ants = new Ant[numAnts];
    solutions = new double[numAnts];
    

    //Poor man's arg parsing:
    //Input file is first argument, alpha and beta are 2nd and 3rd, respectively
    String infile = args[0];
    if(args.length>3) {
      alpha = Double.parseDouble(args[1]);
      beta = Double.parseDouble(args[2]);
      evap = Double.parseDouble(args[3]);
    } else {
      alpha = 1.0;
      beta = 1.0;
      evap= 0.90;
    }
    TSPInOut inout = new TSPInOut();
    Surface surf = inout.readData(infile);
    System.out.println("\n\nStarting ACO with "+numAnts+" ants "+numThreads+" threads  alpha="+alpha+" beta="+beta+" evap="+evap);

    //Initialize pheromone matrix
    pheromone = new Pheromone(surf.getN(),initPher);
    for(int i=0;i<numAnts;i++) {
      ants[i] = new Ant(new Surface(surf),pheromone,alpha,beta);
    }

    for(int i=0;i<numSteps;i++) {
      //One thread per ant
      for(int t=0;t<numThreads;t++) {
        threads[t] = new AntThread(ants,t,solutions); 
        threads[t].start();
      }

      //Since we can't have an actual pointer to double in java
      for(int t=0;t<numThreads;t++) {
        try{
          threads[t].join();
        } catch (Exception e) {
          e.printStackTrace();
          System.exit(1);
        }
      }

      //Find minimum tour
      double bestsolution=Double.MAX_VALUE;
      for(int a=0;a<numAnts;a++) {
        if(solutions[a]<bestsolution) {
          bestAnt=a;
          bestsolution=solutions[a];
        }
      }

      updatePheromones();

      System.out.println("Step "+(i+1)+"/"+numSteps+" best energy "+bestsolution+" ant "+bestAnt+" missing vert "+ants[bestAnt].getMissingVertices());
    }

    System.out.println("Writing output...");
    ants[bestAnt].finalOutput();
    System.out.println("ACO Done");
  }

  /*
   * Written as a separate function so it would be swapped out with
   * This is regular 'ol Ant System
   */
  private static void updatePheromones() {
    double temp;
    for(int i=0; i<pheromone.getN();i++) {
      for(int j=i+1; j<pheromone.getN(); j++) {
        temp = evap*pheromone.get(i,j);
        for(int a=0;a<numAnts;a++) {
          if( ((Surface) ants[a].getSurface()).connected(i,j) )
            temp+=Q/solutions[a]; 
        }
        pheromone.set(i,j,temp);
      }
    }
  }

  //The only thread-safe procedure is construct solution since the pheromone
  //matrix is shared
  static class AntThread extends Thread {
    Ant[] ant;
    int tid;
    double[] solutions;
    public AntThread(Ant[] ants, int tid, double[] solutions) {
      this.ant = ant;
      this.tid = tid;
      this.solutions = solutions;
    }

    //This assumes we always use perfect multiples of ants and threads
    public void run() {
      int nPerThread=(numAnts/numThreads);
      int start=(tid)*(nPerThread);
      int end=start+nPerThread;
      for(int i=start;i<end;i++) {
        ants[i].reset();
        solutions[i] = ants[i].constructSolution();
        if(useEnergy)
          solutions[i] = ants[i].getEnergy();
      }
    }
  }

}
