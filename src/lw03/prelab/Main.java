package lw03.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    // Support dijalankan dari folder prelab maupun dari root repository
    static File locate(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            file = new File("src/lw03/prelab/" + fileName);
        }
        return file;
    }

    // Problem 1: Playlist menggunakan List
    static void problem1() {
        List<String> playlist = new ArrayList<>();

        try (Scanner scanner = new Scanner(locate("playlist.txt"))) {
            while (scanner.hasNext()) {
                String operation = scanner.next();

                if (operation.equals("ADD")) {
                    // Sisa baris adalah judul lagu (bisa mengandung spasi)
                    String song = scanner.nextLine().trim();
                    playlist.add(song);
                } else if (operation.equals("INSERT")) {
                    int index = scanner.nextInt();
                    String song = scanner.nextLine().trim();
                    playlist.add(index, song);
                } else if (operation.equals("REMOVE")) {
                    String song = scanner.nextLine().trim();
                    // remove() hanya menghapus kemunculan pertama,
                    // dan tidak melakukan apa-apa jika lagu tidak ada
                    playlist.remove(song);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File playlist.txt tidak ditemukan");
            return;
        }

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

    // Problem 2: Peserta workshop menggunakan Set
    static void problem2() {
        // LinkedHashSet menjaga urutan kemunculan pertama
        Set<String> participants = new LinkedHashSet<>();
        int duplicates = 0;

        try (Scanner scanner = new Scanner(locate("participants.txt"))) {
            while (scanner.hasNext()) {
                String name = scanner.next();

                if (participants.contains(name)) {
                    duplicates++;
                } else {
                    participants.add(name);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File participants.txt tidak ditemukan");
            return;
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());
        int number = 1;
        for (String name : participants) {
            System.out.println(number + ". " + name);
            number++;
        }
        System.out.println("Duplicate registrations: " + duplicates);
    }

    // Problem 3: Inventaris produk menggunakan Map
    static void problem3() {
        // LinkedHashMap menjaga urutan produk pertama kali muncul
        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;

        try (Scanner scanner = new Scanner(locate("inventory.txt"))) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                String product = scanner.next();
                int quantity = scanner.nextInt();

                if (type.equals("ADD")) {
                    if (inventory.containsKey(product)) {
                        inventory.put(product, inventory.get(product) + quantity);
                    } else {
                        inventory.put(product, quantity);
                    }
                } else if (type.equals("SELL")) {
                    if (inventory.containsKey(product) && inventory.get(product) >= quantity) {
                        inventory.put(product, inventory.get(product) - quantity);
                    } else {
                        failedSales++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File inventory.txt tidak ditemukan");
            return;
        }

        System.out.println("===== Problem 3 =====");
        for (String product : inventory.keySet()) {
            System.out.println(product + ": " + inventory.get(product));
        }
        System.out.println("Failed sales: " + failedSales);
    }

    public static void main(String[] args) {
        problem1();
        problem2();
        problem3();
    }
}