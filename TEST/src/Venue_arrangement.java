import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Activity{
    int start;
    int end;
    boolean arranged;

    public Activity() {
    }

    public Activity(int start, int end, boolean arranged) {
        this.start = start;
        this.end = end;
        this.arranged = arranged;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isArranged() {
        return arranged;
    }

    public void setArranged(boolean arranged) {
        this.arranged = arranged;
    }
}

public class Venue_arrangement {

    public static void main(String[] args) throws Exception {
        int []start;
        int []end;
        List<Activity> activityList = new ArrayList<>();
        //从文件读取数据
        Scanner sc = new Scanner(new File("./TEST/mytest.txt"));
        int COUNT = sc.nextInt();
        for (int i = 0; i < COUNT; i++) {
            activityList.add(new Activity(sc.nextInt(),sc.nextInt(),false));
        }
        sc.close();
        //按照结束时间进行升序排序
        activityList.sort((a,b)->a.getEnd() - b.getEnd());
        //贪心求解并输出到文件
        PrintStream out = new PrintStream(new File("./TEST/output.txt"));
        out.println(solve(activityList));
        out.close();
    }

    public static int solve(List<Activity> activityList) {
        int max_finish;//记录当前会场内最迟的结束时间
        int sum = 0;
        for (int i = 0; i < activityList.size(); i++) { //对于每一个结束时间，选取相容的活动
            if (!activityList.get(i).arranged) {    //判断是否已经被安排
                ++sum;
                max_finish = activityList.get(i).getEnd();
                activityList.get(i).setArranged(true);
                for (int j = i + 1; j < activityList.size(); j++) { //比i结束时间早的活动不可能满足条件结束时间比i的开始时间小
                    if (!activityList.get(j).arranged) {//判断是否已经被安排
                        if (max_finish <= activityList.get(j).getStart()) {
                            max_finish = activityList.get(j).getEnd();
                            activityList.get(j).setArranged(true);
                        }
                    }
                }
            }
        }
        return sum;
    }
}
