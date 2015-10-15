package com.husy.rectanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by husy on 2015/10/15.
 *
 * @link http://blog.csdn.net/u010156024
 * @description：
 */
public class RectAnimationView extends View {
    private Paint rectP;
    private Paint rectK;
    private int rectW = 30;//每个矩形的宽度
    private int count = 0;//矩形的个数
    private int viewW;//组件的宽度
    private int viewH;//组件的高度
    private LinearGradient linearGradient;//线性渐变
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
        rectP = new Paint();//初始化矩形的画笔
        rectP.setAntiAlias(true);
        rectP.setStyle(Paint.Style.FILL);

        rectK = new Paint();//初始化边框的画笔
        rectK.setAntiAlias(true);
        rectK.setStyle(Paint.Style.STROKE);

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
        }//当宽高不指定的时候，指定默认值
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算组件绘制区域的宽高
        viewH = getHeight()-getPaddingLeft()-getPaddingRight();
        viewW = getWidth()-getPaddingTop()-getPaddingBottom();
        int bottom = viewH;//计算view底部距离
        count = viewW /(rectW+10);//计算矩形的个数 10位矩形的间距
        Rect rectw = new Rect(0,0, viewW,bottom);//外边框矩形
        canvas.drawRect(rectw, rectK);
        //循环绘制矩形
        for (int i=0;i<count;i++){
            linearGradient = new LinearGradient(
                    0,
                    0,
                    rectW,
                    randomH(),
                    new int[]{Color.BLUE,Color.YELLOW,Color.CYAN,Color.GREEN},
                    new float[]{0.3f,0.5f,0.7f,0.9f},
                    Shader.TileMode.CLAMP);
            rectP.setShader(linearGradient);
            Rect rect = new Rect(
                    (int)(viewW *0.03f+i*(rectW+10)),
                    bottom-randomH(),
                    (int)(viewW *0.03f+(i)*(rectW+10)+rectW),
                    bottom);
            canvas.drawRect(rect,rectP);
        }
        canvas.save();
        postInvalidateDelayed(300);
    }
    //随机生成矩形的高度，形成梯度
    private int randomH(){
        double rand = Math.random();
        return (int)(rand* viewH);
    }
}