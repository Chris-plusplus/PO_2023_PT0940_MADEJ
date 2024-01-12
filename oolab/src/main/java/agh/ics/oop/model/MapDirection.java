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
    private int num;

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
    public String shortString(){
        return switch (this){
            case NORTH -> "^";
            case NORTHEAST -> "^>";
            case EAST -> ">";
            case SOUTHEAST -> "v>";
            case SOUTH -> "v";
            case SOUTHWEST -> "<v";
            case WEST -> "<";
            case NORTHWEST -> "<^";
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

    public int getNum(){
        return switch (this){
            case NORTH -> 0;
            case NORTHEAST -> 1;
            case EAST -> 2;
            case SOUTHEAST -> 3;
            case SOUTH -> 4;
            case SOUTHWEST -> 5;
            case WEST -> 6;
            case NORTHWEST -> 7;
        };
    }

    public static MapDirection fromNum(int num){
        return switch (num % 8){
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalArgumentException("num must be 0 or greater");
        };
    }

    public MapDirection add(int num) {
        return fromNum(this.getNum() + num);
    }
}
