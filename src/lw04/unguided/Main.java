package lw04.unguided;

import java.io.File;
import java.util.Scanner;

public class Main {

    // sesuaikan kalau parcels.txt kamu taruh di tempat lain
    static final String FILE_PATH = "src/lw04/unguided/parcels.txt";

    // Bubble sort descending, menjalankan pass ke-from sampai pass ke-(to - 1)
    static void bubbleSort(int[] a, int from, int to) {
        int n = a.length;
        for (int i = from; i < to; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] < a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    // Binary search pada array yang terurut menurun, steps[0] menyimpan jumlah langkah
    static int binarySearch(int[] a, int target, int[] steps) {
        int low = 0;
        int high = a.length - 1;
        steps[0] = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            steps[0]++;
            if (a[mid] == target) {
                return mid;
            } else if (target > a[mid]) {
                high = mid - 1; // nilai lebih besar ada di kiri
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + (i < a.length - 1 ? " " : ""));
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File(FILE_PATH));

        int n = sc.nextInt();
        int[] parcels = new int[n];
        for (int i = 0; i < n; i++) {
            parcels[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[] targets = new int[m];
        for (int i = 0; i < m; i++) {
            targets[i] = sc.nextInt();
        }
        sc.close();

        // tahap 1: 2 pass pertama, lalu simpan salinan
        bubbleSort(parcels, 0, 2);
        int[] batteryDead = new int[n];
        for (int i = 0; i < n; i++) {
            batteryDead[i] = parcels[i];
        }

        // tahap 2: lanjutkan sampai total N - 1 pass
        bubbleSort(parcels, 2, n - 1);

        System.out.println("==== After 2 Passes ====");
        printArray(batteryDead);
        System.out.println("==== Fully Sorted ====");
        printArray(parcels);
        System.out.println("==== Search Results ====");

        int[] steps = new int[1];
        for (int i = 0; i < m; i++) {
            int idx = binarySearch(parcels, targets[i], steps);
            if (idx != -1) {
                System.out.println(targets[i] + ": FOUND at index " + idx + " (steps: " + steps[0] + ")");
            } else {
                System.out.println(targets[i] + ": NOT FOUND (steps: " + steps[0] + ")");
            }
        }
    }
}