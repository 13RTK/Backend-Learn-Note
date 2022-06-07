public class MyLinkedStack {
    public static void main(String[] args) {
        LinkedStack stack = new LinkedStack();
        stack.push(10);

        stack.pop();
    } 
}

class LinkedStack {
    class ListNode {
        int val;
        ListNode next;
        
        public ListNode() {
            
        }
        
        public ListNode(int val) {
            this.val = val;
        }
    }
    
    ListNode preHead;
    public LinkedStack() {
        this.preHead = new ListNode();
    }
    
    public void push(int ele) {
        ListNode newNode = new ListNode(ele);
        
        newNode.next = this.preHead.next;
        this.preHead.next = newNode;
    }
    
    public void top() {
        if (this.preHead.next == null) {
            System.out.println("error");
        } else {
            System.out.println(this.preHead.next.val);
        }
    }
    
    public void pop() {
        if (this.preHead.next == null) {
            System.out.println("error");
        } else {
            int res = preHead.next.val;
            this.preHead.next = this.preHead.next.next;

            System.out.println(res);
        }
    }
}