public class ACOMain {

  private static int numAnts = 8;
  private static Pheromone pheromone;
  private static Ant[] ants;
  private static double[] solutions; //length of solution for each ant

  //Ant System parameters
  private static double alpha;
  private static double beta;
  //This evaporation rate is (1-rho) as is common in the literature
  private static double evap;
  
  //Here, we use the original Ant System

  public static void main(String[] args) throws java.lang.CloneNotSupportedException {
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
    System.out.println("\n\nStarting ACO with "+numAnts+" ants "+" alpha="+alpha+" beta="+beta+" evap="+evap);

    //Initialize pheromone matrix
    pheromone = new Pheromone(surf.getN());

    for(int i=0;i<numAnts;i++) {
      ants[i] = new Ant(surf.clone(),pheromone,alpha,beta);
    }
  }

  private void updatePheromones() {

  }

}
