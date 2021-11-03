import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Backtrack {
    static String a;            //单词a
    static String b;            //单词b
    static StringBuilder a_copy = new StringBuilder();  //单词a的副本
    static int[] x;             //当前解向量，0/1/2/3/4分别代表匹配，删除，替换，向前插入，向后插入
    static int[] result;        //最优解向量
    static int minopnum = 0;    //最小操作次数
    static int nowopnum = 0;    //记录当前操作次数

    public static void main(String[] args) {
        //用户输入a和b
        Scanner sc = new Scanner(System.in);
        System.out.println("输入单词a:");
        a = sc.next();
        System.out.println("输入单词b:");
        b = sc.next();
        x = new int[a.length()+1];
        for (int i = 0; i < x.length; i++) {
            x[i] = -1;
        }
        result = new int[a.length()+1];
        minopnum = a.length();  //操作次数最大为单词a的长度
        //计时
        Instant start = Instant.now();
        //回溯加剪枝解决（注意在处理中单词a以及单词b以及副本的0位均为有效字符）
        solve(1);
        //计时
        Instant end = Instant.now();
        //输出
        System.out.println("回溯加剪枝法结果："+minopnum);
        System.out.println("回溯加剪枝法用时："+Duration.between(start,end).toNanos()+"ns");
        System.out.println("对单词a每一位的处理如下");
        for (int i = 1; i < result.length; i++) {
            System.out.print(result[i]+" ");
        }
    }

    //回溯加剪枝
    static void solve(int t){
        //若a_copy已经与b相等
        if(a_copy.toString().equals(b)){
            //若此时已经将a中的所有字符处理完
            if(t>a.length()) {
                //如果当前所得操作次数比最优的还小
                if(nowopnum < minopnum){
                    minopnum = nowopnum;
                    for (int i = 1; i < x.length; i++) {
                        result[i] = x[i];
                    }
                }
                return;
            }
        //否则判断此时是否已经将a的所有字符处理完或者副本中的长度大于等于单词b的长度，是则进行剪枝
        }else {
            if(t>a.length()||a_copy.length()>=b.length()) return;
        }
        for (int i = 0; i < 5; i++) {
            //x记录操作
            x[t] = i;
            //依据i对当前字符串进行处理
            switch (i){
                //匹配
                case 0:
                    //剪枝判断：判断当前副本长度是否小于b的长度（因为匹配会插入而会使副本增加字符，如果副本已经大于或等于b的长度，则匹配不会得出正确的解）而且当前字符与待加入的b中的字符是否相等（如此时副本为‘aac’而b为‘aacd’，则d为待加入），满足才能将当前字符加入否则进行剪枝
                    if(a_copy.length()<b.length()&&a.charAt(t-1)==b.charAt(a_copy.length())){
                        a_copy.append(a.charAt(t-1));               //在副本中加入当前处理字符
                        solve(t+1);                              //递归
                        a_copy.deleteCharAt(a_copy.length() - 1);   //删除加入的当前字符
                    }
                    break;
                //删除
                case 1:
                    //剪枝判断：只有当前操作次数满足小于最优操作次数才继续递归，否则剪枝
                    if(nowopnum<minopnum) {
                        ++nowopnum;                                 //操作次数加一
                        solve(t+1);                              //递归
                        --nowopnum;                                 //操作次数减一
                    }
                    break;
                //替换
                case 2:
                    //剪枝判断：判断当前操作次数是否小于最优操作次数以及当前副本长度是否小于b的长度（理由同匹配操作）以及若a当前字符是否与待插入b字符不相等（若相等此时最优应该进行匹配操作，故剪枝）
                    if(nowopnum<=minopnum&&a_copy.length()<b.length()&&a.charAt(t-1)!=b.charAt(a_copy.length())){
                        ++nowopnum;                                 //操作次数加一
                        a_copy.append(b.charAt(a_copy.length()));   //限定替换的字符只能为待加入字符（因为只有这样才可能产生正确解而且最优解）
                        solve(t+1);                              //递归
                        a_copy.deleteCharAt(a_copy.length()-1);     //删除加入的字符
                        --nowopnum;                                 //操作数减一
                    }
                    break;
                //向前插入
                case 3:
                    //剪枝判断：判断当前操作数是否满足要求（由于向前插入需要向副本中插入当前字符以及b中待插入字符（此时的待插入字符与上述不同））以及当前字符是否与规定字符相同（如副本为'aa',b为‘aacd’，则待插入字符为c，规定字符为d）（作此限制在于确保该次操作是可以得出正确且最优解的）
                    if(nowopnum<=minopnum&&a_copy.length()+2<=b.length()&&a.charAt(t-1)==b.charAt(a_copy.length()+1)){
                        ++nowopnum;                                 //操作次数加一
                        a_copy.append(b.charAt(a_copy.length()));   //先插入待插入字符
                        a_copy.append(a.charAt(t-1));               //再插入当前字符
                        solve(t+1);                              //递归
                        a_copy.deleteCharAt(a_copy.length()-1);     //删除当前字符
                        a_copy.deleteCharAt(a_copy.length()-1);     //删除待插入字符
                        --nowopnum;                                 //操作次数减一
                    }
                    break;
                //向后插入
                case 4:
                    //剪枝判断：判断当前操作数是否满足要求（同向前插入）以及当前字符是否与规定字符相同（如副本为‘aa’，b为‘aacd’，则规定字符为c，待插入字符为d）（作此限制在于确保该次操作是可以得出正确且最优解的）
                    if (nowopnum<=minopnum&&a_copy.length()+2<=b.length()&&a.charAt(t-1)==b.charAt(a_copy.length())){
                        ++nowopnum;                                 //操作次数加一
                        a_copy.append(a.charAt(t-1));               //插入当前字符
                        a_copy.append(b.charAt(a_copy.length()));   //插入待插入字符
                        solve(t+1);                              //递归
                        a_copy.deleteCharAt(a_copy.length()-1);     //删除待插入字符
                        a_copy.deleteCharAt(a_copy.length()-1);     //删除当前字符
                        --nowopnum;                                 //操作次数减一
                    }
                    break;
            }
        }
        //一次操作结束回溯回来后需要将当前解向量对应位置置为-1
        x[t] = -1;
    }
}
