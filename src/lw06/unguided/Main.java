package lw06.unguided;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner read = new Scanner(Main.class.getResourceAsStream("treenumbers.txt"));

        String[] values = read.nextLine().trim().split(" ");
        int target = read.nextInt();

        BinarySearchTree bst = new BinarySearchTree();
        for (int i = 0; i < values.length; i++) {
            bst.insert(Integer.parseInt(values[i]));
        }

        System.out.print("Preorder : ");
        bst.preOrder();
        System.out.print("Inorder : ");
        bst.inOrder();
        System.out.print("Postorder : ");
        bst.postOrder();

        System.out.println("Search " + target + " : " + bst.search(target));
        System.out.println("Nodes : " + bst.countNodes());
        System.out.println("Leaves : " + bst.countLeaves());

        read.close();
    }
}
