import java.util.Arrays;
public class Pheromone {
  /*
   * Class to hold pheromone matrix. Simple matrix for now, could modify to use
   * upper triangular storage for larger systems
   */
  private double[][] pMatrix;
  private int N;

  public Pheromone(int size, double initialValue) {
    N=size;
    pMatrix = new double[N][N];
    for(int i=0; i<N; i++) {
      for(int j=0; j<N; j++) {
        pMatrix[i][j]=initialValue;
      }
    }
  }

  public double get(int i, int j) {
    if(pMatrix[i][j]!=pMatrix[j][i]) 
      error("pMatrix non-symmetric!!!");
    return pMatrix[i][j];
  }

  public void set(int i, int j, double value) {
    pMatrix[i][j] = value;
    pMatrix[j][i] = value;
  }

  public void incr(int i, int j, double value) {
    if(pMatrix[i][j]!=pMatrix[j][i]) 
      error("pMatrix non-symmetric!!!");
    pMatrix[i][j] += value;
    pMatrix[j][i] += value;
  }

  public void decr(int i, int j, double value) {
    if(pMatrix[i][j]!=pMatrix[j][i]) 
      error("pMatrix non-symmetric!!!");
    pMatrix[i][j] -= value;
    pMatrix[j][i] -= value;
  }

  public void error(String message) {
    System.err.println("ERROR "+message);
    System.exit(1);
  }

  public int getN() {
    return N;
  }
}
