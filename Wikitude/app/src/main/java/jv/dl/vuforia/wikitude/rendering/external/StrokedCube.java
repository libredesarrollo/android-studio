package jv.dl.vuforia.wikitude.rendering.external;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class StrokedCube extends Renderable{

    private static final String FRAGMENT_SHADER_CODE =
        "precision mediump float;" +
        "uniform vec3 Color;" +
        "void main()" +
        "{" +
        "  gl_FragColor = vec4(Color, 1.0);" +
        "}";

    private static final String VERTEX_SHADER_CODE =
        "attribute vec4 v_position;" +
        "uniform mat4 u_projection;" +
        "uniform mat4 u_modelView;" +
        "uniform mat4 u_scale;" +
        "uniform mat4 u_translation;" +
        "void main()" +
        "{" +
        "  gl_Position = u_projection * u_modelView * u_translation * u_scale * v_position;" +
        "}";

    private static final float CUBE_VERTICES[] = {
        -0.5f, -0.5f,  0.5f,
        -0.5f,  0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f, -0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
         0.5f,  0.5f,  0.5f,
         0.5f,  0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
         0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f,
        -0.5f,  0.5f,  0.5f,
        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
        -0.5f, -0.5f,  0.5f,
        -0.5f, -0.5f, -0.5f
    };

    private static final short CUBE_INDICES[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

    private final ShortBuffer indicesBuffer;
    private final FloatBuffer cubeBuffer;

    private int augmentationProgram = -1;
    private int positionSlot = -1;
    private int projectionUniform = -1;
    private int modelViewUniform = -1;
    private int scaleMatrixUniform = -1;
    private int colorUniform = -1;
    private int translateMatrixUniform = -1;

    private float red = 1.0f;
    private float green = 0.58f;
    private float blue = 0.16f;

    private float xScale = 1.0f;
    private float yScale = 1.0f;
    private float zScale = 1.0f;

    private float xTranslate = 0.0f;
    private float yTranslate = 0.0f;
    private float zTranslate = 0.0f;

    public StrokedCube() {
        ByteBuffer dlb = ByteBuffer.allocateDirect(CUBE_INDICES.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indicesBuffer = dlb.asShortBuffer();
        indicesBuffer.put(CUBE_INDICES);
        indicesBuffer.position(0);

        ByteBuffer bb = ByteBuffer.allocateDirect(CUBE_VERTICES.length * 4);
        bb.order(ByteOrder.nativeOrder());
        cubeBuffer = bb.asFloatBuffer();
        cubeBuffer.put(CUBE_VERTICES);
        cubeBuffer.position(0);
    }

    private static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
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

        GLES20.glUseProgram(augmentationProgram);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        GLES20.glVertexAttribPointer(positionSlot, 3, GLES20.GL_FLOAT, false, 0, cubeBuffer);
        GLES20.glEnableVertexAttribArray(positionSlot);

        GLES20.glUniformMatrix4fv(projectionUniform, 1, false, this.projectionMatrix, 0);
        GLES20.glUniformMatrix4fv(modelViewUniform, 1, false, this.viewMatrix, 0);

        GLES20.glUniform3f(colorUniform, red, green, blue);

        float[] scaleMatrix = {
                xScale,    0.0f,       0.0f,       0.0f,
                0.0f, yScale,    0.0f,       0.0f,
                0.0f,       0.0f, zScale,    0.0f,
                0.0f,       0.0f,       0.0f,       1.0f
        };

        float[] translateMatrix = {
                1.0f,               0.0f,               0.0f,               0.0f,
                0.0f,               1.0f,               0.0f,               0.0f,
                0.0f,               0.0f,               1.0f,               0.0f,
                xTranslate, yTranslate, zTranslate,        1.0f
        };

        GLES20.glUniformMatrix4fv(scaleMatrixUniform, 1, false, scaleMatrix, 0);
        GLES20.glUniformMatrix4fv(translateMatrixUniform, 1, false, translateMatrix, 0);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glLineWidth(10.0f);
        GLES20.glDrawElements(GLES20.GL_LINE_LOOP, CUBE_INDICES.length, GLES20.GL_UNSIGNED_SHORT, indicesBuffer);
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

    public float getZScale() {
        return zScale;
    }

    public void setZScale(float zScale) {
        this.zScale = zScale;
    }

    public float getXTranslate() {
        return xTranslate;
    }

    public void setXTranslate(float xTranslate) { this.xTranslate = xTranslate; }

    public float getYTranslate() {
        return yTranslate;
    }

    public void setYTranslate(float yTranslate) { this.yTranslate = yTranslate; }

    public float getZTranslate() {
        return zTranslate;
    }

    public void setZTranslate(float zTranslate) { this.zTranslate = zTranslate; }

    private void compileShaders() {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);
        augmentationProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(augmentationProgram, vertexShader);
        GLES20.glAttachShader(augmentationProgram, fragmentShader);
        GLES20.glLinkProgram(augmentationProgram);

        positionSlot = GLES20.glGetAttribLocation(augmentationProgram, "v_position");
        modelViewUniform = GLES20.glGetUniformLocation(augmentationProgram, "u_modelView");
        projectionUniform = GLES20.glGetUniformLocation(augmentationProgram, "u_projection");
        scaleMatrixUniform = GLES20.glGetUniformLocation(augmentationProgram, "u_scale");
        translateMatrixUniform = GLES20.glGetUniformLocation(augmentationProgram, "u_translation");
        colorUniform = GLES20.glGetUniformLocation(augmentationProgram, "Color");
    }
}
