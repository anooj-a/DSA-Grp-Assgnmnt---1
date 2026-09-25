
/**
 * A custom singly linked list used as the primary storage structure for student records. 
 */
public class StudentLinkedList {

    private static class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    private Node head;
    private int size;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    /** Adds a new student to the end of the list. Returns false if the ID already exists. */
    public boolean add(Student student) {
        if (contains(student.getStudentId())) {
            return false;
        }
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = newNode;
        }
        size++;
        return true;
    }

    /** Finds a student by ID. Returns null if not found. */
    public Student find(String studentId) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.getStudentId().equalsIgnoreCase(studentId)) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    public boolean contains(String studentId) {
        return find(studentId) != null;
    }

    /** Updates an existing student's mutable fields. Returns false if not found. */
    public boolean update(String studentId, String name, String programme, double marks) {
        Student s = find(studentId);
        if (s == null) return false;
        s.setName(name);
        s.setProgramme(programme);
        s.setMarks(marks);
        return true;
    }

    /** Removes a student by ID and returns the removed record, or null if not found. */
    public Student delete(String studentId) {
        Node cur = head, prev = null;
        while (cur != null) {
            if (cur.data.getStudentId().equalsIgnoreCase(studentId)) {
                if (prev == null) head = cur.next;
                else prev.next = cur.next;
                size--;
                return cur.data;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    /** Prints every record currently stored, in insertion order. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("ID         | Name                 | Programme       | Marks");
        System.out.println("---------------------------------------------------------------");
        Node cur = head;
        while (cur != null) {
            System.out.println(cur.data);
            cur = cur.next;
        }
    }
}
