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

    public void testSurface() throws CloneNotSupportedException {

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


      Surface surf = new Surface(xcoords, ycoords, 3, cell);
      assertEquals(10, surf.getN());
      //assertEquals(0.0, surf.getDist(0,0));
      //assertEquals(0.0, surf.getDist(3,3));
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

      //Test connections
      surf.connect(1,2);
      assertEquals(true, surf.connected(1,2));
      assertEquals(true, surf.connected(2,1));
      assertEquals(false, surf.connected(4,1));
      surf.connect(1,4);
      surf.disconnect(1,2);
      assertEquals(false, surf.connected(1,2));
      assertEquals(false, surf.connected(2,1));
      assertEquals(true, surf.connected(4,1));
      assertEquals(false, surf.hasMaxVertex(2));
      surf.disconnectAll();
      surf.connect(5,4);
      surf.connect(5,6);
      surf.connect(5,3);
      assertEquals(true, surf.hasMaxVertex(5));
      assertEquals(false, surf.hasMaxVertex(4));
      assertEquals(false, surf.hasMaxVertex(6));
      surf.disconnect(5,3);
      assertEquals(false, surf.hasMaxVertex(5));
      surf.connect(5,3);
      assertEquals(true, surf.hasMaxVertex(5));
      surf.disconnectAll();
      assertEquals(0.0,surf.getTotalLength());
      surf.connect(0,1);
      assertEquals(0.5,surf.getTotalLength());
      surf.connect(0,3);
      assertEquals(1.0,surf.getTotalLength());
      surf.connect(4,5);
      assertEquals(1.125,surf.getTotalLength());
      surf.disconnectAll();
      assertEquals(0.0,surf.getTotalLength());

      //Test free list
      surf.disconnectAll();
      int[] checkList = new int[xcoords.size()];
      for( int i=0; i<checkList.length;i++)
        checkList[i]=i;
      int[] testList = surf.getFreeList();
      assertEquals(checkList.length,testList.length);
      for(int i=0; i<testList.length;i++)
        assertEquals(checkList[i],testList[i]);

      surf.connect(9,8);
      surf.connect(9,7);
      surf.connect(9,6);
      //Particle 9 should no longer be in the free list
      checkList = new int[xcoords.size()-1];
      for( int i=0; i<checkList.length;i++)
        checkList[i]=i;
      testList = surf.getFreeList();
      assertEquals(checkList.length,testList.length);
      for(int i=0; i<testList.length;i++)
        assertEquals(checkList[i],testList[i]);
      //Particle 9 should be back in the free list
      surf.disconnect(8,9);
      checkList = new int[xcoords.size()];
      for( int i=0; i<checkList.length;i++)
        checkList[i]=i;
      testList = surf.getFreeList();
      assertEquals(checkList.length,testList.length);
      for(int i=0; i<testList.length;i++)
        assertEquals(checkList[i],testList[i]);


      surf.disconnectAll();
      boolean[][]connCopy = surf.getConnection();
      assertEquals(false,connCopy[0][1]);
      surf.connect(0,1);
      connCopy = surf.getConnection();
      assertEquals(true,connCopy[0][1]);
      assertEquals(true,connCopy[1][0]);
      surf.disconnect(0,1);
      assertEquals(true,connCopy[0][1]);
      assertEquals(true,connCopy[1][0]);
      connCopy = surf.getConnection();
      assertEquals(false,connCopy[0][1]);
      assertEquals(false,connCopy[1][0]);

      //Test copy constructor
      surf.disconnectAll();
      Surface clone = new Surface(surf);
      clone.connect(0,1);
      assertEquals(2,clone.missingVertex(0));
      assertEquals(3,surf.missingVertex(0));
      assertEquals(true,clone.connected(0,1));
      assertEquals(false,surf.connected(0,1));
      clone.connect(surf.getN()-1,3);
      assertEquals(true,clone.connected(surf.getN()-1,3));
      assertEquals(false,surf.connected(surf.getN()-1,3));
      assertEquals(3,surf.missingVertex(3));
      assertEquals(clone.getDist(5,4),surf.getDist(5,4));
      assertEquals(clone.getDist(0,surf.getN()-1),surf.getDist(surf.getN()-1,0));
      assertEquals(false,surf.hasMaxVertex(0));
      assertEquals(false,clone.hasMaxVertex(0));
      clone.connect(0,2);
      assertEquals(1,clone.missingVertex(0));
      clone.connect(0,3);
      assertEquals(true,clone.hasMaxVertex(0));
      assertEquals(false,surf.hasMaxVertex(0));
      assertEquals(3,surf.missingVertex(0));


      //Test freeList and vertices with smaller surface
      xcoords = new ArrayList<Double>();
      ycoords = new ArrayList<Double>();
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
      surf = new Surface(xcoords, ycoords, 3, cell);
      assertEquals(12,surf.missingVertices());
      assertEquals(4,surf.getFreeList().length);
      surf.connect(0,1);
      assertEquals(10,surf.missingVertices());
      surf.connect(0,2);
      assertEquals(8,surf.missingVertices());
      surf.connect(0,3);
      assertEquals(6,surf.missingVertices());
      assertEquals(3,surf.getFreeList().length);
      surf.connect(1,2);
      surf.connect(1,3);
      assertEquals(2,surf.getFreeList().length);
      surf.connect(2,3);
      assertEquals(null,surf.getFreeList());
      assertEquals(0,surf.missingVertices());
      surf.disconnectAll();
      int[] vertices = surf.getVertices();
      for(int i=0;i<vertices.length;i++)
        assertEquals(0,vertices[i]);
      
      assertEquals(4,surf.getFreeList().length);
      surf.connect(0,1);
      surf.connect(0,2);
      surf.connect(0,3);
      assertEquals(true,surf.hasMaxVertex(0));
      assertEquals(3,surf.getFreeList().length);
      vertices = surf.getVertices();
      assertEquals(3,vertices[0]);
      for(int i=1;i<vertices.length;i++)
        assertEquals(1,vertices[i]);
      surf.disconnect(0,1);
      vertices = surf.getVertices();
      assertEquals(2,vertices[0]);
      assertEquals(0,vertices[1]);
      for(int i=2;i<vertices.length;i++)
        assertEquals(1,vertices[i]);
      surf.disconnectAll();
      vertices = surf.getVertices();
      for(int i=0;i<vertices.length;i++)
        assertEquals(0,vertices[i]);
   }

}
