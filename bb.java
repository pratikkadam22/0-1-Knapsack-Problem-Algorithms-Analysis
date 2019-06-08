import java.io.*;
import java.util.*;

class node{
    int level;
    int profit;
    int weight;
    int bound;
}

public class bb{
        public static void main(String args[])throws Exception{
                int maxProfit;
                int N;
                int W;
                int maxVal;

                Scanner input = new Scanner(System.in);

                int Vl[] = new int[] { 51, 85, 102, 102, 33, 99, 136, 40, 95, 187, 99, 2, 163 }; 
                int Wt[] = new int[] { 23, 110, 186, 124, 162, 82, 126, 112, 147, 171, 123, 20, 10 }; 
				W = 500; 
                N = Vl.length; 
                
                maxVal = knapsack(N, Vl, Wt, W);

                System.out.println(maxVal);

        }


        public static int bound(node u, int n, int W, int[] pVa, int[] wVa){
                int j = 0, k = 0;
                int totweight = 0;
                int result = 0;

                if (u.weight >= W){
                        return 0;
                }
                else{
                        result = u.profit;
                        j = u.level + 1;
                        totweight = u.weight;

                        while ((j < n) && (totweight + wVa[j] <= W)){
                                totweight = totweight + wVa[j];
                                result = result + pVa[j];
                                j++;
                        }

                        k = j;

                        if (k < n){
                                result = result + (W - totweight) * pVa[k]/wVa[k];
                        }
                        return result;
                }
        }

        public static int knapsack(int n, int[] p, int[] w, int W){
                Queue<node> Q = new LinkedList<node>();
                node u = new node();
                node v = new node();
                int[] pV = new int[p.length];
                int[] wV = new int[w.length];
                Q.poll();

                for (int i = 0; i < n; i++){
                        pV[i] = p[i];
                        wV[i] = w[i];
                }

                v.level = -1;
                v.profit = 0;
                v.weight = 0;

                int maxProfit = 0;

                //v.bound = bound(v, n, W, pV, wV);
                Q.add(v);

                while (Q.size() > 0){
                        v = Q.remove();
						node a = new node();

                        if (v.level == -1){
                                a.level = 0;
                        }
                        else if (v.level != (n - 1)){
                                a.level = v.level + 1;
                        }

                        a.weight = v.weight + w[a.level];
                        a.profit = v.profit + p[a.level];

                        a.bound = bound(a, n, W, pV, wV);

                        if (a.weight <= W && a.profit > maxProfit){
                                maxProfit = a.profit;
                        }

                        if (a.bound > maxProfit){
                                Q.add(a);
                        }

						node b = new node();
						b.level = v.level + 1;
						
                        b.weight = v.weight;
                        b.profit = v.profit;

                        b.bound = bound(b, n, W, pV, wV);

                        if (b.bound > maxProfit){
                                Q.add(b);
                        }

                }
                return maxProfit;
        }
}