public class Ant {
  private Surface surf;
  private Pheromone pheromone;
  private double[][] eta;

  public Ant(Surface inputSurf, Pheromone inputPheromone) throws java.lang.CloneNotSupportedException {
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

  //Do the main work based on Surface, pheromone
  public double constructSolution() {
    double length=0;
  }
}
