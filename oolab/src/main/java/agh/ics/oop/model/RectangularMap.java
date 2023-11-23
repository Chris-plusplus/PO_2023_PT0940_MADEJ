package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {
    private static final Vector2d LOWER_LEFT_CORNER = new Vector2d(0, 0);
    private final Vector2d upperRightCorner;

    public RectangularMap(int width, int height){
        upperRightCorner = LOWER_LEFT_CORNER.add(new Vector2d(width - 1, height - 1));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(upperRightCorner) && position.follows(LOWER_LEFT_CORNER) && super.canMoveTo(position);
    }

    @Override
    public Vector2d getUpperRightCorner() {
        return upperRightCorner;
    }
    @Override
    // fajnie jak oba wierzchołki mają gettery
    public Vector2d getLowerLeftCorner() {
        return LOWER_LEFT_CORNER;
    }
}