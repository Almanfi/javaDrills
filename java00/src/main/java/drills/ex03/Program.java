package drills.ex03;

import java.util.Scanner;

public class Program {
    private static int expectedWeek = 1;
    private static final String WEEK = "Week";
    private static final String EOF = "42";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line;
        String output = "";
        int minOfTheWeek;

        try {
            while (sc.hasNextLine())
            {
                line = sc.nextLine();
                if (line.equals(EOF)) {
                    break;
                }
                checkWeek(line);
                expectedWeek++;
                minOfTheWeek = findMinOfScores(sc.nextLine());
                output += line + " " + createGraph(minOfTheWeek) + "\n";
            }
            System.out.print(output);
        }
        catch (IllegalArgumentException e) {
            sc.close();
            System.err.println("IllegalArgument: " + e.getMessage());
            System.exit(-1);
        }
        sc.close();
    }

    private static void checkWeek(String WeekLine) throws
                            IllegalArgumentException {
        if (!WeekLine.equals(WEEK + " " + expectedWeek)) {
            throw new IllegalArgumentException("this is not the expected week");
        }
    }

    private static String createGraph(int size) {
        String graph = "=";
        graph = graph.repeat(size);
        graph += ">";
        return graph;
    }

    public static int findMinOfScores(String scores) throws
                                    IllegalArgumentException {
        boolean hasSpaceBefore = true;
        int scoresCount = 0;
        char min = '9';
        for (int i = 0; i < scores.length();i++) {
            char c = scores.charAt(i);
            if (c < '0' || c > '9') {
                if (!Character.isWhitespace(c)) {
                    throw new IllegalArgumentException(
                        "seperated by non space");
                }
                hasSpaceBefore = true;
                continue;
            }
            scoresCount++;
            if (hasSpaceBefore == false || scoresCount > 5) {
                throw new IllegalArgumentException(
                    "the scores are not single digits");
            }
            hasSpaceBefore = false;
            if (c < min) {
                min = c;
            }
        }
        if (scoresCount != 5) {
            throw new IllegalArgumentException(
                    "the scores are not five");
        }
        return (min - '0');
    }
}