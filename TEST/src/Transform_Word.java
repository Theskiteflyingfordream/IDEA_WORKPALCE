import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Transform_Word {

    static String a;                    //单词a
    static String b;                    //单词b
    static int[][] Dist;                //优化前用于存储子问题的解的二维数组
    static int[] Optimization_Dist;     //优化后用于存储子问题的解的二维数组
    static int minopnum;                //存储最后所得的操作数

    public static void main(String[] args) {
        //用户输入a和b
        Scanner sc = new Scanner(System.in);
        System.out.println("输入单词a:");
        a = sc.next();
        System.out.println("输入单词b:");
        b = sc.next();
        Dist = new int[a.length() + 1][b.length() + 1];
        //计时
        Instant start = Instant.now();
        //动态规划解决问题
        minopnum = DPsolve();
        //计时
        Instant end = Instant.now();
        //输出
        System.out.println("动态规划结果："+minopnum);
        System.out.println("动态规划用时："+Duration.between(start,end).toNanos()+"ns");
        //动态规划解决，优化存储空间，将二维存储降为一维存储（该优化仅对存储空间，时间复杂度并未增加或减少，故不计算用时）
        Optimization_Dist = new int[b.length() + 1];
        System.out.println("优化存储空间的动态规划结果："+DPsolve_RollingArray());
    }



    //动态规划求解
    public static int DPsolve(){
        //确定边界
        for (int i = 0; i < a.length() + 1; i++) {
            Dist[i][0] = i;
        }
        for (int i = 0; i < b.length() + 1; i++) {
            Dist[0][i] = i;
        }
        //求每一个子问题的最优解
        for (int i = 1; i < a.length() + 1; i++) {
            for (int j = 1; j < b.length() + 1; j++) {
                if(a.charAt(i-1)==b.charAt(j-1)) {
                    Dist[i][j] = min(Dist[i-1][j-1],Dist[i-1][j]+1,Dist[i][j-1]+1);
                }else {
                    Dist[i][j] = min(Dist[i-1][j-1]+1,Dist[i-1][j]+1,Dist[i][j-1]+1);
                }
            }
        }
        //返回结果
        return Dist[a.length()][b.length()];
    }

    //空间优化后的动态规划求解（利用滚动数组减少存储空间）
    public static int DPsolve_RollingArray(){
        //确定上边界
        for (int i = 0; i < b.length() + 1; i++) {
            Optimization_Dist[i] = i;
        }
        //temp用于存储每一次内循环中的Dist[i-1][j-1]，由边界得其初始值为0
        int temp = 0;
        //求每一个子问题的最优解，每一次内循环中，对Optimization_Dist中得元素依次迭代，min中的Optimization_Dist[j]对应原来的Dist[i-1][j]，Optimization_Dist[j-1]对应原来的Dist[i][j-1]
        for (int i = 1; i < a.length() + 1; i++) {
            for (int j = 0; j < b.length() + 1; j++) {
                int p = Optimization_Dist[j];   //记录当前的Optimization_Dist[j]作为下一个内循环的Dist[i-1][j-1]
                if(j==0){
                    Optimization_Dist[j] = i;   //对于每一行确定左边界
                }else {
                    if(a.charAt(i-1)==b.charAt(j-1)) {
                        Optimization_Dist[j] = min(temp,Optimization_Dist[j]+1,Optimization_Dist[j-1]+1);
                    }else {
                        Optimization_Dist[j] = 1 + min(temp,Optimization_Dist[j],Optimization_Dist[j-1]);
                    }
                }
                temp = p;
            }
        }
        //返回结果
        return Optimization_Dist[b.length()];
    }

    //返回三个数中最小的数
    static int min(int i,int j,int k){
        if(i<=j){
            if(i<=k) return i;
            return k;
        }else if(j<=k){
            return j;
        }
        return k;
    }

}
