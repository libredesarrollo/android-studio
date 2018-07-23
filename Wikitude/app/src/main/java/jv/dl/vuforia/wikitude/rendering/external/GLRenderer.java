package jv.dl.vuforia.wikitude.rendering.external;

import com.wikitude.camera.CameraManager;
import com.wikitude.common.rendering.RenderExtension;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.TreeMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer, CameraManager.FovChangedListener {

    private final float[] projectionMatrix = new float[16];

    private RenderExtension renderExtension;
    private TreeMap<String, Renderable> occluders = new TreeMap<>();
    private TreeMap<String, Renderable> renderables = new TreeMap<>();
    private int width;
    private int height;

    public GLRenderer(RenderExtension wikitudeRenderExtension) {
        renderExtension = wikitudeRenderExtension;
        /*
         * Until Wikitude SDK version 2.1 onDrawFrame triggered also a logic update inside the SDK core.
         * This behaviour is deprecated and onUpdate should be used from now on to update logic inside the SDK core. <br>
         *
         * The default behaviour is that onDrawFrame also updates logic. <br>
         *
         * To use the new separated drawing and logic update methods, RenderExtension.useSeparatedRenderAndLogicUpdates should be called.
         * Otherwise the logic will still be updated in onDrawFrame.
         */
        renderExtension.useSeparatedRenderAndLogicUpdates();
    }

    @Override
    public void onSurfaceCreated(final GL10 unused, final EGLConfig config) {
        if (renderExtension != null) {
            renderExtension.onSurfaceCreated(unused, config);
        }

        for (TreeMap.Entry<String, Renderable> pairOccluder : occluders.entrySet()) {
            Renderable renderable = pairOccluder.getValue();
            renderable.onSurfaceCreated();
        }

        for (TreeMap.Entry<String, Renderable> pairRenderables : renderables.entrySet()) {
            Renderable renderable = pairRenderables.getValue();
            renderable.onSurfaceCreated();
        }
    }

    @Override
    public void onSurfaceChanged(final GL10 unused, final int width, final int height) {
        this.width = width;
        this.height = height;
        if (renderExtension != null) {
            renderExtension.onSurfaceChanged(unused, width, height);
        }
    }

    @Override
    public synchronized void onDrawFrame(final GL10 unused) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if (renderExtension != null) {
            // Will trigger a logic update in the SDK
            renderExtension.onUpdate();
            // will trigger drawing of the camera frame
            renderExtension.onDrawFrame(unused);
        }

        for (TreeMap.Entry<String, Renderable> pairOccluder : occluders.entrySet()) {
            Renderable renderable = pairOccluder.getValue();
            renderable.onDrawFrame();
        }

        for (TreeMap.Entry<String, Renderable> pairRenderables : renderables.entrySet()) {
            Renderable renderable = pairRenderables.getValue();
            renderable.onDrawFrame();
        }
    }

    public void onResume() {
        if (renderExtension != null) {
            renderExtension.onResume();
        }
    }

    public void onPause() {
        if (renderExtension != null) {
            renderExtension.onPause();
        }
    }

    public synchronized void setRenderablesForKey(final String key, final Renderable renderbale, final Renderable occluder) {
        if (occluder != null) {
            occluder.projectionMatrix = projectionMatrix;
            occluders.put(key, occluder);
        }

        renderbale.projectionMatrix = projectionMatrix;
        renderables.put(key, renderbale);
    }

    public synchronized void removeRenderablesForKey(final String key) {
        renderables.remove(key);
        occluders.remove(key);
    }

    public synchronized void removeAllRenderables() {
        renderables.clear();
        occluders.clear();
    }

    public synchronized Renderable getRenderableForKey(final String key) {
        return renderables.get(key);
    }

    public synchronized Renderable getOccluderForKey(final String key) {
        return occluders.get(key);
    }

    @Override
    public synchronized void onFovChanged(final float fieldOfView) {
        if (width != 0 && height != 0) {
            Matrix.perspectiveM(projectionMatrix, 0, fieldOfView, (float)width/(float)height, 0.05f, 5000f);
        }
    }
}
