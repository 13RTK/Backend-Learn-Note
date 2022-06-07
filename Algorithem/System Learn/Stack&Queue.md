# 一、两个栈实现队列

![Xnip2022-04-22_08-46-22](Stack:Queue/Xnip2022-04-22_08-46-22.jpg)



Code:

```java
class MyQueue {
    private final Stack<Integer> inStack;
    private final Stack<Integer> outStack;

    public MyQueue() {
        this.inStack = new Stack<>();
        this.outStack = new Stack<>();
    }
    
    public void push(int x) {
        inStack.push(x);
    }
    
    public int pop() {
        if (outStack.isEmpty()) {
            moveToOutStack();
        }

        return outStack.pop();
    }
    
    public int peek() {
        if (outStack.isEmpty()) {
            moveToOutStack();
        }

        return outStack.peek();
    }
    
    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    private void moveToOutStack() {
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }
}
```

<hr>








# 二、两个队列实现栈

![Xnip2022-06-06_08-07-41](Stack:Queue/Xnip2022-06-06_08-07-41.jpg)



Code:

```java
class MyStack {
    Queue<Integer> mainQueue;
    Queue<Integer> auxiliaryQueue;

    public MyStack() {
        mainQueue = new LinkedList<>();
        auxiliaryQueue = new LinkedList<>();
    }

    public void push(int x) {
        auxiliaryQueue.add(x);

        while (!mainQueue.isEmpty()) {
            auxiliaryQueue.add(mainQueue.remove());
        }

        Queue<Integer> temp = auxiliaryQueue;
        auxiliaryQueue = mainQueue;
        mainQueue = temp;
    }

    public int pop() {
        return mainQueue.remove();
    }

    public int top() {
        return mainQueue.element();
    }

    public boolean empty() {
        return mainQueue.isEmpty();
    }
}
```

<hr>









# 三、有效的括号(经典题目)

![Xnip2022-06-07_07-39-00](Stack:Queue/Xnip2022-06-07_07-39-00.jpg)



![Xnip2022-06-07_07-57-36](Stack:Queue/Xnip2022-06-07_07-57-36.jpg)

题意:

给你一个字符串，其由多种括号组成，请你判断该字符串中的括号是否有效(一一匹配)



思路:

- 该题目是栈相关的经典题目，思路就是用栈来存放每个括号，一旦栈顶括号与当前括号匹配(栈顶是其匹配的左括号)，则弹出栈顶元素，不匹配则将其入栈
- 最后，如果栈中没有元素，说明所有的括号都成对匹配完成，此时括号有效



复杂度:

- 我们遍历了一次输入字符串，所以时间复杂度为O(n)
- 我们创建了一个栈来记录每个括号，所以空间复杂度为O(n)















