package com.example.functionpainting;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class HelpPath {
    /**
     * 绘制网格:注意只有用path才能绘制虚线
     *
     * @param step 小正方形边长
     * @param coo 坐标系原点
     */
    public static Path gridPath(int step,Point coo,int[] location) {
        Path path = new Path();
        for (int i = 0; i < coo.y / step + 1; i++) {
            path.moveTo(0, coo.y-step*i);
            path.lineTo(location[0], coo.y-step * i);
            path.moveTo(0, coo.y+step*i);
            path.lineTo(location[0], coo.y+step * i);
        }
        for (int i = 0; i <  coo.x/ step + 1; i++) {
            path.moveTo(coo.x-step*i, 0);
            path.lineTo(coo.x-step * i, location[1]);
            path.moveTo(coo.x+step*i, 0);
            path.lineTo(coo.x+step * i, location[1]);
        }
        return path;
    }
    /**
     * 坐标系路径
     *
     * @param coo     坐标点
     * @return 坐标系路径
     */
    public static Path cooPath(Point coo,int[] location) {
        Path path = new Path();
        //x正半轴线
        path.moveTo(coo.x, coo.y);
        path.lineTo(location[0], coo.y);
        //x负半轴线
        path.moveTo(coo.x, coo.y);
        path.lineTo(0, coo.y);
        //y正半轴线
        path.moveTo(coo.x, coo.y);
        path.lineTo(coo.x, 0);
        //y负半轴线
        path.moveTo(coo.x, coo.y);
        path.lineTo(coo.x, location[1]);
        return path;
    }
}
