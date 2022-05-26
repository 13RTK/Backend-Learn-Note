public class Node {
    int val;
    Node next;

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    public static int getElement(int index, Node head) throws ArrayIndexOutOfBoundsException {
        Node curNode = head;

        int curOrder = 1;
        while (curNode != null && curOrder < index) {
            curNode = curNode.next;
            curOrder++;
        }

        if (curNode == null || curOrder > index) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return curNode.val;
    }

    public static void insertElement(int index, int value, Node head) throws ArrayIndexOutOfBoundsException {
        Node curNode = head;
        int orderCount = 1;

        while (curNode != null && orderCount < index - 1) {
            curNode = curNode.next;
            orderCount++;
        }

        if (curNode == null || orderCount > index) {
            throw new ArrayIndexOutOfBoundsException();
        }

        Node insertNode = new Node(value);
        insertNode.next = curNode.next;
        curNode.next = insertNode;
    }

    public static void printAllNodes(Node head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }

        System.out.println();
    }

    public static void deleteElement(int index, Node head) throws ArrayIndexOutOfBoundsException{
        Node curNode = head;
        int orderCount = 1;

        while (curNode != null && orderCount < index) {
            curNode = curNode.next;
            orderCount++;
        }

        if (curNode.next == null || orderCount > index) {
            throw new ArrayIndexOutOfBoundsException();
        }

        curNode.next = curNode.next.next;
    }

    public static void main(String[] args) {
        Node linkedList = new Node(20, new Node(15, new Node(29)));

        Node.printAllNodes(linkedList);

        Node.deleteElement(2, linkedList);

        Node.printAllNodes(linkedList);
    }
}