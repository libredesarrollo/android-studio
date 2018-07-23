package jv.dl.vuforia.wikitude.rendering.external;

import com.wikitude.common.rendering.RenderExtension;

import java.util.TreeMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRendererExtendedTracking extends GLRenderer {

    private TreeMap<String, StrokedRectangle> extendedTrackingRectangles = new TreeMap<>();

    public GLRendererExtendedTracking(RenderExtension wikitudeRenderExtension) {
        super(wikitudeRenderExtension);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        super.onSurfaceCreated(unused, config);

        for (TreeMap.Entry<String, StrokedRectangle> pairRectangles : extendedTrackingRectangles.entrySet()) {
            StrokedRectangle rectangle = pairRectangles.getValue();
            rectangle.onSurfaceCreated();
        }
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        super.onDrawFrame(unused);

        for (TreeMap.Entry<String, StrokedRectangle> pairRectangles : extendedTrackingRectangles.entrySet()) {
            StrokedRectangle rectangle = pairRectangles.getValue();
            rectangle.onDrawFrame();
        }
    }

    @Override
    public void setRenderablesForKey(final String key, final Renderable renderable, final Renderable occluder) {
        super.setRenderablesForKey(key, renderable, occluder);

        if (!extendedTrackingRectangles.containsKey(key)) {
            extendedTrackingRectangles.put(key, new StrokedRectangle(StrokedRectangle.Type.EXTENDED));
        }
    }

    @Override
    public void removeRenderablesForKey(final String key) {
        super.removeRenderablesForKey(key);

        extendedTrackingRectangles.remove(key);
    }
}
