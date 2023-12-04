package agh.ics.oop.model.util;

import agh.ics.oop.model.Grass;
import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    // można tez użyć iteratora z positions
    private static class PositionIterator implements Iterator<Vector2d> {
        private final Iterator<Vector2d> setIterator;

        private PositionIterator(java.util.Iterator<Vector2d> setIterator){
            this.setIterator = setIterator;
        }

        @Override
        public boolean hasNext() {
            return setIterator.hasNext();
        }
        @Override
        public Vector2d next() {
            return setIterator.next();
        }
    }

    private final Set<Vector2d> positions = new HashSet<>();

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount){
        this(maxWidth, maxHeight, grassCount, new Random());
    }

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount, long seed){
        this(maxWidth, maxHeight, grassCount, new Random(seed));
    }

    private RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount, Random random){
        generate(maxWidth, maxHeight, grassCount, random);
    }
    
    // max liczba losowań 2n-1, n oryginalnych, n-1 poprawkowych
    private void generate(int maxWidth, int maxHeight, int grassCount, Random random){
        for(int i = 0; i != grassCount; ++i){
            int x = random.nextInt(maxWidth + 1);
            int y = random.nextInt(maxHeight + 1);
            Vector2d pos = new Vector2d(x, y);
            if(positions.contains(pos)){
                pos = findNearestEmpty(pos, maxWidth + 1, maxHeight + 1, random);
            }
            positions.add(pos);
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
                    if (!positions.contains(v)){
                        found.add(v);
                        //return v;
                    }
                }
                if(yEnd < yBound){
                    Vector2d v = new Vector2d(x, yEnd);
                    if(!positions.contains(v)){
                        //return v;
                        found.add(v);
                    }
                }
            }
            for(int y = Math.max(0, yBegin); y <= yEnd && y < yBound; ++y) {
                if(xBegin >= 0){
                    Vector2d v = new Vector2d(xBegin, y);
                    if (!positions.contains(v)){
                        //return v;
                        found.add(v);
                    }
                }
                if(xEnd < xBound){
                    Vector2d v = new Vector2d(xEnd, y);
                    if(!positions.contains(v)){
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

    @Override
    public Iterator<Vector2d> iterator() {
        // można tez zwrócić samo positions.iterator()
        // wolałem jednak działać zgodnie z tym co pisze w poleceniu
        return new PositionIterator(positions.iterator());
    }
}
