package com.presentacion.desarrollolibre.audioplayer;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by andre on 28/5/2017.
 */

public class AudioPlayer extends Fragment {

    public static float density = 1;

    private class SeekBarView extends FrameLayout {

        private Paint innerPaint1;
        private Paint outerPaint1;
        private int thumbWidth;
        private int thumbHeight;
        public int thumbX = 0;
        public int thumbDX = 0;
        private boolean pressed = false;

        public SeekBarView(Context context) {
            super(context);
            setWillNotDraw(false);
            innerPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
//            innerPaint1.setColor(Resources.Theme.getColor(Resources.Theme.key_player_progressBackground));

            outerPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
//            outerPaint1.setColor(Resources.Theme.getColor(Resources.Theme.key_player_progress));

            thumbWidth = dp(24);
            thumbHeight = dp(24);
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

        public void setProgress(float progress) {
            int newThumbX = (int) Math.ceil((getMeasuredWidth() - thumbWidth) * progress);
            if (thumbX != newThumbX) {
                thumbX = newThumbX;
                if (thumbX < 0) {
                    thumbX = 0;
                } else if (thumbX > getMeasuredWidth() - thumbWidth) {
                    thumbX = getMeasuredWidth() - thumbWidth;
                }
                invalidate();
            }
        }

        public boolean isDragging() {
            return pressed;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int y = (getMeasuredHeight() - thumbHeight) / 2;
            canvas.drawRect(thumbWidth / 2, getMeasuredHeight() / 2 - dp(1), getMeasuredWidth() - thumbWidth / 2, getMeasuredHeight() / 2 + dp(1), innerPaint1);
            canvas.drawRect(thumbWidth / 2, getMeasuredHeight() / 2 - dp(1), thumbWidth / 2 + thumbX, getMeasuredHeight() / 2 + dp(1), outerPaint1);
            canvas.drawCircle(thumbX + thumbWidth / 2, y + thumbHeight / 2, dp(pressed ? 8 : 6), outerPaint1);
        }

    }

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

}