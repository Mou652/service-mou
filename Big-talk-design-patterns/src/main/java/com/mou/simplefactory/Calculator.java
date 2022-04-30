package com.mou.simplefactory;

import com.mou.simplefactory.factory.OperatorFactory;
import com.mou.simplefactory.operator.Operator;

import java.util.Scanner;

/**
 * 计算器运行类
 *
 * @author: mou
 * @date: 2020/10/3
 */
public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第一个数字:");
        String numA = scanner.nextLine();
        System.out.println("请输入运算符号:");
        String operator = scanner.nextLine();
        System.out.println("请输入第二个数字:");
        String numB = scanner.nextLine();

        // 利用工厂创建操作对象
        Operator oper = OperatorFactory.getOperator(operator);
        Object calculate = oper.calculate(Double.parseDouble(numA), Double.parseDouble(numB));
        System.out.println(numA + operator + numB + " = " + calculate.toString());
    }

    public Object calculator() {
        return 0;
    }
}
