public class MyArrayQueue {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(4);

        queue.push(29);
        queue.pop();
    } 
}

class ArrayQueue {
    int[] queueArray;
    int rear;
    int front;
    int capacity;

    public ArrayQueue(int capacity) {
        this.queueArray = new int[capacity];
        this.capacity = capacity;
        this.front = 0;
        this.rear = 0;
    }

    public void push(int ele) {
        if ((this.rear + 1) % this.capacity == this.front) {
            return;
        }

        this.queueArray[rear] = ele;
        this.rear = (this.rear + 1) % this.capacity;
    }

    public void pop() {
        if (this.front == this.rear) {
            System.out.println("error");
            return;
        }

        int removeEle = this.queueArray[front];
        this.front = (this.front + 1) % this.capacity;

        System.out.println(removeEle);
    }
    
    public void front() {
        if (this.front == this.rear) {
            System.out.println("error");
            return;
        }
        
        System.out.println(this.queueArray[front]);
    }
}