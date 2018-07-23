package jv.dl.vuforia.wikitude.rendering.external;

import com.wikitude.common.rendering.RenderExtension;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRendererFaceDetectionPlugin extends GLRenderer {

    private StrokedRectangle strokedRectangle;
    private FaceTarget currentlyRecognizedFace;


    public GLRendererFaceDetectionPlugin(RenderExtension wikitudeRenderExtension_) {
        super(wikitudeRenderExtension_);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        super.onSurfaceCreated(unused, config);
        strokedRectangle = new StrokedRectangle(StrokedRectangle.Type.FACE);
        strokedRectangle.onSurfaceCreated();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        super.onDrawFrame(unused);
        if (currentlyRecognizedFace != null) {
            strokedRectangle.projectionMatrix = currentlyRecognizedFace.getProjectionMatrix();
            strokedRectangle.viewMatrix = currentlyRecognizedFace.getViewMatrix();
            strokedRectangle.onDrawFrame();
        }
    }

    public void setCurrentlyRecognizedFace(FaceTarget face) {
        currentlyRecognizedFace = face;
    }

}
