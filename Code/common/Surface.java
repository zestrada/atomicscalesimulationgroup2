import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.lang.Math;

/*
 * Class to represent the problem we are working on.
 * This is probably not the most ideal thing to use, but it's a start
 */
public class Surface {

  //Distance matrix
  private double[][] distance; 
  //Array to count number of connections each site currently has
  private int[] vertices;
  //Number of sites, vertices per site
  private int N,maxVertex;
  //Connection matrix
  private boolean[][] connection;
  //Simulation cell
  private Cell cell;


  /*
   * Base constructor - two Lists were chosen over a List of double[2]
   */
  public Surface(ArrayList<Double> xcoords, ArrayList<Double> ycoords, int nvertex, Cell cell) {
    this.N = xcoords.size(); //assums xcoords.length==ycoords.length
    this.cell = cell;
    this.maxVertex = nvertex; 

    //Allocate matrices
    this.vertices = new int[N];
    this.connection = new boolean[N][N];
    this.distance = new double[N][N];

    //Now, compute distance matrix
    //Ideally, we'd use upper triangular storage...
    double[] delta = new double[2];
    for(int i=0; i<N;i++) {
      vertices[i]=0; 
      for(int j=0; j<N; j++) {
        connection[i][j] = false;
        delta[0] = ((Double)xcoords.get(i)).doubleValue() - ((Double)xcoords.get(j)).doubleValue();
        delta[1] = ((Double)ycoords.get(i)).doubleValue() - ((Double)ycoords.get(j)).doubleValue();
        //Minimum image convention
        cell.putInBox(delta);
        distance[i][j] = Math.sqrt(delta[0]*delta[0] + delta[1]*delta[1]);
      }
    }

  }

  public int getMaxVertex() {
    return maxVertex;
  }

  public double getDist(int i, int j) {
    return distance[i][j];
  }

  public double[][] setDistance(int i, int j, double d) {
    distance[i][j] = d;
    distance[j][i] = d;
    return distance;
  }

  public double getEnergy() {
    double energy = 0;
    int countConnections = 0;
    int maxConnectionsPerVertex = 4;
    for(int i=0; i<N; i++) {
      countConnections = 0;
      for(int j=i+1; j<N; j++) {
	      energy += (connection[i][j] ? 1:0)*(distance[i][j]*distance[i][j]);
	      countConnections += connection[i][j] ? 1 : 0;
      }
      if(countConnections > maxConnectionsPerVertex) {
        //This is 2^20.  It is a big number but not so big as to overflow the double buffer.
	      energy += 1024*1024;
      }
    }
      return energy;
  }

  public int getN() {
    return N;
  }

  public void connect(int i, int j) {
    connection[i][j] = true;
    connection[j][i] = true;
    vertices[i]++;    
    vertices[j]++;
    if(vertices[i]>maxVertex || vertices[j]>maxVertex) {
      System.err.println("ERROR: connection between "+i+" and "+j+" violate maxVertex of"+maxVertex);
    }
  }

  public boolean connected(int i, int j) {
    return connection[i][j];
  }

  //Like energy, but just the length component
  public double getTotalLength() {
    double len = 0.0;
    for(int i=0; i<N; i++) {
      for(int j=i+1; j<N; j++) {
          len += (connection[i][j] ? 1:0)*(distance[i][j]);
      }
    }
    return len;
  }

  public void disconnect(int i, int j) {
    connection[i][j] = false;
    connection[j][i] = false;
    vertices[i]--;    
    vertices[j]--;
  }

  public boolean hasMaxVertex(int i) {
    return (vertices[i]==maxVertex);
  }

  public void disconnectAll() {
    for(int i=0; i<N;i++) {
      vertices[i]=0; 
      for(int j=0; j<N; j++) {
        connection[i][j] = false;
      }
    }
  }
}
