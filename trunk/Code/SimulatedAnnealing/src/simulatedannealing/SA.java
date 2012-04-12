/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;

/**
 *
 * @author jlai7
 */
import java.util.Random;

public class SA {
    
    static Random rng;
    int N,n;
    double[][] distance, weight,oldWeight;
    double alpha = 0.0;
    
    public SA(int N, int n, double[][] distance, double[][] weight) {
        init(N,n,distance,weight);
    }
    
    private void init(int N, int n, double[][] distance, double[][] weight) {
        this.N = N;
        this.n = n;
        this.distance = distance;
        this.weight = new double[N][N];
        this.oldWeight = new double[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                this.weight[i][j] = weight[i][j];
                this.oldWeight[i][j] = weight[i][j];
            }
        }
        this.rng = new java.util.Random();
    }
    
    public double get_energy() {
        double energy = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < i; j++) {
               energy += (distance[i][j]*weight[i][j]);
            }
        }
        return energy;
    }
    
    public double[][] run(int len, double T0) {
        int accept = 0;
        int total = 0;
        double temp = T0;
        double oldEnergy, newEnergy, deltaE;
        for(int i = 0; i < len; i++) {
            temp = (T0 - (T0*len)/(i + 1));
            oldEnergy = get_energy();
            move();
            newEnergy = get_energy();
            deltaE = oldEnergy - newEnergy;
            if(rng.nextDouble() < Math.exp(-deltaE/temp) ) {
                //System.out.println(newEnergy + " " + temp);
                accept++;
            } else {
                //System.out.println(oldEnergy + " " + temp);
                for(int z = 0; z < N; z++) {
                    for(int y = 0; y < N; y++) {
                        weight[z][y] = oldWeight[z][y];
                    }
                }
            }
            total++;
        }
        System.out.println("AR: " + accept*1.0/total);
        return weight;
    }

    private void move() {
        int x,y;
        double sumX = 0;
        double sumY = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                oldWeight[i][j] = weight[i][j];               
            }
        }
        do{
            x = rng.nextInt(N);
            y = rng.nextInt(N);
        } while(x == y);
        //double store = 1.0*rng.nextDouble();
        double store = 0.001;
        double delta = -(store - weight[x][y]);
        weight[x][y] = store;
        weight[y][x] = store;           
        sumX = 1 - store;
        sumY = 1 - store;
        for(int z = 0; z < N; z++) {
            if(x != z && y != z) {
                weight[x][z]+=(sumX/(N-2));
                weight[z][y]+=(sumY/(N-2));                
            }
        }
    }
}
