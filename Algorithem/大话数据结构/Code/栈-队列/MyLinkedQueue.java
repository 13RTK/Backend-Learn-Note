public class MyLinkedQueue {

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
        
    }
}
