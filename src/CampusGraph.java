import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CampusGraph {

    // adjacency list: location name -> list of directly connected locations
    private final Map<String, List<String>> adjacencyList = new LinkedHashMap<>();

    public boolean addLocation(String location) {
        if (adjacencyList.containsKey(location)) return false; // duplicate
        adjacencyList.put(location, new ArrayList<>());
        return true;
    }

    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    /** Removes a location and any connections referring to it. */
    public boolean removeLocation(String location) {
        if (!adjacencyList.containsKey(location)) return false;
        adjacencyList.remove(location);
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(location);
        }
        return true;
    }

    /** Adds an undirected connection/road between two existing locations. */
    public boolean addConnection(String a, String b) {
        if (!adjacencyList.containsKey(a) || !adjacencyList.containsKey(b)) return false;
        if (adjacencyList.get(a).contains(b)) return false; // already connected
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
        return true;
    }

    public boolean removeConnection(String a, String b) {
        if (!adjacencyList.containsKey(a) || !adjacencyList.containsKey(b)) return false;
        boolean removedA = adjacencyList.get(a).remove(b);
        boolean removedB = adjacencyList.get(b).remove(a);
        return removedA || removedB;
    }

    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations have been added yet.");
            return;
        }
        System.out.println("Campus network (adjacency list):");
        for (Map.Entry<String, List<String>> e : adjacencyList.entrySet()) {
            String neighbours = e.getValue().isEmpty() ? "(no connections)" : String.join(", ", e.getValue());
            System.out.println("  " + e.getKey() + " -> " + neighbours);
        }
    }

    /** Breadth-first traversal starting from the given location. */
    public List<String> bfs(String start) {
        List<String> visitedOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) return visitedOrder;

        Set<String> visited = new java.util.LinkedHashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitedOrder.add(current);
            for (String neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return visitedOrder;
    }

    /** Depth-first traversal starting from the given location. */
    public List<String> dfs(String start) {
        List<String> visitedOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) return visitedOrder;
        Set<String> visited = new java.util.LinkedHashSet<>();
        dfsRec(start, visited, visitedOrder);
        return visitedOrder;
    }

    private void dfsRec(String current, Set<String> visited, List<String> visitedOrder) {
        visited.add(current);
        visitedOrder.add(current);
        for (String neighbour : adjacencyList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsRec(neighbour, visited, visitedOrder);
            }
        }
    }

    public Set<String> getLocations() {
        return adjacencyList.keySet();
    }
}