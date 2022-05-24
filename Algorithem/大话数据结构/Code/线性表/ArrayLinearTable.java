import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayLinearTable {
    final int ARRAY_LENGTH = 20;
    int[] linearArray = new int[ARRAY_LENGTH];
    int length;

    public ArrayLinearTable() {
        this.length = 0;
    }

    public int getElement(int order) {
        if (this.length == 0 || order < 1 || order > this.length) {
            return -1;
        }

        return this.linearArray[order - 1];
    }

    public void insertElement(int index, int value) throws ArrayIndexOutOfBoundsException {
        if (this.length >= ARRAY_LENGTH || index < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int idx = this.length - 1; idx >= index - 1; idx--) {
            this.linearArray[idx + 1] = this.linearArray[idx];
        }

        this.linearArray[index - 1] = value;
        this.length++;
    }

    public void deleteElement(int index) throws ArrayIndexOutOfBoundsException {
        if (this.length == 0 || index < 1 || index > this.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int idx = index; idx <= this.length; idx++) {
            this.linearArray[idx - 1] = this.linearArray[idx];
        }

        this.length--;
    }

    public static void main(String[] args) {
        ArrayLinearTable table = new ArrayLinearTable();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        table.insertElement(1, 100);

        for (int i = 0; i < 5; i++) {
            int curIdx = random.nextInt(1, table.length + 1);
            int curNum = random.nextInt(200);

            table.insertElement(curIdx, curNum);
            System.out.println(Arrays.toString(table.linearArray));
        }


        System.out.println(table.length);
        table.deleteElement(1);
        System.out.println(Arrays.toString(table.linearArray));
    }
}