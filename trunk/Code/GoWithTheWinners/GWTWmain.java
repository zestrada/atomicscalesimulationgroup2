public class GWTWmain {
  
    private static Surface surface;
    private static GWTW[] g = new GWTW[5];

  public static void main(String[] args) {
      //gwtwInit();
      for(int i = 0; i < 5; i++) {
	  g[i] = new GWTW(i);
	  g[i].setID(i);
	  g[i].start();
      }
      System.out.print("DONE\n");
  }

  public static void gwtwInit() {
      //surface = new Surface();
  }

}
