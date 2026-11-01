package lw04.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // Support dijalankan dari folder prelab maupun dari root repository
    static File locate(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            file = new File("src/lw04/prelab/" + fileName);
        }
        return file;
    }

    // Insertion sort descending, berhenti tepat setelah "passes" kali pass
    static void insertionSort(int[] arr, int passes) {
        for (int i = 1; i <= passes && i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // Geser elemen di kiri yang lebih kecil dari key ke kanan
            while (j >= 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // Linear search karena array belum terurut sempurna.
    // Mengembalikan index target, atau -1 jika tidak ditemukan
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] branches;
        int[] targets;

        try (Scanner scanner = new Scanner(locate("branches.txt"))) {
            int n = scanner.nextInt();
            branches = new int[n];
            for (int i = 0; i < n; i++) {
                branches[i] = scanner.nextInt();
            }

            int m = scanner.nextInt();
            targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File branches.txt tidak ditemukan");
            return;
        }

        // Rid memakai insertion sort, dan berhenti setelah pass ke-3
        insertionSort(branches, 3);

        for (int i = 0; i < branches.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(branches[i]);
        }
        System.out.println();

        for (int i = 0; i < targets.length; i++) {
            int index = linearSearch(branches, targets[i]);
            if (index != -1) {
                System.out.println(targets[i] + ": FOUND at index " + index);
            } else {
                System.out.println(targets[i] + ": NOT FOUND");
            }
        }
    }
}