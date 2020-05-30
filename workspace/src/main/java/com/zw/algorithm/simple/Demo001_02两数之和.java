package com.zw.algorithm.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwei
 * @date 2020-30-16:57
 */
public class Demo001_02两数之和 {

    public static void main(String[] args) {

        int[] ints = {1, 4, 2, 3, 5, 2};
        int target = 4;
        int[] sum = twoSum(ints, target);
        for (int i = 0; i < sum.length; i++) {
            System.out.println(sum[i]);
        }
    }

    // 两遍哈希表 ,时间复杂度0（n），空间复杂度O（n）
    public static int[] twoSum(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int middle = target - nums[i];
            if (map.containsKey(middle) && map.get(middle) != i) {
                // return new int[]{i,map.get(middle)};
                return new int[]{nums[i], middle};
            }
        }
        return null;
    }
}
