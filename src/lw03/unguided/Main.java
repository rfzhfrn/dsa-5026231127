package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    // Support dijalankan dari folder unguided maupun dari root repository
    static File locate(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            file = new File("src/lw03/unguided/" + fileName);
        }
        return file;
    }

    public static void main(String[] args) {
        // Set: ID terdaftar (otomatis unik, duplikat di file dianggap satu)
        Set<String> registered = new HashSet<>();

        // Set: ID yang sudah berhasil check-in (satu mahasiswa hanya sekali)
        Set<String> checkedIn = new HashSet<>();

        // List: hasil tiap check-in sesuai urutan scan
        List<String> results = new ArrayList<>();

        int rejected = 0;

        // 1. Baca data registrasi
        try (Scanner scanner = new Scanner(locate("registrations.txt"))) {
            while (scanner.hasNext()) {
                registered.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File registrations.txt tidak ditemukan");
            return;
        }

        // 2. Baca dan validasi check-in sesuai urutan
        try (Scanner scanner = new Scanner(locate("checkins.txt"))) {
            while (scanner.hasNext()) {
                String id = scanner.next();

                if (!registered.contains(id)) {
                    results.add(id + ": Rejected (not registered)");
                    rejected++;
                } else if (checkedIn.contains(id)) {
                    results.add(id + ": Rejected (already checked in)");
                    rejected++;
                } else {
                    checkedIn.add(id);
                    results.add(id + ": Checked in");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File checkins.txt tidak ditemukan");
            return;
        }

        // 3. Tampilkan hasil
        System.out.println("===== Event Check-In Results =====");
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }

        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + registered.size());
        System.out.println("Successful check-ins: " + checkedIn.size());
        System.out.println("Absent students: " + (registered.size() - checkedIn.size()));
        System.out.println("Rejected attempts: " + rejected);
    }
}