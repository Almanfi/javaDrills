package drills.ex04;

import java.util.Scanner;

public class Program {
    private static char[] codes = new char[10];
    private static int[] frequencies = new int[10];
    private static int len = 0;

    private static char[] finalCodes = new char[10];
    private static int[] finalFreqs = new int[10];
    private static int[] finalValues = new int[10];
    private static int finalLen = 0;
    private static final int MAX_LEN = 10;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line;

        line = sc.nextLine();
        sc.close();
        analyseInput(line);
        printGraph();
    }

    private static void printGraph() {
        if (finalLen == 0) {
            return;
        }
        int maxFreqIdx = 0;
        boolean flag = true;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < finalLen; j++) {
                if (finalValues[j] < finalValues[maxFreqIdx]) {
                    if (finalValues[j] >= 10 - (i + 1)) {
                        flag = true;
                        maxFreqIdx = j;
                    }
                    else {
                        flag = false;
                    }
                    break;
                }
                if (flag && finalValues[j] == finalValues[maxFreqIdx]) {
                    if (j == finalLen - 1) {
                        flag = false;
                    }
                    System.out.printf("%4d", finalFreqs[j]);
                }
                else {
                    System.out.printf("   #");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < finalLen; i++) {
            System.out.printf("%4c", finalCodes[i]);
        }
        System.out.println();
    }

    public static void analyseInput(String input) {
        reset();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int pos = findElem(c);
            if (pos == -1) {
                insertElem(c);
            }
            else {
                frequencies[pos]++;
            }
        }
        finalizeResults();
        normalizeValues();
    }

    private static void finalizeResults() {
        for (int i = 0; i < len; i++) {
            insertInOrder(codes[i], frequencies[i]);
        }
    }

    private static void normalizeValues() {
        int max = finalFreqs[0];
        if (max < 1)
            max = 1;
        for (int i = 0; i < finalLen; i++) {
            finalValues[i] = (10 * (finalFreqs[i])) / max;
        }
    }

    private static void insertInOrder(char c, int freq) {
        int i;
        for (i = 0; i < finalLen; i++) {
            if (freq > finalFreqs[i]
                || (freq == finalFreqs[i] && c < finalCodes[i])) {
                break;
            }
        }
        if (i == finalLen) {
            if (finalLen < MAX_LEN) {
                finalCodes[i] = c;
                finalFreqs[i] = freq;
                ++finalLen;
            }
            return;
        }
        if (finalLen < MAX_LEN)
            ++finalLen;
        shiftFinalArraysByOne(i);
        finalCodes[i] = c;
        finalFreqs[i] = freq;
    }

    private static void shiftFinalArraysByOne(int pos) {
        for (int i = finalLen - 1; i > pos; i--) {
            finalCodes[i] = finalCodes[i - 1];
            finalFreqs[i] = finalFreqs[i - 1];
        }
    }

    public static char [] getFinalCodes() {
        return finalCodes;
    }

    public static char [] getCodes() {
        return codes;
    }

    public static int [] getFinalFreqs() {
        return finalFreqs;
    }

    public static int getFinalLen() {
        return finalLen;
    }

    private static void resize() {
        char[] bigCodeArr = new char[len + 10];
        int[] bigFreqArr = new int[len + 10];
        System.arraycopy(codes, 0, bigCodeArr, 0, len);
        System.arraycopy(frequencies, 0, bigFreqArr, 0, len);
        codes = bigCodeArr;
        frequencies = bigFreqArr;
    }

    private static int insertElem(char c) {
        if (codes.length == len) {
            resize();
        }
        codes[len] = c;
        frequencies[len] = 1;
        return len++;
    }

    private static int findElem(char c) {
        for (int i = 0; i < len; i++) {
            if (c == codes[i]) {
                return i;
            }
        }
        return -1;
    }
    private static int findFreqAt(int pos) {
        if (pos >= len) {
            return -1;
        }
        return frequencies[pos];
    }

    public static int getFrequancy(char c) {
        int pos = findElem(c);
        if (pos == -1) {
            return -1;
        }
        return findFreqAt(pos);
    }

    public static void reset() {
        len = 0;
        finalLen = 0;
    }
}