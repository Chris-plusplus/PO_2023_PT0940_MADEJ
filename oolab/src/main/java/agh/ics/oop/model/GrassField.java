package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grassMap = new HashMap<>();
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;
    private boolean boundsNeedUpdate = true;

    // niedeterministyczny konstruktor
    public GrassField(int n){
        this(n, getSeed());
    }

    // deterministyczny konstruktor
    public GrassField(int n, long seed){
        this(n, new Random(seed));
    }

    private GrassField(int n, Random random){
        generateField(n, random);
    }

    private static long getSeed(){
        Random random = new Random();
        long seed = random.nextLong();
        System.out.println(seed);
        return seed;
    }
    // max liczba losowań 2n-1, n oryginalnych, n-1 poprawkowych
    private void generateField(int n, Random random){
        int flooredBound = (int)Math.floor(Math.sqrt(10.0 * (double)n));

        for(int i = 0; i != n; ++i){
            int x = random.nextInt(flooredBound + 1);
            int y = random.nextInt(flooredBound + 1);
            Vector2d pos = new Vector2d(x, y);
            if(grassMap.containsKey(pos)){
                pos = findNearestEmpty(pos, flooredBound + 1, flooredBound + 1, random);
            }
            Grass g = new Grass(pos);
            grassMap.put(pos, g);
        }
    }
    // znajduje wolne pozycje na kolejnych obwodach kwadratów (2n+1)X(2n+1) o środku pos
    // i wybiera losowo jedną z nich
    // nie sprawdza dalszych kwadratów, jeśli znaleziono pozycje na ostatnim
    // kwadraty są ograniczone w osi X [0, xBound) i osi Y [0, yBound)
    private Vector2d findNearestEmpty(Vector2d pos, int xBound, int yBound, Random random){
        int _x = pos.getX();
        int _y = pos.getY();
        for(int d = 1; d != Math.max(xBound, yBound) ; ++d){
            List<Vector2d> found = new ArrayList<>();
            int xBegin = _x - d;
            int yBegin = _y - d;
            int xEnd = _x + d;
            int yEnd = _y + d;

            // [xBegin, xEnd]
            // [yBegin, yEnd]

            for(int x = Math.max(0, xBegin); x <= xEnd && x < xBound; ++x) {
                if(yBegin >= 0){
                    Vector2d v = new Vector2d(x, yBegin);
                    if (!grassMap.containsKey(v)){
                        found.add(v);
                        //return v;
                    }
                }
                if(yEnd < yBound){
                    Vector2d v = new Vector2d(x, yEnd);
                    if(!grassMap.containsKey(v)){
                        //return v;
                        found.add(v);
                    }
                }
            }
            for(int y = Math.max(0, yBegin); y <= yEnd && y < yBound; ++y) {
                if(xBegin >= 0){
                    Vector2d v = new Vector2d(xBegin, y);
                    if (!grassMap.containsKey(v)){
                        //return v;
                        found.add(v);
                    }
                }
                if(xEnd < xBound){
                    Vector2d v = new Vector2d(xEnd, y);
                    if(!grassMap.containsKey(v)){
                        //return v;
                        found.add(v);
                    }
                }
            }
            if(!found.isEmpty()){
                return found.get(random.nextInt(found.size()));
            }
        }
        // sytuacja niemożliwa, ale kompilator się czepiał
        return null;
    }

    private void updateBounds(){
        int xMax = 0;
        int xMin = Integer.MAX_VALUE;
        int yMax = 0;
        int yMin = Integer.MAX_VALUE;

        for (Animal animal : animalMap.values()){
            xMax = Math.max(xMax, animal.getPosition().getX());
            yMax = Math.max(yMax, animal.getPosition().getY());
            xMin = Math.min(xMin, animal.getPosition().getX());
            yMin = Math.min(yMin, animal.getPosition().getY());
        }
        for (Grass grass : grassMap.values()){
            xMax = Math.max(xMax, grass.getPosition().getX());
            yMax = Math.max(yMax, grass.getPosition().getY());
            xMin = Math.min(xMin, grass.getPosition().getX());
            yMin = Math.min(yMin, grass.getPosition().getY());
        }

        lowerLeftCorner = new Vector2d(xMin, yMin);
        upperRightCorner = new Vector2d(xMax, yMax);
        boundsNeedUpdate = false;
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !animalMap.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position){
        WorldElement animal = animalMap.get(position);
        if(animal == null){
            return grassMap.get(position);
        }
        return animal;
    }

    @Override
    public Vector2d getUpperRightCorner() {
        if(boundsNeedUpdate){
            updateBounds();
        }
        return upperRightCorner;
    }

    @Override
    public Vector2d getLowerLeftCorner() {
        if(boundsNeedUpdate){
            updateBounds();
        }
        return lowerLeftCorner;
    }

    @Override
    protected void onPlace(Animal placed) {
        cachedDrawing = "";
        boundsNeedUpdate = true;
    }
    @Override
    protected void onPositionChanged(Animal moved, Vector2d oldPosition) {
       cachedDrawing = "";
       boundsNeedUpdate = true;
    }
    @Override
    protected void onOrientationChanged(Animal moved, MapDirection oldOrientation) {
        cachedDrawing = "";
    }

    @Override
    public List<WorldElement> getElements() {
        return Collections.<WorldElement>unmodifiableList(Stream.concat(super.getElements().stream(), grassMap.values().stream()).toList());
    }
}
