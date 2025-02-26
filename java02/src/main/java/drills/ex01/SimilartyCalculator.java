package drills.ex01;

import java.util.Map;
import java.util.function.Consumer;
import java.io.BufferedReader;
import java.io.FileReader;

public class SimilartyCalculator {
    Map<String, Vect2> wordMap;

    class Vect2 {
        int v1 = 0;
        int v2 = 0;

        public Vect2(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }
    
    public SimilartyCalculator() {
        wordMap = new java.util.HashMap<>();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: similartyCalculator file1 file2");
            System.exit(2);
        }
        SimilartyCalculator sc = new SimilartyCalculator();
        sc.treatFile1(args[0]);
        sc.treatFile2(args[1]);
        double similarity = sc.calculateSimilarity();
        System.out.printf("Similarity = %.2f", similarity);
    }

    void readFile(String fileName, Consumer<String> processor) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(fileName))) {
            char[] buffer = new char[1024];
            int start = 0;
            String content = "";

            while (true) {
                if ( br.read(buffer) == -1) {
                    break;
                }
                content = content + new String(buffer);
                while (true) {
                    while (start < content.length() && content.charAt(start) == ' ') {
                        start++;
                    }
                    int end = content.indexOf(" ", start + 1);
                    if (end == -1) {
                        break;
                    }
                    String word = content.substring(start, end);
                    processor.accept(word);
                    start = end + 1;
                }
                content = content.substring(start);
                start = 0;
            }
            String leftOver = content.trim();
            if (leftOver.length() > 0) {
                processor.accept(leftOver);
            }
        }
        catch (Exception e) {
            System.out.println("Error: readFile - " + e.getMessage());
            System.exit(1);
        }
    }

    void invrimentV1OfWord(String word) {
        if (!wordMap.containsKey(word)) {
            wordMap.put(word, new Vect2(1, 0));
            return;
        }
        Vect2 v = wordMap.get(word);
        v.v1++;
        wordMap.put(word, v);
    }

    void invrimentV2OfWord(String word) {
        if (!wordMap.containsKey(word)) {
            wordMap.put(word, new Vect2(0, 1));
            return;
        }
        Vect2 v = wordMap.get(word);
        v.v2++;
        wordMap.put(word, v);
    }

    void treatFile1(String fileName) {
        readFile(fileName, this::invrimentV1OfWord);
    }

    void treatFile2(String fileName) {
        readFile(fileName, this::invrimentV2OfWord);
    }

    double calculateSimilarity() {
        double numerator = 0;
        double sum1 = 0;
        double sum2 = 0;
        for (Vect2 v : wordMap.values()) {
            numerator += v.v1 * v.v2;
            sum1 += v.v1 * v.v1;
            sum2 += v.v2 * v.v2;
        }
        double denominator = Math.sqrt(sum1) * Math.sqrt(sum2);
        if (denominator == 0) {
            return 0.0;
        }
        return numerator / denominator;
    }

}
