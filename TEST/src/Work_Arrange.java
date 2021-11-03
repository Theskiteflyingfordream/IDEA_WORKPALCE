import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Work_Arrange {

    static int cw = 0;  //当前费用
    static int bestw = 0; //当前最优费用
    static int Count;   //工作数目

    public static void main(String[] args) throws Exception {
        //从文件中读入数据
        Scanner sc = new Scanner(new File("input.txt"));
        Count = sc.nextInt();
        int [][] cost = new int[Count][Count];      //cost为费用数组，下标从0开始
        int [] x = new int[Count];                  //x为解向量
        for (int i = 0; i < Count; i++) {
            cost[i] = new int[Count];
            x[i] = i + 1;
            for (int j = 0; j < Count; j++) {
                cost[i][j] = sc.nextInt();
            }
        }
        sc.close();
        //回溯法求解并输出到文件中
        PrintStream out = new PrintStream(new File("output.txt"));
        Backtrack(0,x,cost);
        out.println(bestw);
        out.close();
    }
    //回溯法
    public static void Backtrack(int n,int[] x,int [][]cost){
        if(n == Count-1){
            if(cw < bestw || bestw == 0) bestw = cw + cost[n][x[n] - 1];
        }else {
            for (int i = n; i < Count; i++) {
                Swap(x, n, i);
                cw += cost[n][x[n] - 1];
                if (cw < bestw || bestw==0) {   //cw<bestw未限界函数，如果当前已经比所得出的的费用要多，则剪枝
                    Backtrack(n + 1,x,cost);
                }
                cw -= cost[n][x[n] - 1];
                Swap(x, n, i);
            }
        }
    }
    //交换x向量的值
    public static void Swap(int[] x,int i,int j){
        int t = x[i];
        x[i] = x[j];
        x[j] = t;
    }

}
