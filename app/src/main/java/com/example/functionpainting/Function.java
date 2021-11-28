package com.example.functionpainting;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class Function {
    private List<Point> points = new ArrayList<Point>();//主映射表
    private List<Point> pointsList = new ArrayList<>();//辅映射表
    private String string;//映射关系
    private int mul;
    static int value = 0;
    private Path mPath;
    ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");

    public Function(String str,int mul) {
        this.string = str;
        this.mul = mul;
        points = new ArrayList<>();
        pointsList = new ArrayList<>();
        mPath = new Path();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getMul() {
        return mul;
    }

    public void setMul(int mul) {
        this.mul = mul;
    }

    private void initDf(Point coo) {
        Point point;
        initFunction();
        for (double i = -500; i <= 500; i++) {
            point = new Point();
            switch (value){
                case 1:
                {
                    if(i<0){
                        point.x = (int)(coo.x+i);
                        point.y = (int) (coo.y-f(i/mul)*mul);
                        points.add(point);
                    }
                    else if(i>0){
                        point.x = (int)(coo.x+i);
                        point.y = (int)(coo.y-f(i/mul)*mul);
                        pointsList.add(point);
                    }
                    break;
                }
                default:
                    point.x = (int)(coo.x+i);
                    point.y = (int)(coo.y-f(i/mul)*mul);
                    pointsList.add(point);
            }
        }
    }

    private double f(double x) {
        char[] chars = this.string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'x') {
                char[] chs = String.valueOf(x).toCharArray();
                chars[i] = ')';
                chars = insert(chars, chs, i);
                chars = insert(chars, '(', i);
                i = i + chs.length + 1;
            }
        }
        try {
            String str = String.valueOf(chars);
            double result = (Double) js.eval(str);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public char[] insert(char[] chars, char[] chs, int i) {
        for (int m = chs.length - 1; m >= 0; m--) {
            chars = insert(chars, chs[m], i);
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

    /*public char[] delete(char[] chas, int i) {
        char[] chars = new char[chas.length - 1];
        for (int m = i; m < chas.length - 1; m++) {
            chars[m] = chas[m + 1];
        }
        for (int m = 0; m < chars.length; m++) {
            chars[m] = chas[m];
        }
        return chars;
    }*/
    private void initFunction(){
        char[] chars = string.toCharArray();
        for(int i = 0;i<chars.length;i++){
            if(chars[i]=='/' && chars[i+1] =='x'){
                value = 1;
            }
        }
    }
    private void drawPath(){
        if(!pointsList.isEmpty()){
            drawFunction(pointsList);
        }
        drawFunction(points);
    }
    public void drawFunction(List<Point> points){
        float lineSmoothness = (float) 0.16;
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;//前一个点x
        float previousPointY = Float.NaN;//前一个点y
        float currentPointX = Float.NaN;//当前点x
        float currentPointY = Float.NaN;//当前点y
        float nextPointX;
        float nextPointY;

        final int size = points.size();
        for (int i = 0; i < size; i++) {
            if (Float.isNaN(currentPointX)) {
                currentPointX = points.get(i).x;
                currentPointY = points.get(i).y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (i > 0) {
                    Point point = points.get(i - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (i > 1) {
                    Point point = points.get(i - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (i < size - 1) {
                Point point = points.get(i + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (i == 0) {
                // 将Path移动到开始点
                mPath.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (lineSmoothness * firstDiffX);
                final float firstControlPointY = previousPointY + (lineSmoothness * firstDiffY);
                final float secondControlPointX = currentPointX - (lineSmoothness * secondDiffX);
                final float secondControlPointY = currentPointY - (lineSmoothness * secondDiffY);
                //画出曲线
                mPath.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
            }

            // 更新值
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
    }

    public Path getmPath(Point coo) {
        initDf(coo);
        drawPath();
        return mPath;
    }
}
