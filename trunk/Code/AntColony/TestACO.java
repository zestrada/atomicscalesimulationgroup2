import org.junit.*;
import junit.framework.*;
import java.util.ArrayList;
import java.lang.Math;

public class TestACO extends TestCase {
  public void testAntPheromone() throws java.lang.CloneNotSupportedException {
    int numAnts=2;
    //Test Reading of data
    String filename = "test.input";
    TSPInOut inout = new TSPInOut();
    Surface surf = inout.readData(filename);
    Ant[] ants = new Ant[numAnts];

    //Initialize pheromone
    Pheromone pheromone = new Pheromone(surf.getN());

    for(int i=0;i<numAnts;i++) {
      ants[i] = new Ant(surf.clone(),pheromone);
    }

    
    for(int a=0;a<numAnts;a++) {
      for(int i=0;i<surf.getN();i++) {
        for(int j=0;j<surf.getN();j++) {
          assertEquals(0.0,ants[a].getPheromone(i,j));
        }
      }
    }

    //Test pass-by-reference of phermone matrix
    pheromone.incr(1,0,1.0);
    for(int a=0;a<numAnts;a++) {
      assertEquals(1.0,ants[a].getPheromone(1,0));
      assertEquals(1.0,ants[a].getPheromone(0,1));
    }
    pheromone.decr(1,0,1.0);
    for(int a=0;a<numAnts;a++) {
      assertEquals(0.0,ants[a].getPheromone(1,0));
      assertEquals(0.0,ants[a].getPheromone(0,1));
    }
    pheromone.set(1,0,1.0);
    for(int a=0;a<numAnts;a++) {
      assertEquals(1.0,ants[a].getPheromone(1,0));
      assertEquals(1.0,ants[a].getPheromone(0,1));
    }

  }
}
