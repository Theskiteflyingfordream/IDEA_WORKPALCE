import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class Minimum_weight_fixed_point_covering_problem {
    static class PQueueNode{          //优先队列的结点
        int i;  //当前节点序号
        int cn; //当前权重
        int []x;//结果数组
    }
    static class Edge{
        int node1;
        int node2;

        public Edge(int node1, int node2) {
            this.node1 = node1;
            this.node2 = node2;
        }
    }

    static int POINTNUM;            //结点个数
    static int[]W;                  //记录每个结点的权重
    static List<Edge> a = new ArrayList<>();//a存储边
    static int[] result;            //结果向量
    static int bestn;               //记录当前最好值

    public static void main(String[] args) throws Exception {
        //初始化static变量以及从文件读取数据
        Scanner sc = new Scanner(new File("input.txt"));
        POINTNUM = sc.nextInt();
        int EDGENUM = sc.nextInt();
        W = new int[POINTNUM + 1];
        result = new int[POINTNUM + 1];
        for (int i = 0; i < POINTNUM; i++) {
            W[i + 1] = sc.nextInt();
        }
        for (int i = 0; i < EDGENUM; i++) {
           a.add(new Edge(sc.nextInt(),sc.nextInt()));
        }
        sc.close();
        //优先队列的分支限界法
        SOLVE();
        //输出结果到文件中
        PrintStream out = new PrintStream(new File("output.txt"));
        out.println(bestn);
        for (int i = 1; i < POINTNUM + 1; i++) {
            out.printf(result[i] + " ");
        }
        out.close();
    }

    public static void SOLVE(){
        //开辟优先队列
        Queue<PQueueNode> PQueue = new PriorityQueue<PQueueNode>((a,b)->a.cn - b.cn);
        //新建第一个队列结点并初始化
        PQueueNode E = new PQueueNode();
        E.x = new int[POINTNUM + 1];
        E.cn = 0;
        E.i = 0;
        //j用来表示当前所处理的结点
        int j = 1;
        //优先队列的分支限界法主体
        while (true){
            //进入这个判断表示此时已经到达树叶，由于其为优先队列的分支限界法，若此时已经满足cover，则其为最优值，否则直接迭代E
            if(j > POINTNUM){
                if(cover(E)){
                    for (int i = 0; i < POINTNUM + 1; i++) {
                        result[i] = E.x[i];
                    }
                    bestn = E.cn;
                    break;
                }
            }else{
                //将其并入U中
                if(!cover(E)){
                    Insert(PQueue,E,j,true);
                }
                //不将其并入U中
                Insert(PQueue,E,j,false);
            }
            //若此时队列空表示已经没有活结点则退出，否则迭代E
            if(PQueue.size() == 0) break;
            E = PQueue.poll();
            //改变j的值
            j = E.i + 1;
        }
    }

    //对N进行初始化然后插入优先队列中
    public static void Insert (Queue<PQueueNode> PQueue,PQueueNode E,int j,boolean prj){
        PQueueNode N = new PQueueNode();
        N.i = j;                     //j表示此时正在处理第j各结点的包含与否情况
        N.x = new int[POINTNUM + 1];
        for (int i = 0; i < POINTNUM + 1; i++) {
            N.x[i] = E.x[i];
        }
        if(prj){                     //prj为ture表示包含否则不包含
            N.x[j] = 1;
            N.cn = E.cn + W[j];
        }else{
            N.x[j] = 0;
            N.cn = E.cn;
        }
        PQueue.add(N);
    }

    //判断是否覆盖图，方法为对于每一条边判断E中的结果向量是否满足要求，若有一条满不满足则返回false
    static boolean cover(PQueueNode E){
        boolean prj = false;
        for (int i = 0; i < a.size(); i++) {
            for (int j = 1; j < POINTNUM + 1; j++) {
                if(E.x[j] == 1 && (j==a.get(i).node1 || j==a.get(i).node2)) prj = true;
            }
            if (!prj) return prj;
            prj = false;
        }
        return true;
    }


}
