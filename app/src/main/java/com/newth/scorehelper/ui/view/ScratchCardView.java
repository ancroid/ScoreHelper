package com.newth.scorehelper.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class ScratchCardView extends AppCompatTextView {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();
    private Path path = new Path();
    private int mx, my;
    private boolean isClear = false;
    private onClearCardListener cardListener;

    private Runnable runnable = new Runnable() {
        private int[] pixels;
        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;
            pixels = new int[w * h];
            Bitmap mbitmap = bitmap;
            mbitmap.getPixels(pixels, 0, w, 0, 0, w, h);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (pixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                if (percent > 60) {
                    isClear = true;
                    postInvalidate();
                    cardListener.clear();
                }
            }
        }
    };

    public ScratchCardView(Context context) {
        super(context);
    }

    public ScratchCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScratchCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isClear) {
            drawPath();
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    private void drawPath() {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawPath(path, paint);
    }

    public void setClear(boolean isClear){
        this.isClear=isClear;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#c0c0c0"));

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(60);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mx = x;
                my = y;
                path.moveTo(mx, my);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mx);
                int dy = Math.abs(y - my);
                if (dx > 3 || dy > 3) {
                    path.lineTo(x, y);
                }
                mx = x;
                my = y;
                break;
            case MotionEvent.ACTION_UP:
                if (!isClear) {
                    new Thread(runnable).start();
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public void setOnclearCardListener(onClearCardListener cardListener) {
        this.cardListener = cardListener;
    }

    /**
     * check finish cleaning
     */
    public interface onClearCardListener {
        void clear();
    }
}
