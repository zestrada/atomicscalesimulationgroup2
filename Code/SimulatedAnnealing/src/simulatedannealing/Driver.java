/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;

/**
 *
 * @author jlai7
 */
public class Driver {
    
   int n,N;
   double[][] weight, distance, position;
  
   public Driver() {
        init();
        SA sa = new SA(N, n, distance, weight);
        System.out.println(sa.get_energy());
        double[][] answer = sa.run(1000,1.0);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.format("%1$1f ", answer[i][j]);                          
            }
            System.out.println();
        }
        //test();
        //wTest();
        //wdTest();
    }

   public void init() {
        N = 9;
        weight = new double[N][N];
        distance = new double[N][N];
        n = (int)java.lang.Math.sqrt(N);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(i == j) {
                    weight[i][j] = 0.0;
                } else {
                    weight[i][j] = 1.0/(N-1);
                }
            }
        }
        initPlacement();
        initDistance();
    }

    private void initPlacement() {
        position = new double[N][2];
        int z;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                z = i*n + j;
                position[z][0] = i;
                position[z][1] = j;               
            }
        }
    }

    private void initDistance() {
        double dx,dy;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < i; j++) {
                dx = java.lang.Math.abs(position[i][0] - position[j][0]);
                dy = java.lang.Math.abs(position[i][1] - position[j][1]);
                distance[i][j] = java.lang.Math.sqrt(dx*dx + dy*dy);
                distance[j][i] = java.lang.Math.sqrt(dx*dx + dy*dy);
            }
        }
    }

    private void test() {
        int z = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                z = i*n + j;
                System.out.print(" (" + position[z][0] + " , " + position[z][1] + ") ");                          
            }
            System.out.println();
        }
    }

    private void wTest() {
        int z = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.print(weight[i][j] + " ");                          
            }
            System.out.println();
        }
    }

    private void wdTest() {
        int z = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.print(distance[i][j] + " ");                          
            }
            System.out.println();
        }
    }

}
