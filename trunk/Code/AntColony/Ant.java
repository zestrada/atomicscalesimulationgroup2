public class Ant {
  private Surface surf;
  private double[] pheromone;

  public Ant(Surface inputSurf) throws java.lang.CloneNotSupportedException {
    int N;
    surf = inputSurf.clone(); 
    N = surf.getN();
  }

  //Reset this ant's state
  public void reset() {
    surf.disconnectAll();
  }
}
