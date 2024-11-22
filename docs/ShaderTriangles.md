# Triangles

## Adding Triangles

To incorporate triangles into your shader:
- **Both triangles must be right triangles.**
- The intersection of the altitude and a leg must occur in one of the following positions:
  - **Top Left**
  - **Bottom Right**

## Point Criteria

Ensure the following requirements are met for defining triangle points:
- Points must be represented as **float values**.
- Each triangle must include **exactly three points**.

## Notes:
- The `TileGenerator` class provides a structured and reusable approach to creating triangles.