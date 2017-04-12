package com.vetrio.library;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;

import java.util.ArrayList;

public class LineIndicator extends BallSpinFadeLoaderIndicator {

    public static final float SCALE=1.0f;

    public static final int ALPHA=255;

    float[] scaleFloats=new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas=new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};

    private ArrayList<RectF> rectFs = new ArrayList<>();
    private boolean isCreate = true;
    private boolean isRefreshing = false;
    private float percent = 0f;

    public LineIndicator() {
        super();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius=getWidth()/10;
        float scale;
        int alpha;
        for (int i = 0; i < 8; i++) {
            canvas.save();
            Point point=circleAt(getWidth(),getHeight(),getWidth()/2.5f-radius,i*(Math.PI/4));

            if(!isRefreshing) {
                if(i < percent * 8) {
                    scale = 1f;
                    alpha = 255;
                } else {
                    scale = 0.5f;
                    alpha = 126;
                }
            } else {
                scale = scaleFloats[i];
                alpha = alphas[i];
            }
            canvas.translate(point.x, point.y);
            canvas.scale(scale, scale);
            canvas.rotate(i*45);
            paint.setAlpha(alpha);
            if(isCreate) {
                RectF rectF=new RectF(-radius,-radius/3f,2f*radius,radius/3f);
                rectFs.add(rectF);
            }
            canvas.drawRoundRect(rectFs.get(i),5,5,paint);
            canvas.restore();
        }
        isCreate = false;
    }

    public void setRefreshing(boolean isResfreshing) {
        this.isRefreshing = isResfreshing;
    }

    public void setPercent(float percent) {
        if(percent < .5f) {
            this.percent = 0;
        } else {
            this.percent = 2*percent - 1;
        }
    }

    Point circleAt(int width, int height, float radius, double angle){
        float x= (float) (width/2+radius*(Math.cos(angle)));
        float y= (float) (height/2+radius*(Math.sin(angle)));
        return new Point(x,y);
    }

    final class Point{
        public float x;
        public float y;

        public Point(float x, float y){
            this.x=x;
            this.y=y;
        }
    }
}