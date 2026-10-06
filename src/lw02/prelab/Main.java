package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // Support dijalankan dari folder prelab maupun dari root repository
        File file = new File("transactions.txt");
        if (!file.exists()) {
            file = new File("src/lw02/prelab/transactions.txt");
        }

        // 1. LinkedList untuk semua transaksi: {name, type, amount}
        LinkedList<String[]> transactions = new LinkedList<>();

        // 2. LinkedList untuk data customer: {name, balance}
        LinkedList<String[]> customers = new LinkedList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String type = scanner.next();
                String amount = scanner.next();

                transactions.add(new String[] { name, type, amount });

                // Tambahkan customer hanya saat pertama kali muncul
                boolean found = false;
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i)[0].equals(name)) {
                        found = true;
                    }
                }
                if (!found) {
                    customers.add(new String[] { name, "0" });
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File transactions.txt tidak ditemukan");
            return;
        }

        // 3. Pindahkan semua transaksi ke Queue (FIFO)
        Queue<String[]> queue = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            queue.add(transactions.get(i));
        }

        // 4. Stack untuk withdraw yang gagal (LIFO)
        Stack<String[]> failed = new Stack<>();

        // Proses queue sampai habis
        while (!queue.isEmpty()) {
            String[] trx = queue.poll();
            String name = trx[0];
            String type = trx[1];
            int amount = Integer.parseInt(trx[2]);

            // Cari customer yang bersangkutan
            String[] customer = null;
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i)[0].equals(name)) {
                    customer = customers.get(i);
                }
            }

            int balance = Integer.parseInt(customer[1]);

            if (type.equals("DEPOSIT")) {
                balance = balance + amount;
                customer[1] = String.valueOf(balance);
            } else if (type.equals("WITHDRAW")) {
                if (amount > balance) {
                    failed.push(trx); // saldo tidak cukup
                } else {
                    balance = balance - amount;
                    customer[1] = String.valueOf(balance);
                }
            }
        }

        // 5. Tampilkan hasil
        System.out.println("=== Final Balances ===");
        for (int i = 0; i < customers.size(); i++) {
            String[] c = customers.get(i);
            System.out.println(c[0] + " : " + c[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failed.isEmpty()) {
            String[] t = failed.pop();
            System.out.println(t[0] + " " + t[1] + " " + t[2]);
        }
    }
}