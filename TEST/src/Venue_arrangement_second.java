import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Venue_arrangement_second {

    public static void main(String[] args) throws Exception {
        //从文件读取数据
        Scanner sc = new Scanner(new File("./TEST/mytest.txt"));
        int COUNT = sc.nextInt();
        int []start = new int[COUNT];
        int []end = new int[COUNT];
        for (int i = 0; i < COUNT; i++) {
            start[i] = sc.nextInt();
            end[i] = sc.nextInt();
        }
        sc.close();
        //排序
        Arrays.sort(start); //对start进行升序排序
        Arrays.sort(end);   //对end进行升序排序
        //贪心求解并输出到文件
        PrintStream out = new PrintStream(new File("./TEST/output.txt"));
        out.println(solve(start,end,COUNT));
        out.close();
    }

    //贪心算法
    public static int solve(int[] start,int []end,int COUNT){
        int j = 0;
        int sum = 0;    //sum记录会场数
        //其依然按照相同的贪心决策，从活动结束最早的开始选
        for (int i = 0; i < COUNT; i++) {
            if(start[i] < end[j]){
                ++sum;
            }else{
                ++j;
            }
        }
        return sum;
    }

}
