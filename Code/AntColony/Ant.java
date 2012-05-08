public class Ant {
  private Surface surf;
  private Pheromone pheromone;
  private double[][] eta;
  private double alpha, beta;

  public Ant(Surface inputSurf, Pheromone inputPheromone, double alpha, double beta) throws java.lang.CloneNotSupportedException {
    this.alpha=alpha;
    this.beta=beta;
    pheromone = inputPheromone;
    surf = new Surface(inputSurf); 
    eta = new double[surf.getN()][surf.getN()];
    for(int i=0; i<surf.getN();i++) {
      for(int j=0; j<surf.getN();j++) {
        eta[i][j] = 1.0/surf.getDistance(i,j);
      }
    }
  }

  //Reset this ant's state
  public void reset() {
    surf.disconnectAll();
  }

  public void setPheromone(Pheromone newPheromone) {
    pheromone = newPheromone;
  }

  public void getPheromone(Pheromone newPheromone) {
    pheromone = newPheromone;
  }

  public double getPheromone(int i, int j) {
    return pheromone.get(i,j);
  }

  public Surface getSurface() {
    return surf;
  }

  //Do the main work based on Surface, pheromone
  //leaves surface member object modified
  public double constructSolution() {
    double length=0.0;
    boolean done=false;
    int[] freeList;
    double[] pList; //Probability of choosing each site
    double norm,prob,temp; //Normalization, probability, scratch double
    while(!done) {
      //Not sure if we should define a maximum distance, might improve
      //convergence greatly

      freeList = surf.getFreeList(); 
      System.out.println(freeList.length);
      if(freeList.length<=1) {
        done = true;
        break;
      }
      //Where our connection will "start" from
      int source = freeList[randInt(freeList.length)];
      //Now, build a list of probabilities
      pList = new double[freeList.length];
      norm=0.0;
      for(int i=0;i<freeList.length;i++) {
        if(i==source || surf.hasMaxVertex(i)) {
          pList[i]=0.0;
          continue; //no self connections or over vertices
        }
        pList[i] = Math.pow(pheromone.get(source,i),alpha)*Math.pow(eta[source][i],beta);
        norm+=pList[i];
      }
      norm=1.0/norm;
      temp=Math.random();
      prob=0.0;
      //Simple weighted probability distribution
      for(int i=0;i<freeList.length;i++) {
        if(i==source) continue;
        prob+=pList[i]*norm;
        if(prob>=temp) {
          surf.connect(i,source);  
          length+=surf.getDist(i,source);
          break;
        }
      }
    }
    return length;
  }

  private int randInt(int max) {
    return (int)(Math.random() * (max));
  }
}
