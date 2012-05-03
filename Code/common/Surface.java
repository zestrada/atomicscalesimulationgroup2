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
   * Base constructor
   */
  public Surface(ArrayList<Double> xcoords, ArrayList<Double> ycoords, int nvertex, Cell cell) {
    this.N = xcoords.size(); //assums xcoords.length==ycoords.length
    this.cell = cell;
    this.maxVertex = nvertex; 

    //Allocate matrices
    this.vertices = new int[N];
    this.connection = new boolean[N][N];

    //Now, compute distance matrix
    for(int i=0; i<N;i++) {
      vertices[i]=0; 
      for(int j=0; j<N; j++) {
        connection[i][j] = false;
      }
    }

  }

  public int getMaxVertex() {
    return maxVertex;
  }

}
