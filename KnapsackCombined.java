import java.io.*;
import java.util.*;

class node {
    int level;
    int profit;
    int weight;
    int bound;
}

public class KnapsackCombined {
    public static void main(String args[]) throws Exception {
        int maxProfit;
        int N;
        int W;
        int maxVal;

        Scanner input = new Scanner(System.in);

        int Vl[] = new int[] { 60, 100, 120 };
        int Wt[] = new int[] { 10, 20, 30 };
        W = 50;
        N = Vl.length;

        maxVal = bb(N, Vl, Wt, W);
        System.out.println("Branch and Bound = " + maxVal);

        double maxValue = getMaxValue(Wt, Vl, W); 
        System.out.println("Greedy Approach = " +  
                            maxValue); 
        
        System.out.println("Dynamic Programming = " + knapSack(W, Wt, Vl, N));
    }

    public static int bound(node u, int n, int W, int[] pVa, int[] wVa) {
        int j = 0, k = 0;
        int totweight = 0;
        int result = 0;

        if (u.weight >= W) {
            return 0;
        } else {
            result = u.profit;
            j = u.level + 1;
            totweight = u.weight;

            while ((j < n) && (totweight + wVa[j] <= W)) {
                totweight = totweight + wVa[j];
                result = result + pVa[j];
                j++;
            }

            k = j;

            if (k < n) {
                result = result + (W - totweight) * pVa[k] / wVa[k];
            }
            return result;
        }
    }

    public static int bb(int n, int[] p, int[] w, int W) {
        Queue<node> Q = new LinkedList<node>();
        node u = new node();
        node v = new node();
        int[] pV = new int[p.length];
        int[] wV = new int[w.length];
        Q.poll();

        for (int i = 0; i < n; i++) {
            pV[i] = p[i];
            wV[i] = w[i];
        }

        v.level = -1;
        v.profit = 0;
        v.weight = 0;

        int maxProfit = 0;

        // v.bound = bound(v, n, W, pV, wV);
        Q.add(v);

        while (Q.size() > 0) {
            v = Q.remove();
            node a = new node();

            if (v.level == -1) {
                a.level = 0;
            } else if (v.level != (n - 1)) {
                a.level = v.level + 1;
            }

            a.weight = v.weight + w[a.level];
            a.profit = v.profit + p[a.level];

            a.bound = bound(a, n, W, pV, wV);

            if (a.weight <= W && a.profit > maxProfit) {
                maxProfit = a.profit;
            }

            if (a.bound > maxProfit) {
                Q.add(a);
            }

            node b = new node();
            b.level = v.level + 1;

            b.weight = v.weight;
            b.profit = v.profit;

            b.bound = bound(b, n, W, pV, wV);

            if (b.bound > maxProfit) {
                Q.add(b);
            }

        }
        return maxProfit;
    }

    // function to get maximum value
    private static double getMaxValue(int[] wt, int[] val, int capacity) {
        ItemValue[] iVal = new ItemValue[wt.length];

        for (int i = 0; i < wt.length; i++) {
            iVal[i] = new ItemValue(wt[i], val[i], i);
        }

        // sorting items by value;
        Arrays.sort(iVal, new Comparator<ItemValue>() {
            @Override
            public int compare(ItemValue o1, ItemValue o2) {
                return o2.cost.compareTo(o1.cost);
            }
        });

        double totalValue = 0d;

        for (ItemValue i : iVal) {

            int curWt = (int) i.wt;
            int curVal = (int) i.val;

            if (capacity - curWt >= 0) {
                // this weight can be picked while
                capacity = capacity - curWt;
                totalValue += curVal;

            }
        }

        return totalValue;
    }

    // item value class
    static class ItemValue {
        Double cost;
        double wt, val, ind;

        // item value function
        public ItemValue(int wt, int val, int ind) {
            this.wt = wt;
            this.val = val;
            this.ind = ind;
            cost = new Double(val / wt);
        }
    }

    // A utility function that returns maximum of two integers 
    static int max(int a, int b)  
        { return (a > b) ? a : b; } 
  
    // Returns the maximum value that can be put in a knapsack 
    // of capacity W 
    static int knapSack(int W, int wt[], int val[], int n) 
    { 
        int i, w; 
        int K[][] = new int[n + 1][W + 1]; 
  
        // Build table K[][] in bottom up manner 
        for (i = 0; i<= n; i++) { 
            for (w = 0; w<= W; w++) { 
                if (i == 0 || w == 0) 
                    K[i][w] = 0; 
                else if (wt[i - 1]<= w) 
                    K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]); 
                else
                    K[i][w] = K[i - 1][w]; 
            } 
        } 
  
        return K[n][W]; 
    }
}