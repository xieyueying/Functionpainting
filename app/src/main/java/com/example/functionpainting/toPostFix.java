package com.example.functionpainting;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class toPostFix {
    private String[] postFix;
    public boolean comparePrior(String operator1, String operator2) {
        if("(".equals(operator2)) {
            return true;
        }
        if ("*".equals(operator1) || "/".equals(operator1)) {
            if ("+".equals(operator2) || "-".equals(operator2)) {
                return true;
            }
        }
        return false;
    }
    public toPostFix(ArrayList<String> expressionStrs) {
        //新组成的表达式
        ArrayList<String> newExpressionStrs = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < expressionStrs.size(); i++) {
            if ("(".equals(expressionStrs.get(i))) { // 如果是左括号,则入栈
                stack.push(expressionStrs.get(i));
            } else if ("+".equals(expressionStrs.get(i)) || "-".equals(expressionStrs.get(i)) || "*".equals(expressionStrs.get(i)) || "/".equals(expressionStrs.get(i))) {
                if (!stack.empty()) { // 取出先入栈的运算符
                    String s = stack.pop();
                    if(comparePrior(expressionStrs.get(i), s)) { //如果栈值优先级小于要入栈的值,则继续压入栈
                        stack.push(s);
                    } else {  //否则取出值
                        newExpressionStrs.add(s);
                    }
                }
                stack.push(expressionStrs.get(i));
            } else if (")".equals(expressionStrs.get(i))) { //如果是")",则出栈,一直到遇到"("
                while (!stack.empty()) {
                    String s = stack.pop();
                    if (!"(".equals(s)) {
                        newExpressionStrs.add(s);
                    } else {
                        break;
                    }
                }
            } else {
                newExpressionStrs.add(expressionStrs.get(i));
            }
        }
        while (!stack.empty()) {
            String s = stack.pop();
            newExpressionStrs.add(s);
        }
        postFix = newExpressionStrs.toArray(new String[0]);

    }


    public String[] getPostFix() {
        return postFix;
    }

    public void setPostFix(String[] postFix) {
        this.postFix = postFix;
    }
}
