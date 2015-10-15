package com.husy.rectanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.print.PrintAttributes;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by husy on 2015/10/15.
 *
 * @link http://blog.csdn.net/u010156024
 * @description：
 */
public class RectAnimationView extends View {
    private Paint rectP;
    private Paint rectK;
    private int rectW = 30;
    private int count = 0;
    private int screemW;
    private int screemH;
    public RectAnimationView(Context context) {
        super(context);
        init();
    }

    public RectAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RectAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        rectP = new Paint();
        rectP.setStrokeWidth(3);
        rectP.setAntiAlias(true);
        rectP.setStyle(Paint.Style.FILL);

        rectK = new Paint();
        rectK.setAntiAlias(true);
        rectK.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LinearGradient linearGradient = new LinearGradient(
                0,
                0,
                rectW,
                randomH(),
                new int[]{Color.BLUE,Color.YELLOW,Color.CYAN,Color.GREEN},
                null,
                Shader.TileMode.CLAMP);
        rectP.setShader(linearGradient);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (modeW==MeasureSpec.UNSPECIFIED){
            width = 250;
        }
        if (modeH==MeasureSpec.UNSPECIFIED){
            height = 250;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screemH = getHeight()-getPaddingLeft()-getPaddingRight();
        screemW = getWidth()-getPaddingTop()-getPaddingBottom();
        int top = screemH*2/3;
        count = screemW/(rectW+20);
        Rect rectw = new Rect(0,0,screemW,top);
        canvas.drawRect(rectw, rectK);
        for (int i=0;i<count;i++){
            Rect rect = new Rect(
                    (int)(screemW*0.03f+i*(rectW+20)),
                    top-randomH(),
                    (int)(screemW*0.03f+(i)*(rectW+20)+rectW),
                    top);
            canvas.drawRect(rect,rectP);
        }
        canvas.save();
        postInvalidateDelayed(300);
    }
    private int randomH(){
        double rand = Math.random();
        return (int)(rand*screemH*2/3);
    }
}
