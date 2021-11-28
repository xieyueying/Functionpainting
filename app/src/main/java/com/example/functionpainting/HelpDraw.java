package com.example.functionpainting;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Point;
import android.icu.util.ULocale;

public class HelpDraw{
    private Paint helpPaint;
    Picture coo = new Picture();//坐标轴picture
    Picture grid = new Picture();//网格picture
    Point mWinSize = new Point();
    Canvas canvas;

    public static void draw(Canvas canvas, Picture mGridPicture, Picture mCooPicture) {
        canvas.drawPicture(mCooPicture);
        canvas.drawPicture(mGridPicture);
    }

    public Paint getHelpPaint() {
        return helpPaint;
    }

    //获取坐标轴
    public Picture getCoo(Context context, Point mcoo,int[] location,int mul) {
        init(context);
        Canvas recodingCanvas = coo.beginRecording(canvas.getWidth(), canvas.getHeight());
        drawCoo(recodingCanvas,mcoo,helpPaint,location,mul);
        coo.endRecording();
        return coo;
    }
    //获取网格
    public Picture getGrid(Context context,Point mcoo,int[] location) {
        init(context);
        Canvas recodingCanvas = grid.beginRecording(canvas.getWidth(), canvas.getHeight());
        drawGrid(recodingCanvas,helpPaint,mcoo,location);
        grid.endRecording();
        return grid;
    }

    public void init(Context context){
        Utils.loadWinSize(context,mWinSize);
        Bitmap bitmap = Bitmap.createBitmap(mWinSize.x,mWinSize.y-302,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        helpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    //绘制网格
    public static void drawGrid(Canvas canvas, Paint paint,Point mcoo,int[] location) {
        //初始化网格画笔
        paint.setStrokeWidth(2);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        //设置虚线效果new float[]{可见长度, 不可见长度},偏移值
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        canvas.drawPath(HelpPath.gridPath(50,mcoo,location), paint);
    }
    /**
     * 绘制坐标轴
     * @param canvas  画布
     * @param coo     坐标系原点
     * @param paint   画笔
     */
    public static void drawCoo(Canvas canvas, Point coo, Paint paint, int[] location,int mul) {
        //初始化网格画笔
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        //设置虚线效果new float[]{可见长度, 不可见长度},偏移值
        paint.setPathEffect(null);
        //绘制直线
        canvas.drawPath(HelpPath.cooPath(coo,location), paint);
        //左箭头
        canvas.drawLine(location[0], coo.y, location[0] - 40, coo.y - 20, paint);
        canvas.drawLine(location[0], coo.y, location[0] - 40, coo.y + 20, paint);
        //上箭头
        canvas.drawLine(coo.x, 0, coo.x - 20, 40, paint);
        canvas.drawLine(coo.x, 0, coo.x + 20, 40, paint);
        //为坐标系绘制文字
        drawText4Coo(canvas, coo, paint,location,mul);
    }
    /**
     * 为坐标系绘制文字
     *
     * @param canvas  画布
     * @param coo     坐标系原点
     * @param paint   画笔
     */
    private static void drawText4Coo(Canvas canvas, Point coo, Paint paint,int[] location,int mul) {
        //绘制文字
        paint.setTextSize(50);
        canvas.drawText("x", location[0] - 60, coo.y - 40, paint);
        canvas.drawText("y", coo.x + 40, 40, paint);
        paint.setTextSize(25);
        //X正轴文字
        for (int i = 1; i <= (coo.x / 100); i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(100 * i/mul + "", coo.x - 20 + 100 * i, coo.y + 40, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x + 100 * i, coo.y, coo.x + 100 * i, coo.y - 10, paint);
        }
        //X负轴文字
        for (int i = 1; i <= (coo.x / 100); i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(-100 * i/mul + "", coo.x - 20 - 100 * i, coo.y + 40, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x - 100 * i, coo.y, coo.x - 100 * i, coo.y - 10, paint);
        }
        //y正轴文字
        for (int i = 1; i <= (coo.y / 100); i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(100 * i/mul + "", coo.x - 60, coo.y +10 - 100 * i, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x, coo.y + 100 * i, coo.x + 10, coo.y + 100 * i, paint);
        }
        //y负轴文字
        for (int i = 1; i <= (coo.y / 100); i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(-100 * i/mul + "", coo.x - 70, coo.y + 10 + 100 * i, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x, coo.y - 100 * i, coo.x + 10, coo.y - 100 * i, paint);
        }
    }

    /*作者：张风捷特烈
    链接：https://juejin.cn/post/6844903705930629128
    来源：稀土掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
}
