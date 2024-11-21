package game.Display;

import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private final FloatBuffer POINT_POSITIONS = MemoryUtil.memAllocFloat((int) Math.pow(2,24));
    private final int MESH_SIZE_COORDINATES_INCREMENT = 6;

    private int POINT_POSITIONS_CAPACITY = 0;
    public int MESH_SIZE_COORDINATES = 0;

    private boolean ALREADY_INITIALIZED = false;

    public Mesh() {
        checkInitialization();
        ALREADY_INITIALIZED = true;
    }

    public Mesh(float[] pointPositions) {
        checkInitialization();
        this.POINT_POSITIONS.put(pointPositions);
        POINT_POSITIONS_CAPACITY += MESH_SIZE_COORDINATES_INCREMENT;
        MESH_SIZE_COORDINATES += MESH_SIZE_COORDINATES_INCREMENT;
        ALREADY_INITIALIZED = true;
    }

    public Mesh(Matrix3x2f pointPositions) {
        checkInitialization();
        pointPositions.get(POINT_POSITIONS_CAPACITY, POINT_POSITIONS);
        POINT_POSITIONS_CAPACITY += MESH_SIZE_COORDINATES_INCREMENT;
        MESH_SIZE_COORDINATES += MESH_SIZE_COORDINATES_INCREMENT;
        ALREADY_INITIALIZED = true;
    }

    private void checkInitialization() {
        if (ALREADY_INITIALIZED) {
            throw new IllegalStateException("Mesh has already been initialized, cannot re-initialize mesh.");
        }
    }

    public void addTriangle(Matrix3x2f pointPositions) {
        pointPositions.get(POINT_POSITIONS_CAPACITY, POINT_POSITIONS);
        POINT_POSITIONS_CAPACITY += MESH_SIZE_COORDINATES_INCREMENT;
        MESH_SIZE_COORDINATES += MESH_SIZE_COORDINATES_INCREMENT;
    }

    public FloatBuffer getMesh() {
        return POINT_POSITIONS;
    }

    public int getLength() {
        return POINT_POSITIONS.capacity();
    }
}