package com.example.functionpainting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class DrawView extends View {
    private Point mCoo;//坐标原点
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件
    private Paint mPaint;//主画笔
    private Path mPath;//主路径
    private int[] location;
    static int mul = 1;
    private Function function;
    String initString = "x";
    List<Point> points = new ArrayList<>();
    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        location = new int[]{1080,1791};
        init(context);
    }
    private void init(Context context) {
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //初始化主路径
        mPath = new Path();
        //初始化原点
        mCoo = new Point(location[0]/2,location[1]/2);
        //初始化辅助
        HelpDraw helpDraw = new HelpDraw();
        mGridPicture = helpDraw.getGrid(getContext(),mCoo,location);
        mCooPicture = helpDraw.getCoo(getContext(),mCoo,location,mul);
        //初始化函数类
        function = new Function(initString,mul);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        HelpDraw.draw(canvas, mGridPicture, mCooPicture);
        mPath = function.getmPath(mCoo);
        canvas.drawPath(mPath,mPaint);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);
        canvas.scale(1, -1);//y轴向上
        canvas.restore();
    }
    //初始化最小分度值
    public void initScale(int mo){
        HelpDraw helpDraw = new HelpDraw();
        mul = 100/mo;
        function = new Function(initString,mul);
        mGridPicture = helpDraw.getGrid(getContext(),mCoo,location);
        mCooPicture = helpDraw.getCoo(getContext(),mCoo,location,mul);
        invalidate();
    }
    //初始化函数
    public void initFunction(String str){
        initString = str;
        function = new Function(initString,mul);
        invalidate();
    }

    public void setLocation(int[] location) {
        this.location = location;
    }
}
