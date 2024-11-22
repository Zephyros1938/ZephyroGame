# Shader Class Documentation ([`Shader.java`](..\src\game\Display\Shader.java))

The `Shader` class in this file provides a framework for managing OpenGL shaders, including their buffers, vertex attributes, and uniforms. This class facilitates the handling of vertex and texture coordinate buffers and simplifies the process of sending data to the GPU.

#### Class-Level Constants:
- **`SHADER_COORD_LEN`**: Defines the number of components per vertex coordinate (2D).
- **`SHADER_TEX_COORD_LEN`**: Defines the number of components per texture coordinate (2D).

---

#### Constructor:
- **`Shader(int SHADER_ATTRIBUTE_LEN)`**  
  Initializes the shader program and sets the length of attributes.  
  - `SHADER_ATTRIBUTE_LEN`: Length of the attributes per vertex.

---

#### Methods:
- **`void use()`**  
  Activates the shader program for rendering.

- **`void init()`**  
  Initializes the VAO (Vertex Array Object) by binding it.

- **`void initShaderBuffers()`**  
  Sets up and initializes the VBO (Vertex Buffer Object) and TBO (Texture Buffer Object).

- **`void addVertCoord(Matrix3x2f m)`**  
  Adds vertex coordinates to the VBO buffer from a 3x2 matrix.

- **`void addTexCoord(Matrix2f m0, Matrix2f m1)`**  
  Adds texture coordinates to the TBO buffer from two 2x2 matrices.

- **`void addVertexCoords(FloatBuffer vertices)`**  
  Uploads a buffer of vertex coordinates to the GPU.

- **`void addTexCoords(float[] TEX_COORDS)`**  
  Uploads an array of texture coordinates to the GPU.

- **`void addVertexAttribPointer(int SIZE)`**  
  Configures the shader attribute pointer for a vertex attribute of the specified size.

- **`void addUniformAttrib1f(float V0, CharSequence NAME)`**  
  Sends a float uniform variable to the shader.

- **`void addUniformAttrib2f(float V0, float V1, CharSequence NAME)`**  
  Sends a 2D vector uniform variable to the shader.

- **`void addUniformAttrib3f(float V0, float V1, float V2, CharSequence NAME)`**  
  Sends a 3D vector uniform variable to the shader.

- **`void addUniformAttrib4f(float V0, float V1, float V2, float V3, CharSequence NAME)`**  
  Sends a 4D vector uniform variable to the shader.

- **`void bindTexture(Texture tex, int TEX_WIDTH, int TEX_HEIGHT)`**  
  Binds a texture to a texture unit for use in the shader.

- **`int getShaderSize()`**  
  Returns the total size of the shader's vertex attributes.

---

#### Buffers:
- **`VBO_BUFFER`**: Float buffer for vertex coordinates.
- **`TBO_BUFFER`**: Float buffer for texture coordinates.
- **`BUFFER_SIZE`**: Size of the buffers (2^16 floats by default).

---

#### Shader Objects:
- **`VAO`**: Vertex Array Object identifier.
- **`VBO`**: Vertex Buffer Object identifier.
- **`TBO`**: Texture Buffer Object identifier.
- **`SHADER_PROGRAM`**: The OpenGL shader program identifier.