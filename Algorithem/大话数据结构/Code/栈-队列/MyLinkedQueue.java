public class MyLinkedQueue {
    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();
        queue.enqueue(20);
        queue.enqueue(30);
        queue.dequeue();
        queue.dequeue();
    }
}

class LinkedQueue {
    
    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode() {
        }
    }

    ListNode front;
    ListNode rear;

    public LinkedQueue() {
        this.front = new ListNode();
        this.rear = this.front;
    }

    public void enqueue(int ele) {
        ListNode newNode = new ListNode(ele); 
    
        this.rear.next = newNode;
        this.rear = newNode;
    }

    public void dequeue() {
        if (this.front == this.rear) {
            return;
        }

        ListNode removeNode = this.front.next;
        this.front.next = this.front.next.next;

        System.out.println(removeNode.val);
    }
}
