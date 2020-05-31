package com.zw.algorithm.simple;

/**
 * @author zhouwei
 * @date 2020-30-16:19
 */
public class Demo001_01两数之和 {

    public static void main(String[] args) {

        int[] arr = {1, 3, 6, 7, 8, 99};
        System.out.println(arr.length);
        int target = 10;
        int[] ints = twoSun(arr, target);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }

    // 两数之和（暴力法，时间复杂度：O（n^2）空间复杂度：O（1））
    public static int[] twoSun(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                // 如果满足求和要求
                System.out.println(i + ":" + j);
                if (nums[i] == target - nums[j]) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }
}
