package game.Display;

public final class ShaderObject {
    public Shader shader;
    public Mesh mesh;

    public ShaderObject(Shader shader, Mesh mesh) {
        this.mesh = mesh;
        this.shader = shader;
    }

    public void updateShader(Shader shader) {
        this.shader = shader;
    }

    public void updateMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Shader getShader() {
        return this.shader;
    }

    public Mesh getMesh() {
        return this.mesh;
    }
}
