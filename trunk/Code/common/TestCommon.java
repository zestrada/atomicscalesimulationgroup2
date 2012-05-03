import org.junit.*;
import junit.framework.*;

public class TestCommon extends TestCase {
    public void testTSPCommon() {
      String filename = "test.input";
      TSPInOut inout = new TSPInOut();
      Surface mySurface = inout.readData(filename);
      assertEquals(3,mySurface.getMaxVertex());
    }
}
