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

    public static void main(String[] args) {
        Node linkedList = new Node(20, new Node(15, new Node(29)));

        System.out.println(Node.getElement(3, linkedList));
    }
}