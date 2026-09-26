/**
 * A binary search tree that organizes student records by Student ID,
 * allowing sorted display 
 */
public class StudentBST {

    private static class TreeNode {
        Student data;
        TreeNode left, right;
        TreeNode(Student data) { this.data = data; }
    }

    private TreeNode root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private TreeNode insertRec(TreeNode node, Student student) {
        if (node == null) return new TreeNode(student);
        int cmp = student.getStudentId().compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) node.left = insertRec(node.left, student);
        else if (cmp > 0) node.right = insertRec(node.right, student);
        // if cmp == 0 the ID already exists; caller should have checked for duplicates beforehand
        return node;
    }

    public Student search(String studentId) {
        TreeNode node = root;
        while (node != null) {
            int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
            if (cmp == 0) return node.data;
            node = (cmp < 0) ? node.left : node.right;
        }
        return null;
    }

    public boolean delete(String studentId) {
        int before = countNodes(root);
        root = deleteRec(root, studentId);
        return countNodes(root) < before;
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private TreeNode deleteRec(TreeNode node, String studentId) {
        if (node == null) return null;
        int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, studentId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            TreeNode successor = node.right;
            while (successor.left != null) successor = successor.left;
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    /** In-order traversal prints students sorted by Student ID. */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("No student records in the tree.");
            return;
        }
        System.out.println("Students sorted by Student ID:");
        System.out.println("ID         | Name                 | Programme       | Marks");
        System.out.println("---------------------------------------------------------------");
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.println(node.data);
        inOrderRec(node.right);
    }
}