public class Ant {
  private Surface surf;
  private double[] pheromone;

  public Ant(Surface inputSurf, double[] inputPheromone) throws java.lang.CloneNotSupportedException {
    surf = inputSurf.clone(); 
    pheromone = inputPheromone;
  }

  //Reset this ant's state
  public void reset() {
    surf.disconnectAll();
  }

  public void setPheromone(double[] newPheromone) {
    pheromone = newPheromone;
  }

  //Do the main work based on Surface, pheromone
  public void constructSolution() {

  }
}
