# Shader Info (Shader.java)

## Triangles

### Adding Triangles

Coordinates for triangles must result in both triangles being :
- Right triangles
- Intersection of altitude and leg being in ONLY:
- - Top Left
- - Bottom Right

Triangle Coords : 
- Top Left : 
- - Vert Coords : { -1f, -1f, -1f, 1f, 1f, 1f }
- - Tex Coords : { 0f, 0f, -1f, 0f, -1f, -1f, 0f, -1f }
- Bottom Right : 
- - Vert Coords : { -1f, -1f, 1f, -1f, 1f, 1f }
- - Tex Coords : { 0f, 0f, 1f, 0f, 1f, -1f, 0f, -1f }