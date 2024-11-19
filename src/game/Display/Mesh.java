package game.Display;

import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private FloatBuffer POINT_POSITIONS = MemoryUtil.memAllocFloat((3 * 3 * 2 * 6) * 256);

    private int POINT_POSITIONS_CAPACITY = 0;

    private boolean ALREADY_INITIALIZED = false;

    private CharSequence NAME = "Unnamed";

    public Mesh(float[] pointPositions) {
        checkInitialization();
        this.POINT_POSITIONS.put(pointPositions).flip();
        POINT_POSITIONS_CAPACITY += 27;
        ALREADY_INITIALIZED = true;
    }

    public Mesh(Matrix3f pointPositions) {
        checkInitialization();
        pointPositions.get(POINT_POSITIONS_CAPACITY, POINT_POSITIONS);
        POINT_POSITIONS_CAPACITY += 27;
        ALREADY_INITIALIZED = true;
    }

    public Mesh(float[] pointPositions, CharSequence name) {
        checkInitialization();
        this.POINT_POSITIONS.put(pointPositions).flip();
        POINT_POSITIONS_CAPACITY += 27;
        this.NAME = name;
        this.ALREADY_INITIALIZED = true;
    }

    public Mesh(Matrix3f pointPositions, CharSequence name) {
        checkInitialization();
        pointPositions.get(POINT_POSITIONS_CAPACITY, POINT_POSITIONS);
        POINT_POSITIONS_CAPACITY += 27;
        this.NAME = name;
        this.ALREADY_INITIALIZED = true;
    }

    private void checkInitialization() {
        if (ALREADY_INITIALIZED) {
            throw new IllegalStateException("Mesh has already been initialized, cannot re-initialize mesh.");
        }
    }

    public void addTriangle(Matrix3f pointPositions) {
        pointPositions.get(POINT_POSITIONS_CAPACITY, POINT_POSITIONS);
        POINT_POSITIONS_CAPACITY += 27;
    }

    public FloatBuffer getMesh() {
        return POINT_POSITIONS;
    }
}