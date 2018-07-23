package jv.dl.vuforia.wikitude.rendering.external;

import java.util.Timer;
import java.util.TimerTask;

public class Driver {

    private final CustomSurfaceView customSurfaceView;
    private final int fps;
    private Timer renderTimer = null;


    public Driver(final CustomSurfaceView customSurfaceView, int fps) {
        this.customSurfaceView = customSurfaceView;
        this.fps = fps;

    }

    public void start() {
        if (renderTimer != null) {
            renderTimer.cancel();
        }

        renderTimer = new Timer();
        renderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                customSurfaceView.requestRender();
            }
        }, 0, 1000 / fps);
    }

    public void stop() {
        renderTimer.cancel();
        renderTimer = null;
    }

}
