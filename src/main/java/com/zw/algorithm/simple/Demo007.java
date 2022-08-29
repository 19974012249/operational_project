package com.zw.algorithm.simple;

/**
 * NC7 买卖股票的最好时机(一)
 * 假设你有一个数组prices，长度为n，其中prices[i]是股票在第i天的价格，请根据这个价格数组，返回买卖股票能获得的最大收益
 * 1.你可以买入一次股票和卖出一次股票，并非每天都可以买入或卖出一次，总共只能买入和卖出一次，且买入必须在卖出的前面的某一天
 * 2.如果不能获取到任何利润，请返回0
 * 3.假设买入卖出均无手续费
 * <p>
 * 数据范围： 0 \le n \le 10^5 , 0 \le val \le 10^40≤n≤10
 * 5
 * ,0≤val≤10
 * 4
 * <p>
 * 要求：空间复杂度 O(1)O(1)，时间复杂度 O(n)O(n)
 *
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-26 11:33
 * @since 0.1.0
 **/
public class Demo007 {

    public static void main(String[] args) {

        int[] prices = {3, 15, 1, 3, 5, 7, 11};
        System.out.println(maxProfitTest(prices));
    }


    /**
     * 规则：
     * 暴力循环比较最大差
     *
     * @param prices
     * @return
     */
    public static int maxProfitTest(int[] prices) {
        // 利润最低点
        int res = 0;
        //初始化最小值
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 变动最小值
            min = Math.min(min, prices[i]);

            System.out.print("第" + min);

            res = Math.max(res, prices[i] - min);

            System.out.print("第" + res);
            System.out.println();
        }
        return res;


    }

    /**
     * @param prices
     * @return
     */
    public int maxProfit001(int[] prices) {

        int len = prices.length;
        int minPrices = Integer.MAX_VALUE;
        int ans = prices[0];
        for (int i = 0; i < len; i++) {
            //寻找最低点
            if (prices[i] < minPrices) {
                minPrices = prices[i];
            } else if (prices[i] - minPrices > ans) {
                //更新答案（最大利润）
                ans = prices[i] - minPrices;
            }
        }
        return ans;
    }

    /**
     * 大循环比较
     *
     * @param prices
     * @return
     */
    public int maxProfit002(int[] prices) {
        // 判空
        if (prices == null) {
            return 0;
        }
        int mid = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int max = prices[j] - prices[i];
                if (max > mid) {
                    mid = max;
                }
            }
        }
        return mid;
    }
}
