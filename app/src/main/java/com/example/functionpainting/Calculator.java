package com.example.functionpainting;


import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
    private String[] str;
    static int m = 0;
    public String[] getStr() {
        return str;
    }

    public void setStr(String[] str) {
        this.str = str;
    }

    public Calculator(char[] chars) {//字符串str2用来接收文本内容（表达式）
        chars = Calculator(chars);
        ArrayList<String> list = new ArrayList<>();//创建字动态符串数组list
        String str1 = new String();
        for (int i = 0; i < chars.length; i++)//遍历字符数组chars
        {
            if (chars[i] >= '0' && chars[i] <= '9') {
                str1 = toChars(chars, i, str1);
                i=m-1;
                list.add(str1);
                if(i < chars.length-2 && (chars[i+1]=='^' || (chars[i+1]==')' && chars[i+2]=='^'))){
                    if(chars[i+1] == '^'){
                        String str3 = new String();
                        str3 = toChars(chars,i+2,str3);
                        i=m-1;
                        int q = Integer.valueOf(str3);
                        for(int e=1;e<q;e++){
                            list.add("*");
                            list.add(str1);
                        }
                    }else if(chars[i+1]==')' && chars[i+2]=='^'){
                        String str3 = new String();
                        str3 = toChars(chars,i+2,str3);
                        i=m-1;
                        int q = Integer.valueOf(str3);
                        for(int e=1;e<q;e++){
                            list.add("*");
                            list.add(str1);
                        }
                    }

                }
                str1 = new String();
            } else {
                str1 += chars[i];
                list.add(str1);
                str1 = new String();
            }
        }
        toPostFix postFix = new toPostFix(list);
        this.str = postFix.getPostFix();
    }

    public char[] Calculator(char[] chars) {//将字符串中的负数转化为式子，-2 -> (0-2)
        String str = new String();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                if (i != 0 && chars[i - 1] == '(') {
                    chars = insert(chars, '0', i);
                    chars = insert(chars, '(', i);
                    toChars(chars, i + 3, str);
                    chars = insert(chars, ')', m);
                    i = m - 1;
                } else if (i == 0) {
                    chars = insert(chars, '0', i);
                    chars = insert(chars, '(', i);
                    toChars(chars, i + 3, str);
                    chars = insert(chars, ')', m);
                    i = m - 1;
                }
            }
            if(chars[i]=='e'){
                chars[i] = '2';
                chars = insert(chars,'.',i+1);
                chars = insert(chars,'7',i+2);
                chars = insert(chars,'1',i+3);
                chars = insert(chars,'8',i+4);
                i=i+4;
            }
        }
        return chars;
    }

    public char[] insert(char[] chars, char ch, int i) {
        char[] chars1 = new char[chars.length + 1];
        for (int m = 0; m < i; m++) {
            chars1[m] = chars[m];
        }
        chars1[i] = ch;
        for (int m = i + 1; m < chars1.length; m++) {
            chars1[m] = chars[m - 1];
        }
        return chars1;
    }

    public String toChars(char[] chars, int i, String string) {//迭代将字符串划分
        if (chars[i] >= '0' && chars[i] <= '9') {
            string += chars[i];
            m = i + 1;
            if (i == chars.length - 1) {
                return string;
            } else {
                return toChars(chars, m, string);
            }
        }
        return string;
    }

    public int calculator() {

        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < str.length; i++) {
            if (isNumeric(str[i])) {
                int m = Integer.parseInt(str[i]);//转为double类型
                stack.push(m);
            } else {
                int n = stack.pop();//操作数1
                int m = stack.pop();//操作数2
                stack.push(Operate(m, n, str[i]));//计算运算结果并入栈
            }
        }
        return stack.pop();//返回运算结果
    }

    public int Operate(int m, int n, String str) {
        int value = 0;
        switch (str) {
            case "+":
                value = m + n;
                break;
            case "-":
                value = m - n;
                break;
            case "*":
                value = m * n;
                break;
            case "/":
                value = m / n;
                break;
            default:
                System.out.println("input error");
                break;
        }
        return value;
    }

    public static boolean isNumeric(String string) {
        double intValue;
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Double.parseDouble(string);//字符串类型转换为double类型
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}
