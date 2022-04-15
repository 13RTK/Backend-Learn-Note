# 一、重复数字

![Xnip2022-04-14_08-58-27](Array/Xnip2022-04-14_08-58-27.jpg)



```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int curNum : nums) {
            if (!set.add(curNum)) {
                return true;
            }
        }

        return false;
    }
}


class Solution {
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }

        return false;
    }
}
```

<hr>









# 二、最大子数组和

![Xnip2022-04-14_09-06-01](Array/Xnip2022-04-14_09-06-01.jpg)



Code:

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        int curSum = 0;

        for (int i = 0; i < nums.length; i++) {
            curSum += nums[i];

            res = Math.max(curSum, res);
            curSum = Math.max(curSum, 0);
        }

        return res;
    }
}
```





