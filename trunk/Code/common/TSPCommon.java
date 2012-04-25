/**
 *
 * @author zak.estrada
 */
public class TSPCommon {
  public static class InputOutput{
    public surface readData(String filename) {

    }
  }

/*
 * Class to represent the problem we are working on.
 * This is probably not the most ideal thing to use, but it's a start
 */
  public class surface {

    //Array of 2 element double arrays to store our points
    private double[][] points; 
    //Array to count number of connections each site currently has
    private int[] verticies;
    //Number of sites
    private int N;


    /*
     * Base constructor
     */
    public surface(double[] xcoords, double[] ycoords) {
      this.N = xcoords.length;
    }
  }
}
