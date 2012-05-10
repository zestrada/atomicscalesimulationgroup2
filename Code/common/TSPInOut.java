/**
 * Common library for surface reconstruction problem in java
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Queue;
import java.util.LinkedList;

public class TSPInOut {
  Queue <Double> energies;
  int id;
  long startTime;
  boolean stdout;

  public TSPInOut() {
    id=0;
    energies = new LinkedList<Double>();
    startTime = System.currentTimeMillis();
    stdout=true;
  }

  public TSPInOut(int id) {
    this.id=id;
    energies = new LinkedList<Double>();
    stdout=true;
  }

  public void disableStdout() {
    stdout=false;
  }

  public Surface readData(String filename) {
    String line;
    int npart = 0;
    int nvert = 0;
    double xmin=0, xmax=0, ymin=0, ymax=0;
    Cell myCell;

    ArrayList<Double> xcoord = new ArrayList<Double>();
    ArrayList<Double> ycoord = new ArrayList<Double>();

    //Regex patterns to extract data
    String floatStr = "[-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?";
    Pattern vertexPattern = Pattern.compile("#nvert (\\d+)");
    Pattern npartPattern = Pattern.compile("#npart (\\d+)");
    Pattern xminmaxPattern = Pattern.compile("#x ("+floatStr+")\\s+("+floatStr+")");
    Pattern yminmaxPattern = Pattern.compile("#y ("+floatStr+")\\s+("+floatStr+")");
    Pattern coordPattern = Pattern.compile("("+floatStr+")\\s+("+floatStr+")");
     
    //System.out.println(coordPattern.pattern());
    //Actually parse the file
    try {
      if(stdout)
        System.out.println("Reading in data from "+filename);
      FileReader freader = new FileReader(filename);
      BufferedReader reader = new BufferedReader(freader);
   
      while((line = reader.readLine())!=null) {

        //Grab number of vertices
        if(nvert<=0) {
          Matcher m = vertexPattern.matcher(line);
          if(m.find()) {
            if(stdout)
              System.out.println("Number of vertices: " + m.group(1));
            nvert = Integer.parseInt(m.group(1));
            continue;
          }
        }
      
        //Grab number of particles
        if(npart<=0) {
          Matcher m = npartPattern.matcher(line);
          if(m.find()) {
            if(stdout)
              System.out.println("Number of particles: " + m.group(1));
            npart = Integer.parseInt(m.group(1));
            continue;
          }
        }

        if(xmax==xmin) {
          Matcher m = xminmaxPattern.matcher(line);
          if(m.find()) {
            if(stdout)
              System.out.println("xmin " + m.group(1) + " xmax " +  m.group(2));
            xmin = Double.parseDouble(m.group(1));
            xmax = Double.parseDouble(m.group(2));
            continue;
          }
        }

        if(ymax==ymin) {
          Matcher m = yminmaxPattern.matcher(line);
          if(m.find()) {
            if(stdout)
              System.out.println("ymin " + m.group(1) + " ymax " +  m.group(2));
            ymin = Double.parseDouble(m.group(1));
            ymax = Double.parseDouble(m.group(2));
            continue;
          }
        }

        //Bail on comments
        if(line.charAt(0)=='#')
          continue;

        //Grab coordinates
        if(npart>0) {
          Matcher m = coordPattern.matcher(line);
          if(m.find()) {
            //System.out.println("x " + m.group(1) + " y " +  m.group(2));
            xcoord.add(Double.parseDouble(m.group(1)));
            ycoord.add(Double.parseDouble(m.group(2)));
            continue;
          } else {
            if(stdout)
              System.out.println(line);
          }
        }

      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    if(xcoord.size()!=npart) {
      System.out.println("Found "+(xcoord.size())+" particles, expected to find "+npart); 
      System.exit(1);
    }
    if(stdout)
      System.out.printf("Found %d particles with maximum number of %d vertices\n",npart,nvert);

    //Need to read in coordinates from file and create a cell
    myCell = new Cell(xmin,xmax,ymin,ymax,stdout);
    return new Surface(xcoord, ycoord, nvert, myCell);
  }

  //Save an energy value per step
  public void recordEnergy(double energy) {
    energies.offer(energy);
  }

  //Outpout the energies
  public void outputEnergy() {
    Double temp;
    try{ 
      String filename = "energy"+id+".out";
      FileWriter fw = new FileWriter(filename);
      BufferedWriter out = new BufferedWriter(fw);
      while(energies.peek() != null) {
        temp = energies.poll();
        out.write(temp.doubleValue()+"\n");
      }
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public void timerStart() {
    startTime = System.currentTimeMillis();
  }

  public long timerStop() {
    return System.currentTimeMillis()-startTime;
  }
}
