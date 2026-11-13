package lw05.prelab;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main {

    static final String FILE_PATH = "src/lw05/prelab/campus.txt";

    static int size;
    static String[] names;
    static int[][] graph;
    static ArrayList<Integer> dfsOrder = new ArrayList<>();

    static void addEdge(int a, int b, int w) {
        graph[a][b] = w;
        graph[b][a] = w;
    }

    static void printPath(ArrayList<Integer> order) {
        for (int i = 0; i < order.size(); i++) {
            System.out.print(names[order.get(i)]);
            if (i < order.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    static ArrayList<Integer> bfs(int start) {
        ArrayList<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);
            for (int next = 0; next < size; next++) {
                if (graph[current][next] != 0 && !visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
        return order;
    }

    static void dfs(int current, Set<Integer> visited) {
        visited.add(current);
        dfsOrder.add(current);
        for (int next = 0; next < size; next++) {
            if (graph[current][next] != 0 && !visited.contains(next)) {
                dfs(next, visited);
            }
        }
    }

    static int findTheNextNode(int[] dist, boolean[] visited) {
        int best = -1;
        for (int i = 0; i < size; i++) {
            if (!visited[i] && (best == -1 || dist[i] < dist[best])) {
                best = i;
            }
        }
        return best;
    }

    static void route(int[] prev, int node) {
        if (prev[node] != -1) {
            route(prev, prev[node]);
            System.out.print(" -> ");
        }
        System.out.print(names[node]);
    }

    static void dijkstraAlg(int start, int dest) {
        int[] dist = new int[size];
        int[] prev = new int[size];
        boolean[] visited = new boolean[size];

        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        dist[start] = 0;

        for (int count = 0; count < size; count++) {
            int u = findTheNextNode(dist, visited);
            visited[u] = true;

            for (int v = 0; v < size; v++) {
                if (graph[u][v] != 0 && !visited[v] && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
        }

        System.out.println("=== Shortest Time from " + names[start] + " ===");
        for (int i = 0; i < size; i++) {
            System.out.println(names[i] + " : " + dist[i] + " min");
        }

        System.out.println("=== Shortest Route from " + names[start] + " to " + names[dest] + " ===");
        route(prev, dest);
        System.out.println();
        System.out.println("Total time: " + dist[dest] + " min");
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File(FILE_PATH));

        size = sc.nextInt();
        names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = sc.next();
        }
        graph = new int[size][size];

        int e = sc.nextInt();
        for (int i = 0; i < e; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int w = sc.nextInt();
            addEdge(a, b, w);
        }

        int start = sc.nextInt();
        int dest = sc.nextInt();
        sc.close();

        System.out.println("=== BFS from " + names[start] + " ===");
        printPath(bfs(start));

        System.out.println("=== DFS from " + names[start] + " ===");
        dfs(start, new HashSet<>());
        printPath(dfsOrder);

        dijkstraAlg(start, dest);
    }
}