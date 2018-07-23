package jv.dl.vuforia.wikitude.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import jv.dl.vuforia.wikitude.R;

public final class DropDownAlert {

    public static final String TAG = "DropDownAlert";

    private final Activity activity;
    private View alertView;

    private final TextView alertText;
    private int marginTop;
    private boolean shown;

    public DropDownAlert(Activity activity) {
        this.activity = activity;

        LayoutInflater factory = LayoutInflater.from(this.activity);
        alertView = factory.inflate(R.layout.drop_down_alert, null);
        alertText = alertView.findViewById(R.id.dropdown_alert_text);
    }

    public void show() {
        this.show(null);
    }

    public void show(ViewGroup parentView) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        alertView.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) alertView.getLayoutParams();
                layoutParams.setMargins(0, marginTop, 0, 0);
                alertView.setLayoutParams(layoutParams);

                TranslateAnimation down = new TranslateAnimation(0, 0, -alertView.getHeight(), 0);
                down.setDuration(500);
                down.setInterpolator(new LinearInterpolator());

                alertView.startAnimation(down);
                shown = true;
            }
        });
        if (parentView == null) {
            if (alertView.getParent() == null) {
                activity.addContentView(alertView, lp);
            }
        } else {
            parentView.addView(alertView, lp);
        }
    }

    public void dismiss() {
        if (alertView != null && shown) {
            shown = false;
            alertView.post(new Runnable() {
                @Override
                public void run() {
                    TranslateAnimation up = new TranslateAnimation(0, 0, 0, -alertView.getHeight());
                    up.setDuration(500);
                    up.setInterpolator(new LinearInterpolator());
                    alertView.startAnimation(up);

                    if (alertView.getParent() != null) {
                        ((ViewManager) alertView.getParent()).removeView(alertView);
                        alertView = null;
                    }
                }
            });
        }
    }

    public void setText(String text) {
        alertText.setText(text);
    }

    public void addImages(String... paths) {
        for (String path : paths) {
            addImage(path);
        }
    }

    public void setTextWeight(float textToImageWidthRatio) {
        LinearLayout.LayoutParams textLayoutparams = (LinearLayout.LayoutParams) alertText.getLayoutParams();
        textLayoutparams.weight = textToImageWidthRatio;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        alertView.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) alertView.getLayoutParams();
                layoutParams.setMargins(0, DropDownAlert.this.marginTop, 0, 0);
                alertView.setLayoutParams(layoutParams);
            }
        });
    }

    private void addImage(String path) {

    }

    private Bitmap getBitmapFromAsset(String filePath) {
        AssetManager assetManager = activity.getAssets();

        InputStream inputStream;
        Bitmap bitmap = null;
        try {
            inputStream = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "getBitmapFromAsset: ", e);
        }

        return bitmap;
    }

    private int dpToPx(int dp) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
