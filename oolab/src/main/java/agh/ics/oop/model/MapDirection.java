package agh.ics.oop.model;

public enum MapDirection {
    NORTH("North", new Vector2d(0, 1)),
    NORTHEAST("NorthEast", new Vector2d(1, 1)),
    NORTHWEST("NorthWest", new Vector2d(-1, 1)),
    EAST("East", new Vector2d(1, 0)),
    SOUTH("South", new Vector2d(0, -1)),
    SOUTHEAST("SouthEast", new Vector2d(1, -1)),
    SOUTHWEST("SouthWest", new Vector2d(-1, -1)),
    WEST("West", new Vector2d(-1, 0));

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
    public MapDirection next(){
        return switch (this){
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> WEST;
        };
    }
    public MapDirection previous(){
        return switch (this){
            case NORTH -> NORTHWEST;
            case NORTHWEST -> WEST;
            case WEST -> SOUTHWEST;
            case SOUTHWEST -> SOUTH;
            case SOUTH -> SOUTHEAST;
            case SOUTHEAST -> EAST;
            case EAST -> NORTHEAST;
            case NORTHEAST -> NORTH;
        };
    }
    public Vector2d toUnitVector(){
        return new Vector2d(unitVector.getX(), unitVector.getY());
    }
}
