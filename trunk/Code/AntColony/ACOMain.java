public class ACOMain {

  private static int numAnts = 32;
  private static int numThreads = 8;
  private static Pheromone pheromone;
  private static Ant[] ants;
  private static double[] solutions; //energy of solution for each ant
  private static int numSteps=100000; //number of steps to run ACO for
  private static int bestAnt=0; //best ant
  private static double bestSeen=Double.MAX_VALUE,lastBest=Double.MAX_VALUE; //best energy we've seen
  //dynmically adjust the pheromone update factor
  private static boolean dynamicQ = true; 

  //Ant System parameters
  private static double alpha; //pheromone^alpha
  private static double beta; //(1/distance)^beta
  private static double Q=Double.MAX_VALUE;
  private static final int stepsPerUpdate=10;
  //This evaporation rate is (1-rho) as is common in the literature
  private static double evap;
  private static double initPher=1.0; //initial value for pheromone matrix

  private static final boolean useEnergy = true;
  private static final boolean animate = true; //write trajectories at each step
  
  //convergence test
  private static int convCounter;
  //number of steps with equal energy before we declare a converged solution
  private static final int convRequired=100;
  
  //Here, we use the original Ant System

  public static void main(String[] args) throws java.lang.CloneNotSupportedException {
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
    System.out.println("\n\nStarting ACO with "+numAnts+" ants "+numThreads+" threads  alpha="+alpha+" beta="+beta+" evap="+evap+" dynQ: "+dynamicQ);

    //Initialize pheromone matrix
    pheromone = new Pheromone(surf.getN(),initPher);
    scaledPreProcessor(surf); //Bias pheromone matrix to short distances 
    System.out.println("Using Scaled Preprocessor");
    for(int i=0;i<numAnts;i++) {
      ants[i] = new Ant(new Surface(surf),pheromone,alpha,beta);
    }
   
    ///////////////
    //Main ACO LOOP
    ///////////////
    inout.timerStart();
    convCounter=0;
    for(int i=0;i<numSteps;i++) {
      if(convCounter==convRequired) {
        System.out.println("Energy constant for "+convRequired+" iterations ... solution converged");
        break;
      }

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
      
      if(bestsolution<bestSeen) {
        bestSeen=bestsolution;
        if(dynamicQ)
          Q=bestSeen;//refactor based on bestsolution
      }

      //Check for convergence
      if(bestsolution==lastBest)
        convCounter++;
      else
        convCounter=0;
      lastBest=bestsolution;
      
      //Initialize to a sensible parameter - best first ant
      if(Q==Double.MAX_VALUE)
        Q=bestSeen;

      updatePheromonesBest();

      System.out.println("Step "+(i+1)+"/"+numSteps+" best energy "+bestsolution+" ant "+bestAnt+" missing vert "+ants[bestAnt].getMissingVertices());
      inout.recordEnergy(bestsolution);
      ants[bestAnt].getSurface().writeConnection_name("./trajectory/step"+i);
    }
    long time = inout.timerStop();
    System.out.println("Run finished in "+time+" ms");
    //pheromone.printPheromoneMatrix();
    System.out.println("Writing output...");
    ants[bestAnt].finalOutput();
    inout.outputEnergy();
    System.out.println("ACO Done ... overall best energy "+bestSeen);
  }

  /*
   * Written as a separate function so it would be swapped out with
   * This is regular 'ol Ant System
   */
  private static void updatePheromonesAS() {
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

  /*
   * Only the best ant updates, all others evaporate - kinda like Max-Min
   */
  private static void updatePheromonesBest() {
    double temp;
    for(int i=0; i<pheromone.getN();i++) {
      for(int j=i+1; j<pheromone.getN(); j++) {
        temp = evap*pheromone.get(i,j);
        if( ((Surface) ants[bestAnt].getSurface()).connected(i,j) )
          temp+=Q/solutions[bestAnt]; 
        pheromone.set(i,j,temp);
      }
    }
  }

  private static void updatePheromonesMaxMin() {
    double max,min;
    double temp;
    max=bestSeen*4;
    min=initPher;
    for(int i=0; i<pheromone.getN();i++) {
      for(int j=i+1; j<pheromone.getN(); j++) {
        temp = evap*pheromone.get(i,j);
        if( ((Surface) ants[bestAnt].getSurface()).connected(i,j) )
          temp+=Q/solutions[bestAnt]; 
        if(temp>max) {
          pheromone.set(i,j,max);
        } else if(temp<min) {
          pheromone.set(i,j,min);
        } else {
          pheromone.set(i,j,temp);
        }
      }
    }
  }




  private static void preProcessor(Surface surf) {
    int[] distIndex;
    int maxVertex = surf.missingVertex(0); //assume unconnected surface
    for(int i = 0; i < surf.getN(); i++) {
        distIndex = surf.getShortestDistance(i);
        for(int j = 0; j < maxVertex; j++) {
            pheromone.incr(i,distIndex[j],initPher*10);
        } 
    }
  }

 private static void scaledPreProcessor(Surface surf) {
    int[] distIndex;
    int maxVertex = surf.getMaxVertex(); //assume unconnected surface
    for(int i = 0; i < surf.getN(); i++) {
        distIndex = surf.getShortestDistance(i);
        for(int j = 0; j < maxVertex*2; j++) {
            double scale;
            if(j<maxVertex)
              scale = 1.0;
            else
              scale = 0.5;
            pheromone.incr(i,distIndex[j],initPher*(10.0)*scale);
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
      super("ant"+tid);
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
