import java.util.Scanner;
import java.util.Arrays;

public class MyArrayStack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int inputTime = Integer.parseInt(scanner.nextLine());
        TheStack stack = new TheStack(inputTime);
        
        for (int i = 0; i < inputTime; i++) {
            String[] inputStr = scanner.nextLine().split(" ");
            
            if (inputStr[0].equals("push")) {
                stack.push(Integer.parseInt(inputStr[1]));
            } else if (inputStr[0].equals("pop")) {
                stack.pop();
            } else {
                stack.top();
            }
            
//             System.out.println(Arrays.toString(stack.myStack));
        }
    }
}

class TheStack {
    int[] myStack;
    int curIdx;
    
    public TheStack(int capacity) {
        this.myStack = new int[capacity];
        curIdx = 0;
    }
    
    public void push(int ele) {
        this.myStack[curIdx++] = ele;
    }
    
    public void top() {
        if (curIdx == 0) {
            System.out.println("error");
        } else {
            System.out.println(this.myStack[curIdx - 1]);
        }
    }
    
    public void pop() {
        if (curIdx == 0) {
            System.out.println("error");
        } else {
            System.out.println(this.myStack[--curIdx]);
        }
    }
}