/**
 * A custom hash table (separate chaining) that maps Student ID -> Student,
 */
public class HashTable {

    private static class Entry {
        String key;
        Student value;
        Entry next;
        Entry(String key, Student value) { this.key = key; this.value = value; }
    }

    private Entry[] buckets;
    private int capacity;
    private int count;

    public HashTable() {
        capacity = 16;
        buckets = new Entry[capacity];
    }

    private int hash(String key) {
        int h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = 31 * h + key.charAt(i);
        }
        return Math.abs(h) % capacity;
    }

    public void put(String studentId, Student student) {
        if (count >= capacity * 0.75) resize();
        int idx = hash(studentId);
        Entry cur = buckets[idx];
        while (cur != null) {
            if (cur.key.equalsIgnoreCase(studentId)) {
                cur.value = student; // update existing
                return;
            }
            cur = cur.next;
        }
        Entry node = new Entry(studentId, student);
        node.next = buckets[idx];
        buckets[idx] = node;
        count++;
    }

    public Student get(String studentId) {
        int idx = hash(studentId);
        Entry cur = buckets[idx];
        while (cur != null) {
            if (cur.key.equalsIgnoreCase(studentId)) return cur.value;
            cur = cur.next;
        }
        return null;
    }

    public boolean remove(String studentId) {
        int idx = hash(studentId);
        Entry cur = buckets[idx], prev = null;
        while (cur != null) {
            if (cur.key.equalsIgnoreCase(studentId)) {
                if (prev == null) buckets[idx] = cur.next;
                else prev.next = cur.next;
                count--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    private void resize() {
        Entry[] old = buckets;
        capacity *= 2;
        buckets = new Entry[capacity];
        count = 0;
        for (Entry head : old) {
            Entry cur = head;
            while (cur != null) {
                put(cur.key, cur.value);
                cur = cur.next;
            }
        }
    }
}