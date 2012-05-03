import org.junit.*;
import junit.framework.*;
import java.util.ArrayList;
import java.lang.Math;

public class TestCommon extends TestCase {
    public void testReadData() {
      //Test Reading of data
      String filename = "test.input";
      TSPInOut inout = new TSPInOut();
      Surface mySurface = inout.readData(filename);
      assertEquals(3,mySurface.getMaxVertex());
    }

    public void testCell() {
      //Test cell dimensions
      Cell cell = new Cell(-0.5,0.5,-1.5,1.5);
      assertEquals(1.0,cell.getL(0));
      assertEquals(3.0,cell.getL(1));

      //Test pbc
      double[] testCoords = new double[2];
      cell = new Cell(-0.5,0.5,-0.5,0.5);

      testCoords[0]=0;
      testCoords[1]=0;
      cell.putInBox(testCoords);
      assertEquals(0.0,testCoords[0]);
      assertEquals(0.0,testCoords[1]);


      testCoords[0]=0.501;
      testCoords[1]=0;
      cell.putInBox(testCoords);
      assertEquals(0.501-1,testCoords[0]);
      assertEquals(0.0,testCoords[1]);

      testCoords[0]=0;
      testCoords[1]=0.501;
      cell.putInBox(testCoords);
      assertEquals(0.0,testCoords[0]);
      assertEquals(0.501-1.0,testCoords[1]);

      testCoords[0]=2.0;
      testCoords[1]=-0.501;
      cell.putInBox(testCoords);
      assertEquals(0.0,testCoords[0]);
      assertEquals(1.0-0.501,testCoords[1]);
    }

    public void testSurface() {

      //Test distance matrix
      Cell cell = new Cell(-0.5,0.5,-0.5,0.5);
      ArrayList<Double> xcoords = new ArrayList<Double>();
      ArrayList<Double> ycoords = new ArrayList<Double>();
      //particle 0
      xcoords.add(-0.25);
      ycoords.add(0.25);
      //particle 1
      xcoords.add(0.25);
      ycoords.add(0.25);
      //particle 2
      xcoords.add(0.25);
      ycoords.add(-0.25);
      //particle 3
      xcoords.add(-0.25);
      ycoords.add(-0.25);
      //particle 4
      xcoords.add(-0.0625);
      ycoords.add(0.0);
      //particle 5
      xcoords.add(0.0625);
      ycoords.add(0.0);
      //particle 6
      xcoords.add(0.0);
      ycoords.add(-0.0625);
      //particle 7
      xcoords.add(0.0);
      ycoords.add(0.0625);
      //particle 8
      xcoords.add(0.0625);
      ycoords.add(0.0625);
      //particle 9
      xcoords.add(-0.0625);
      ycoords.add(-0.0625);


      Surface surf = new Surface(xcoords, ycoords, 1, cell);
      assertEquals(10, surf.getN());
      assertEquals(0.0, surf.getDist(0,0));
      assertEquals(0.0, surf.getDist(3,3));
      assertEquals(0.5, surf.getDist(0,1));
      assertEquals(0.5, surf.getDist(1,0));
      assertEquals(0.5, surf.getDist(0,3));
      //The joys of testing non-exact FP numbers
      assertEquals(Math.round(100000*1.0/Math.sqrt(2.0)), Math.round(100000*surf.getDist(1,3)));
      assertEquals(0.125, surf.getDist(4,5));
      assertEquals(0.125, surf.getDist(6,7));
      assertEquals(0.125, surf.getDist(7,6));
      assertEquals(Math.round(100000*1.0/(4.0*Math.sqrt(2.0))), Math.round(100000*surf.getDist(8,9)));
      assertEquals(Math.round(100000*1.0/(4.0*Math.sqrt(2.0))), Math.round(100000*surf.getDist(9,8)));
    }
}
