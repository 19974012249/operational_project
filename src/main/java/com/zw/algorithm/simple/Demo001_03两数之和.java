package com.zw.algorithm.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwei
 * @date 2020-30-21:27
 */
public class Demo001_03两数之和 {


    // 一遍哈希表
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        return null;
    }

}
