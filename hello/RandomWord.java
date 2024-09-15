import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int count = 0;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            count++;
            if (StdRandom.bernoulli(1.0 / count)) { // 以1/i的概率选择当前单词为冠军
                champion = word;
            }
        }

        if (champion != null) {
            StdOut.println(champion);
        }
        else {
            StdOut.println("No words were read from standard input.");
        }
    }
}