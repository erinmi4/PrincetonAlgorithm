import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0; // 记录读取的第i个单词
        String champion = ""; // 用于存储当前选中的单词
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word; // 选中当前单词为冠军
            }
        }
        System.out.println(champion);
    }
}
