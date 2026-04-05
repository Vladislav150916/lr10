package timus;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class timus_1203 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[][] doklads = new int[N][2];
        for (int i = 0; i < N; i++) {
            doklads[i][0] = sc.nextInt();
            doklads[i][1] = sc.nextInt();
        }

        Arrays.sort(doklads, Comparator.comparingInt(a -> a[1]));

        int count = 0;
        int lastEnd = 0;
        for (int[] report : doklads) {
            if (report[0] > lastEnd) {
                count++;
                lastEnd = report[1];
            }
        }
        System.out.println(count);
        sc.close();
    }
}
