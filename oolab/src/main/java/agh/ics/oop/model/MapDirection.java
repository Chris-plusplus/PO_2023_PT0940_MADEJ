package agh.ics.oop.model;

public enum MapDirection {
    NORTH("Północ", new Vector2d(0, 1)),
    EAST("Wschód", new Vector2d(1, 0)),
    SOUTH("Południe", new Vector2d(0, -1)),
    WEST("Zachód", new Vector2d(-1, 0));

    private String stringRepresentation;
    private Vector2d unitVector;
    private MapDirection(String strRep, Vector2d uV){
        stringRepresentation = strRep;
        unitVector = uV;
    }

    @Override
    public String toString(){
        return stringRepresentation;
    }
    public String shortString() {
        return switch (this){
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }
    public MapDirection next(){
        return switch (this){
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
    public MapDirection previous(){
        return switch (this){
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }
    public Vector2d toUnitVector(){
        return new Vector2d(unitVector.getX(), unitVector.getY());
    }
}
