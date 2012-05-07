public class Ant {
  private Surface surf;
  private Pheromone pheromone;
  private double[][] eta;
  private double alpha, beta;

  public Ant(Surface inputSurf, Pheromone inputPheromone, double alpha, double beta) throws java.lang.CloneNotSupportedException {
    this.alpha=alpha;
    this.beta=beta;
    pheromone = inputPheromone;
    surf = inputSurf.clone(); 
    eta = new double[surf.getN()][surf.getN()];
    for(int i=0; i<surf.getN();i++) {
      for(int j=0; j<surf.getN();j++) {
        eta[i][j] = 1.0/surf.getDistance(i,j);
      }
    }
  }

  //Reset this ant's state
  public void reset() {
    surf.disconnectAll();
  }

  public void setPheromone(Pheromone newPheromone) {
    pheromone = newPheromone;
  }

  public void getPheromone(Pheromone newPheromone) {
    pheromone = newPheromone;
  }

  public double getPheromone(int i, int j) {
    return pheromone.get(i,j);
  }

  public Surface getSurface() throws java.lang.CloneNotSupportedException {
    //FIXME: might not need to clone, get some efficiency
    return surf.clone();
  }

  //Do the main work based on Surface, pheromone
  //leaves surface member object modified
  public double constructSolution() {
    double length=0.0;
    double p;
    boolean done=false;
    while(!done) {
       
    }
    return length;
  }
}
