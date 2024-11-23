# TileGenerator Class Documentation ([`TileGenerator.java`](/src/game/Display/TileGenerator.java))

The `TileGenerator` class in this file provides functionality for generating tiles with texture coordinates. It uses matrices to represent tile positions and transformations, as well as texture mappings for rendering.

---

#### Constructor:
- **`TileGenerator(float scale)`**  
  Initializes the `TileGenerator` with a specified scale factor.  
  - `scale`: The scale factor for the tile generator. Must be greater than 0.  

---

#### Methods:
- **`void generateTile(Shader s, int x, int y, int u, int v)`**  
  Generates a tile at the specified position with the given texture coordinates. The tile's position is scaled by the `scale` factor.  
  - `s`: The shader to add the vertex and texture coordinates to.  
  - `x`: The x-coordinate of the tile position.  
  - `y`: The y-coordinate of the tile position.  
  - `u`: The u texture coordinate.  
  - `v`: The v texture coordinate.

- **`Matrix3x2f translateMatrix(Matrix3x2f m, float x, float y)`**  
  Translates the given matrix by the specified x and y amounts.  
  - `m`: The matrix to be translated.  
  - `x`: The amount to translate along the x-axis.  
  - `y`: The amount to translate along the y-axis.  
  - **Returns**: A new translated matrix.

---

#### Matrix Definitions:
- **`TOP_LEFT`**: A `Matrix3x2f` representing the top-left corner of the tile, initialized based on the `scale` factor.
- **`BOTTOM_RIGHT`**: A `Matrix3x2f` representing the bottom-right corner of the tile, initialized based on the `scale` factor.
- **`TC`**: A `Matrix4x3f` matrix storing combined texture coordinates for the tile, defined as:
  $$
  \text{TC} =
  \begin{bmatrix}
  0.0 & 1.0 \\
  1.0& 1.0 \\
  0.0 & 0.0 \\
  1.0 & 1.0 \\
  1.0 & 0.0 \\
  0.0 & 0.0
  \end{bmatrix}
  $$

---

#### Example Usage:
```java
TileGenerator tileGen = new TileGenerator(0.1f);
Shader shader = new Shader();
tileGen.generateTile(shader, 5, 3, 0, 0); // Generate tile at position (5, 3) with texture coordinates (0, 0)
```

### Notes
- The `TileGenerator` class assumes a 2D tile grid for positioning.
- Texture coordinates are mapped from two 2x2 matrices (`TC0A`, `TC0B`, `TC1A`, `TC1B`) and stored in a single 4x4 matrix (`TC`).