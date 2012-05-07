public class ACOMain {

  private static int numAnts = 8;
  private static Pheromone pheromone;
  private static Ant[] ants;
  
  //Here, we use the original Ant System

  public static void main(String[] args) throws java.lang.CloneNotSupportedException {
    ants = new Ant[numAnts];
    
    //Input file is first argument
    String infile = args[0];
    TSPInOut inout = new TSPInOut();
    Surface surf = inout.readData(infile);
    System.out.println("\n\nStarting ACO with "+numAnts+" ants");

    //Initialize pheromone matrix
    pheromone = new Pheromone(surf.getN());

    for(int i=0;i<numAnts;i++) {
      ants[i] = new Ant(surf.clone(),pheromone);
    }
  }

  private void updatePheromones() {

  }

}
