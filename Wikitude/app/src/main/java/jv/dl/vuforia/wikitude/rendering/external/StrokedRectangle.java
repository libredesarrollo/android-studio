package jv.dl.vuforia.wikitude.rendering.external;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class StrokedRectangle extends Renderable {

    private final static String TAG = "StrokedRectangle";

    private static final String FRAGMENT_SHADER_CODE =
            "precision mediump float;" +
                    "uniform vec3 Color;" +
                    "void main()" +
                    "{" +
                    "  gl_FragColor = vec4(Color, 1.0);" +
                    "}";

    private static final String VERTEX_SHADER_CODE =
            "attribute vec4 v_position;" +
                    "uniform mat4 Projection;" +
                    "uniform mat4 ModelView;" +
                    "uniform mat4 Scale;" +
                    "void main()" +
                    "{" +
                    "  gl_Position = Projection * ModelView * Scale * v_position;" +
                    "}";

    private static final float RECT_VERTS[] = {
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f};

    private static final float RECT_VERTS_EXTENDED[] = {
            -0.7f, -0.7f, 0.0f,
            -0.7f, 0.7f, 0.0f,
            0.7f, 0.7f, 0.0f,
            0.7f, -0.7f, 0.0f};

    private static final float RECT_VERTS_FACE[] = {
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f};

    private final ShortBuffer indicesBuffer;
    private final FloatBuffer rectBuffer;
    private final short indices[] = {0, 1, 2, 3};

    private int augmentationProgram = -1;
    private int positionSlot = -1;
    private int projectionUniform = -1;
    private int modelViewUniform = -1;
    private int colorUniform = -1;
    private int scaleUniform = -1;

    private float red = 1.0f;
    private float green = 0.2f;
    private float blue = 0.9f;

    private float xScale = 1.0f;
    private float yScale = 1.0f;

    public StrokedRectangle() {
        this(Type.STANDARD);
    }

    public StrokedRectangle(Type type) {
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indicesBuffer = dlb.asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);

        ByteBuffer bb = ByteBuffer.allocateDirect(RECT_VERTS.length * 4);
        bb.order(ByteOrder.nativeOrder());
        rectBuffer = bb.asFloatBuffer();
        if (type == Type.EXTENDED) {
            rectBuffer.put(RECT_VERTS_EXTENDED);
        } else if (type == Type.FACE || type == Type.TRACKING_3D) {
            rectBuffer.put(RECT_VERTS_FACE);
        } else {
            rectBuffer.put(RECT_VERTS);
        }
        rectBuffer.position(0);
    }

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    @Override
    public void onSurfaceCreated() {
        compileShaders();
    }

    @Override
    public void onDrawFrame() {
        if (augmentationProgram == -1) {
            compileShaders();
        }

        if (this.projectionMatrix == null || this.viewMatrix == null) {
            return;
        }

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glUseProgram(augmentationProgram);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        GLES20.glVertexAttribPointer(positionSlot, 3, GLES20.GL_FLOAT, false, 0, rectBuffer);
        GLES20.glEnableVertexAttribArray(positionSlot);

        GLES20.glUniformMatrix4fv(projectionUniform, 1, false, this.projectionMatrix, 0);
        GLES20.glUniformMatrix4fv(modelViewUniform, 1, false, this.viewMatrix, 0);

        GLES20.glUniform3f(colorUniform, red, green, blue);

        float[] scaleMatrix = {
                xScale, 0.0f, 0.0f, 0.0f,
                0.0f, yScale, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        };

        GLES20.glUniformMatrix4fv(scaleUniform, 1, false, scaleMatrix, 0);

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glLineWidth(10.0f);

        GLES20.glDrawElements(GLES20.GL_LINE_LOOP, indices.length, GLES20.GL_UNSIGNED_SHORT, indicesBuffer);

        GLES20.glLineWidth(1.0f);
    }

    public void setColor(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    public float getXScale() {
        return xScale;
    }

    public void setXScale(float xScale) {
        this.xScale = xScale;
    }

    public float getYScale() {
        return yScale;
    }

    public void setYScale(float yScale) {
        this.yScale = yScale;
    }

    private void compileShaders() {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);
        augmentationProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(augmentationProgram, vertexShader);
        GLES20.glAttachShader(augmentationProgram, fragmentShader);
        GLES20.glLinkProgram(augmentationProgram);

        positionSlot = GLES20.glGetAttribLocation(augmentationProgram, "v_position");
        modelViewUniform = GLES20.glGetUniformLocation(augmentationProgram, "ModelView");
        projectionUniform = GLES20.glGetUniformLocation(augmentationProgram, "Projection");
        colorUniform = GLES20.glGetUniformLocation(augmentationProgram, "Color");
        scaleUniform = GLES20.glGetUniformLocation(augmentationProgram, "Scale");
    }


    public enum Type {
        FACE, STANDARD, EXTENDED, TRACKING_3D
    }
}
