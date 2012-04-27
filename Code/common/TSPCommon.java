/**
 *
 * @author zak.estrada
 */
public class TSPCommon {
  public static class InputOutput{
    public surface readData(String filename) {
      //Need to read in coordinates from file and create a cell
      surface mySurf = new surface(xcoords, ycoords, cell);
    }
  }

/*
 * Class to represent the problem we are working on.
 * This is probably not the most ideal thing to use, but it's a start
 */
  public class surface {

    //Distance matrix
    private double[][] distance; 
    //Array to count number of connections each site currently has
    private int[] vertices;
    //Number of sites, vertices per site
    private int N,maxVertex;
    //Connection matrix
    private boolean[][] connection;
    //Simulation cell
    private cell cell;


    /*
     * Base constructor
     */
    public surface(double[] xcoords, double[] ycoords, int nvertex, cell cell) {
      this.N = xcoords.length; //assums xcoords.length==ycoords.length
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
  }

  public class cell {

    //Boundaries
    private double xmin,xmax;
    private double ymin,ymax;

    public cell(double xmin, double xmax, double ymin, double ymax) {
      this.xmin = xmin;
      this.xmax = xmax;
      this.ymin = ymin;
      this.ymax = ymax;
    }
  }
}
