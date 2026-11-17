package lw05.unguided;

import java.io.File;
import java.util.*;

public class Main {

    static int[][] graph;
    static int size;
    static String[] names;

    static int[] bfsLevel;
    static int[] bfsPrev;
    static int[] dijkstraPrev;

    // Adds a two-way track (complete, do not change)
    static void addEdge(int from, int to, int minutes) {
        graph[from][to] = minutes;
        graph[to][from] = minutes;
    }

    // BFS: finds the route with the fewest stops
    static List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        bfsLevel = new int[size];
        bfsPrev = new int[size];

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll(); // take the station at the front of the queue
            order.add(current);

            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0 && !visited.contains(next)) { // next is a neighbour that has not been visited
                    visited.add(next);
                    bfsLevel[next] = bfsLevel[current] + 1; // set the level of next
                    bfsPrev[next] = current; // remember the previous station
                    queue.add(next); // put next at the back of the queue
                }
            }
        }

        return order;
    }

    // Prints stations level by level
    static void printLevels(List<Integer> order) {
        int lastLevel = bfsLevel[order.get(order.size() - 1)];

        for (int level = 0; level <= lastLevel; level++) {
            System.out.println();
            System.out.print("Level " + level + ": ");

            boolean first = true;

            for (int i = 0; i < order.size(); i++) {
                int station = order.get(i);

                if (bfsLevel[station] == level) { // check whether the station belongs to this level
                    if (!first) {
                        System.out.print(", ");
                    }

                    System.out.print(names[station]);
                    first = false;
                }
            }
        }

        System.out.println();
    }

    // Dijkstra: finds the fastest route
    static void dijkstraAlg(int start) {
        int[] dist = new int[size];

        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;

        boolean[] visited = new boolean[size];
        dijkstraPrev = new int[size];

        for (int i = 0; i < size; i++) {
            int current = findTheNextNode(visited, dist);

            if (current == -1) {
                break;
            }

            visited[current] = true; // mark current as visited

            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0
                        && !visited[next]) {

                    int newDist = dist[current] + graph[current][next];

                    if (newDist < dist[next]) { // check whether the new distance is shorter
                        dist[next] = newDist;
                        dijkstraPrev[next] = current; // remember the previous station
                    }
                }
            }
        }
    }

    // Finds the unvisited station with the smallest distance
    static int findTheNextNode(boolean[] visited, int[] dist) {
        int min = Integer.MAX_VALUE;
        int closest = -1;

        for (int i = 0; i < size; i++) {
            if (!visited[i] && dist[i] < min) { // i is unvisited and closer than min
                min = dist[i];
                closest = i;
            }
        }

        return closest;
    }

    // Rebuilds the route using prev[]
    static List<Integer> buildPath(int current, int[] prev, int start) {
        List<Integer> path;

        if (current == start) {
            path = new ArrayList<>();
        } else {
            path = buildPath(prev[current], prev, start); // go back to the previous station
        }

        path.add(current);
        return path;
    }

    // Calculates the total travel time
    static int pathTime(List<Integer> path) {
        int total = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            total += graph[path.get(i)][path.get(i + 1)]; // add the travel time between two consecutive stations
        }

        return total;
    }

    // Prints the route (complete, do not change)
    static void printPath(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(names[path.get(i)]);

            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    // Prints route information (complete, do not change)
    static void printSummary(String title, List<Integer> path) {
        System.out.println("=== " + title + " ===");
        printPath(path);

        System.out.println("Stops: " + (path.size() - 1)
                + " | Time: " + pathTime(path) + " min");
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("src/lw05/unguided/metro.txt"));

        size = sc.nextInt();
        names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = sc.next();
        }
        graph = new int[size][size];

        int e = sc.nextInt();
        for (int i = 0; i < e; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int minutes = sc.nextInt();
            addEdge(from, to, minutes);
        }

        int start = sc.nextInt();
        int dest = sc.nextInt();
        sc.close();

        // printLevels() diawali println(), jadi judul dicetak tanpa newline
        List<Integer> order = bfs(start);
        System.out.print("=== Station Levels from " + names[start] + " ===");
        printLevels(order);

        List<Integer> fewest = buildPath(dest, bfsPrev, start);
        printSummary("Fewest Stops Route from " + names[start] + " to " + names[dest], fewest);

        dijkstraAlg(start);
        List<Integer> fastest = buildPath(dest, dijkstraPrev, start);
        printSummary("Fastest Route from " + names[start] + " to " + names[dest], fastest);

        System.out.println("=== Comparison ===");
        if (fewest.equals(fastest)) {
            System.out.println("Both routes are the same.");
        } else {
            int saved = pathTime(fewest) - pathTime(fastest);
            int extraStops = fastest.size() - fewest.size();
            System.out.println("The fastest route saves " + saved + " min but needs " + extraStops + " more stops.");
        }
    }
}