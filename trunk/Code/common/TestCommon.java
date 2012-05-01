public class TestCommon {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       String filename = "test.input";
       TSPCommon common = new TSPCommon();
       TSPCommon.surface mySurface = common.readData(filename);

       System.out.println(mySurface.getMaxVertex());
    }
    
}
