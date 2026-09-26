import java.util.List;
import java.util.Scanner;

/**
 * CIT300 Graded Practical Assignment 1
 * University Student Record and Campus Route Management System
 *
 * Menu-driven console entry point tying together:
 *  - StudentLinkedList (linear storage of student records)
 *  - ActionStack        (recent actions / history)
 *  - ServiceQueue        (service requests, FIFO)
 *  - StudentBST          (records organised/searched by Student ID)
 *  - HashTable           (fast Student ID lookup)
 *  - CampusGraph         (campus locations + connections, BFS/DFS)
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentLinkedList studentList = new StudentLinkedList();
    private static final ActionStack actionHistory = new ActionStack();
    private static final ServiceQueue serviceQueue = new ServiceQueue();
    private static final StudentBST studentTree = new StudentBST();
    private static final HashTable studentHash = new HashTable();
    private static final CampusGraph campus = new CampusGraph();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: studentList.displayAll(); break;
                case 5: addServiceRequest(); break;
                case 6: processNextRequest(); break;
                case 7: actionHistory.displayAll(); break;
                case 8: studentTree.displayInOrder(); break;
                case 9: searchStudentByHash(); break;
                case 10: addLocation(); break;
                case 11: removeLocation(); break;
                case 12: addConnection(); break;
                case 13: removeConnection(); break;
                case 14: campus.displayConnections(); break;
                case 15: traverseCampus(); break;
                case 16:
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 16.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== University Student Record & Campus Route Management System =====");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
    }

    // ---------- Student record operations ----------

    private static void addStudent() {
        String id = readNonEmpty("Enter Student ID: ");
        if (studentList.contains(id)) {
            System.out.println("Error: Student ID '" + id + "' already exists.");
            return;
        }
        String name = readNonEmpty("Enter Name: ");
        String programme = readNonEmpty("Enter Programme: ");
        double marks = readMarks("Enter Marks (0-100): ");

        Student student = new Student(id, name, programme, marks);
        studentList.add(student);
        studentTree.insert(student);
        studentHash.put(id, student);
        actionHistory.push("ADDED student " + id + " (" + name + ")");
        System.out.println("Student added successfully.");
    }

    private static void updateStudent() {
        String id = readNonEmpty("Enter Student ID to update: ");
        if (!studentList.contains(id)) {
            System.out.println("Error: No student found with ID '" + id + "'.");
            return;
        }
        String name = readNonEmpty("Enter new Name: ");
        String programme = readNonEmpty("Enter new Programme: ");
        double marks = readMarks("Enter new Marks (0-100): ");

        studentList.update(id, name, programme, marks);
        Student updated = studentList.find(id);
        studentHash.put(id, updated);
        // The BST node's key (Student ID) does not change on update, only the
        // marks/name/programme fields, so no re-insertion is needed: the Student
        // object referenced by the tree node is the same one just updated above
        // only if we update in place. To keep things simple and safe, remove and
        // re-insert the record so the tree always reflects the latest data.
        studentTree.delete(id);
        studentTree.insert(updated);
        actionHistory.push("UPDATED student " + id);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        String id = readNonEmpty("Enter Student ID to delete: ");
        Student removed = studentList.delete(id);
        if (removed == null) {
            System.out.println("Error: No student found with ID '" + id + "'.");
            return;
        }
        studentTree.delete(id);
        studentHash.remove(id);
        actionHistory.push("DELETED student " + id + " (" + removed.getName() + ")");
        System.out.println("Student deleted successfully.");
    }

    private static void searchStudentByHash() {
        String id = readNonEmpty("Enter Student ID to search: ");
        Student found = studentHash.get(id);
        if (found == null) {
            System.out.println("No student found with ID '" + id + "'.");
        } else {
            System.out.println("Found: " + found);
        }
    }

    // ---------- Service queue operations ----------

    private static void addServiceRequest() {
        String id = readNonEmpty("Enter Student ID making the request: ");
        String description = readNonEmpty("Enter request description: ");
        serviceQueue.enqueue(id, description);
        actionHistory.push("QUEUED service request for " + id);
        System.out.println("Service request added to the queue.");
    }

    private static void processNextRequest() {
        String result = serviceQueue.dequeue();
        if (result == null) {
            System.out.println("No pending service requests.");
        } else {
            System.out.println("Processed: " + result);
            actionHistory.push("PROCESSED service request: " + result);
        }
    }

    // ---------- Campus graph operations ----------

    private static void addLocation() {
        String location = readNonEmpty("Enter new campus location name: ");
        if (campus.addLocation(location)) {
            actionHistory.push("ADDED campus location " + location);
            System.out.println("Location added.");
        } else {
            System.out.println("Error: Location '" + location + "' already exists.");
        }
    }

    private static void removeLocation() {
        String location = readNonEmpty("Enter campus location to remove: ");
        if (campus.removeLocation(location)) {
            actionHistory.push("REMOVED campus location " + location);
            System.out.println("Location removed.");
        } else {
            System.out.println("Error: Location '" + location + "' does not exist.");
        }
    }

    private static void addConnection() {
        String a = readNonEmpty("Enter first location: ");
        String b = readNonEmpty("Enter second location: ");
        if (!campus.hasLocation(a) || !campus.hasLocation(b)) {
            System.out.println("Error: Both locations must exist before connecting them.");
            return;
        }
        if (campus.addConnection(a, b)) {
            actionHistory.push("CONNECTED " + a + " <-> " + b);
            System.out.println("Connection added.");
        } else {
            System.out.println("Error: Connection already exists between these locations.");
        }
    }

    private static void removeConnection() {
        String a = readNonEmpty("Enter first location: ");
        String b = readNonEmpty("Enter second location: ");
        if (campus.removeConnection(a, b)) {
            actionHistory.push("DISCONNECTED " + a + " <-> " + b);
            System.out.println("Connection removed.");
        } else {
            System.out.println("Error: No such connection exists (or location unavailable).");
        }
    }

    private static void traverseCampus() {
        if (campus.getLocations().isEmpty()) {
            System.out.println("No campus locations available to traverse.");
            return;
        }
        String start = readNonEmpty("Enter starting location: ");
        if (!campus.hasLocation(start)) {
            System.out.println("Error: Location '" + start + "' does not exist.");
            return;
        }
        String mode = readNonEmpty("Traverse using (BFS/DFS): ").trim().toUpperCase();
        List<String> order;
        if (mode.equals("BFS")) {
            order = campus.bfs(start);
        } else if (mode.equals("DFS")) {
            order = campus.dfs(start);
        } else {
            System.out.println("Invalid traversal type. Please enter BFS or DFS.");
            return;
        }
        System.out.println(mode + " order from '" + start + "': " + String.join(" -> ", order));
    }

    // ---------- Input helpers with validation ----------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double readMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double marks = Double.parseDouble(line);
                if (marks < 0 || marks > 100) {
                    System.out.println("Invalid marks. Please enter a value between 0 and 100.");
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("This field cannot be empty. Please try again.");
        }
    }
}