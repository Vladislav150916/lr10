package timus;

import java.util.Scanner;

public class timus_1924 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        if (n % 4 == 0 || n % 4 == 3) {
            System.out.println("black");
        } else {
            System.out.println("grimy");
        }
        sc.close();
    }
}
