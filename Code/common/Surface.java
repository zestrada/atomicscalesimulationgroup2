import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

/*
 * Class to represent the problem we are working on.
 * This is probably not the most ideal thing to use, but it's a start
 */
public class Surface implements Cloneable {

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
  //Random number generator
  private Random rng;

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

  public double getDistance(int i, int j) {
    return getDist(i,j);
  }

  public double[][] setDistance(int i, int j, double d) {
    distance[i][j] = d;
    distance[j][i] = d;
    return distance;
  }

  public double getEnergy() {
    double energy = 0;
    int countConnections = 0;
    for(int i=0; i<N; i++) {
	countConnections = 0;
      for(int j=i+1; j<N; j++) {
	      energy += (connection[i][j] ? 1:1024)*(distance[i][j]*distance[i][j]);
	      countConnections += connection[i][j] ? 1 : 0;
      }
      if(countConnections != maxVertex) {
        //This is 2^10.  It is a big number but not so big as to overflow the double buffer.
     energy += 1024*Math.abs(countConnections - maxVertex);
	  }
    }
      return energy;
  }

  public void minBind(int x) {
      double maxDistance = -1;
      int indexMax = -1;
      for(int i = 0; i < N; i++) {
	  if(connection[x][i] && distance[x][i] > maxDistance) {
	      indexMax = i;
	      maxDistance = distance[x][i];
	  }
      }
      if(indexMax >= 0) {
	  disconnect(x,indexMax);
      }
      return;
  }

    public int maxVertex() {
	int maxV = -1;
	for(int i = 0; i < N; i++) {
	    if(maxV < vertices[i]) {
		maxV = vertices[i];
	    }
	}
	return maxV;
    }

  public boolean flipConnection(int x, int y) {
      return (connection[x][y] = !connection[x][y]);
  }
    
  public void swapConnection(int x1, int y1, int x2, int y2) {
	boolean tmp1 = connection[x1][y1];
	boolean tmp2 = connection[x2][y2];
	connection[x1][y1] = tmp2;
	connection[y1][x1] = tmp2;
	connection[x2][y2] = tmp1;
	connection[y2][x2] = tmp1;
	}

  public int getN() {
    return N;
  }

  public void connect(int i, int j) {
    if(i==j) return; //noop
    if(connection[i][j] != connection[j][i]) {
      error("connection "+i+","+j+" = "+connection[i][j]+"while connection "+j+","+i+" = "+connection[j][i]);
    }
    if(connection[i][j]==false) {
      connection[i][j] = true;
      connection[j][i] = true;
      vertices[i]++;    
      vertices[j]++;
      if(vertices[i]>maxVertex || vertices[j]>maxVertex) {
        error("connection between "+i+" and "+j+" violate maxVertex of"+maxVertex);
      }
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
    if(i==j) return;//noop
    if(connection[i][j] != connection[j][i]) {
      error("connection "+i+","+j+" = "+connection[i][j]+"while connection "+j+","+i+" = "+connection[j][i]);
    }
    if(connection[i][j]==true) {
      connection[i][j] = false;
      connection[j][i] = false;
      vertices[i]--;    
      vertices[j]--;
    }
  }

  public boolean hasMaxVertex(int i) {
    return (vertices[i]==maxVertex);
  }

  public boolean hasMoreMaxVertex(int i) {
      return (vertices[i]>maxVertex);
  }

  public void disconnectAll() {
    for(int i=0; i<N;i++) {
      vertices[i]=0; 
      for(int j=0; j<N; j++) {
        disconnect(i,j);
      }
    }
  }

  //Returns a list of sites NOT fully connected
  public int[] getFreeList() {
    //Not very efficient, but faster might be faster than using an ArrayList
    //and converting later. Also avoids needing to sort a stored list
    int[] freeList;
    int freeCount = 0, counter = 0;
    for(int i=0;i<N;i++)
      if(!hasMaxVertex(i)) freeCount++;
    
    freeList = new int[freeCount];
    counter=0;
    for(int i=0;i<N;i++) {
        if(!hasMaxVertex(i)) freeList[counter++]=i;
      }

      return freeList;
    }

    public Surface clone() throws java.lang.CloneNotSupportedException {
      return (Surface) super.clone();
    }

    public boolean[][] getConnection() {
      boolean[][] connectCopy = new boolean[N][N];
      for(int i=0; i<N; i++) {
        for(int j=0; j<N; j++) {
          connectCopy[i][j]=connection[i][j];
        }
      }
      return connectCopy;
    }

    public void error(String message) {
      System.err.println("ERROR "+message);
      System.exit(1);
    }
}
