package jv.dl.vuforia.wikitude.rendering.external;

public class FaceTarget {
    private float[] projectionMatrix;
    private float[] viewMatrix;

    public FaceTarget() {
        projectionMatrix = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        viewMatrix = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(float[] projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(float[] viewMatrix) {
        this.viewMatrix = viewMatrix;
    }
}
