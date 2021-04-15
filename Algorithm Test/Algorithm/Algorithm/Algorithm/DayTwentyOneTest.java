public class DayTwentyOneTest {
    /**
     * remove all the repeat element, only retain a non-repeat element
     * @param nums the array to be processed
     * @return the processed array's length
     */

    public int removeDuplicates(int[] nums) {
        /*
            My Idea:
            1.Because the parameter is an ordered array, so if there have repeated element, must same as the previous one.
            2.Hence, we can compare it with the previous number, from the second element
         */

        // The index of the processed array
        int newArrayIndex = 1;

        for (int i = 1; i < nums.length; i++) {
           // If the test element is not repeated, add it to the new array, then update the index of the new array
           if (nums[i] != nums[i - 1]) {
                nums[newArrayIndex] = nums[i];
                newArrayIndex++;
            }
        }

        return newArrayIndex;
    }
}
