public class ActionStack {

    private String[] items;
    private int top;       // index of the next free slot
    private int capacity;

    public ActionStack() {
        capacity = 10;
        items = new String[capacity];
        top = 0;
    }

    public boolean isEmpty() { return top == 0; }

    /** Pushes a new action description onto the stack, growing the array if needed. */
    public void push(String action) {
        if (top == capacity) {
            grow();
        }
        items[top++] = action;
    }

    private void grow() {
        capacity *= 2;
        String[] bigger = new String[capacity];
        System.arraycopy(items, 0, bigger, 0, items.length);
        items = bigger;
    }

    /** Removes and returns the most recent action, or null if the stack is empty. */
    public String pop() {
        if (isEmpty()) return null;
        String value = items[--top];
        items[top] = null;
        return value;
    }

    /** Looks at the most recent action without removing it. */
    public String peek() {
        if (isEmpty()) return null;
        return items[top - 1];
    }

    /** Displays all recorded actions, most recent first. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("Recent actions (most recent first):");
        for (int i = top - 1; i >= 0; i--) {
            System.out.println((top - i) + ". " + items[i]);
        }
    }
}