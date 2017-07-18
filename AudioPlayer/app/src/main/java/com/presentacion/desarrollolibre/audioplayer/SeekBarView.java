package com.presentacion.desarrollolibre.audioplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;


/**
 * Created by andre on 30/5/2017.
 */

class SeekBarView extends FrameLayout {

    Paint mPaint;
    private Paint innerPaint1;
    private Paint outerPaint1;
    private int thumbWidth;
    private int thumbHeight;
    public int thumbX = 5;
    public int thumbDX = 5;
    private boolean pressed = false;
    public static float density = 1;

    public SeekBarView(Context context) {
        super(context);
        setWillNotDraw(false);
        innerPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint1.setColor(Color.BLACK);

        outerPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerPaint1.setColor(Color.BLACK);
//            outerPaint1.setColor(Resources.Theme.getColor(Resources.Theme.key_player_progress));

        thumbWidth = dp(100);
        thumbHeight = dp(10);

        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouch(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onTouch(event);
    }

    boolean onTouch(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);


            int additionWidth = (getMeasuredHeight() - thumbWidth) / 2;
            if (thumbX - additionWidth <= ev.getX() && ev.getX() <= thumbX + thumbWidth + additionWidth && ev.getY() >= 0 && ev.getY() <= getMeasuredHeight()) {
                pressed = true;
                thumbDX = (int) (ev.getX() - thumbX);
                invalidate();
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (pressed) {
                if (ev.getAction() == MotionEvent.ACTION_UP) {
//                        onSeekBarDrag((float) thumbX / (float) (getMeasuredWidth() - thumbWidth));
                }
                pressed = false;
                invalidate();
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (pressed) {
                thumbX = (int) (ev.getX() - thumbDX);
                if (thumbX < 0) {
                    thumbX = 0;
                } else if (thumbX > getMeasuredWidth() - thumbWidth) {
                    thumbX = getMeasuredWidth() - thumbWidth;
                }
                invalidate();
                return true;
            }
        }
        return false;
    }

    public boolean isDragging() {
        return pressed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("AAA", getMeasuredHeight() + "__");
        int y = (getMeasuredHeight() - thumbHeight) / 2;
        canvas.drawRect(thumbWidth / 2, getMeasuredHeight() / 2 - dp(1), getMeasuredWidth() - thumbWidth / 2, getMeasuredHeight() / 2 + dp(1), innerPaint1);
        canvas.drawCircle(thumbX + thumbWidth / 2, y + thumbHeight / 2, dp(pressed ? 8 : 6), outerPaint1);
    }


    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

}
