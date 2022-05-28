public class MyLinkedStack {
    public static void main(String[] args) {
        
    } 
}

class MyLinkedStack {
    class ListNode {
        int val;
        ListNode next;
        ListNode pre;
        
        public ListNode() {
            
        }
        
        public ListNode(int val) {
            this.val = val;
        }
    }
    
    ListNode preHead;
    ListNode tailNext;
    public MyLinkedStack() {
        this.preHead = new ListNode();
        this.tailNext = new ListNode();
        
        preHead.next = tailNext;
        tailNext.pre = preHead;
    }
    
    public void push(int ele) {
        ListNode newNode = new ListNode(ele);
        tailNext.pre.next = newNode;
        newNode.next = tailNext;
        tailNext.pre = newNode;
    }
    
    public void top() {
        if (this.preHead.next == this.tailNext) {
            System.out.println("error");
        } else {
            System.out.println(this.preHead.next.val);
        }
    }
    
    public void pop() {
        if (this.preHead.next == this.tailNext) {
            System.out.println("error");
        } else {
            System.out.println(this.preHead.next.val);
            this.preHead.next = this.preHead.next.next;
        }
    }
}