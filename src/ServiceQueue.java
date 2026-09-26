/**
 * A custom linked-node queue (FIFO) used to manage student service requests
 * in the order they arrive.
 */
public class ServiceQueue {

    private static class Node {
        String studentId;
        String requestDescription;
        Node next;
        Node(String studentId, String requestDescription) {
            this.studentId = studentId;
            this.requestDescription = requestDescription;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    /** Adds a new service request to the back of the queue. */
    public void enqueue(String studentId, String requestDescription) {
        Node node = new Node(studentId, requestDescription);
        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    /** Removes and returns the request at the front, or null if empty. */
    public String dequeue() {
        if (isEmpty()) return null;
        Node old = front;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return "Student " + old.studentId + " -> " + old.requestDescription;
    }

    /** Displays all pending requests without removing them. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        System.out.println("Pending service requests (front to back):");
        Node cur = front;
        int pos = 1;
        while (cur != null) {
            System.out.println(pos++ + ". Student " + cur.studentId + " -> " + cur.requestDescription);
            cur = cur.next;
        }
    }
}