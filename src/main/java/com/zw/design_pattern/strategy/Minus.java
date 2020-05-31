package com.zw.design_pattern.strategy;

/**
 * 策略设计模式
 * @author zhouwei
 * @date 2020-22-22:16
 */
public class Minus extends AbstractCalculator implements ICalculator {

    @Override
    public int calculate(String exp) {

        int arrayInt[] = split(exp, "-");
        return arrayInt[0] - arrayInt[1];
    }
}

class Plus extends AbstractCalculator implements ICalculator {

    @Override
    public int calculate(String exp) {

        int arrayInt[] = split(exp, "\\+");
        return arrayInt[0] + arrayInt[1];
    }
}

class AbstractCalculator {

    public int[] split(String exp, String opt) {

        String array[] = exp.split(opt);
        int arrayInt[] = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}
