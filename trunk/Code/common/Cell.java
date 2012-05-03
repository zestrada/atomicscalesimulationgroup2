import java.lang.Math;
/*
 * Class to handle PBC and the like
 */
public class Cell {

  //Boundaries
  private double xmin,xmax;
  private double ymin,ymax;
  private double[] L;

  public Cell(double xmin, double xmax, double ymin, double ymax) {
    this.xmin = xmin;
    this.xmax = xmax;
    this.ymin = ymin;
    this.ymax = ymax;
    
    L = new double[2];
    L[0] = xmax - xmin;
    L[1] = ymax - ymin;
    System.out.println("Created simulation cell "+L[0]+" by "+L[1]);
  }

  public void putInBox(double[] coords) {
    coords[0] = coords[0]-L[0]*((double)Math.round(coords[0]/L[0]));
    coords[1] = coords[1]-L[1]*((double)Math.round(coords[1]/L[1]));
  }
}
