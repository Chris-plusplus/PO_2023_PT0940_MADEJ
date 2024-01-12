package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneCombiner {
    public static int randomExcluding(Random random, int begin, int end, List<Integer> excluded){
        int rangeLength = end - begin - excluded.size();
        int randomInt = random.nextInt(rangeLength) + begin;

        for (var e : excluded) {
            if (e > randomInt) {
                return randomInt;
            }

            randomInt++;
        }

        return randomInt;
    }

    public static int randomExcluding(Random random, int begin, int end, int excluded){
        return randomExcluding(random, begin, end, List.of(excluded));
    }

    public static List<Integer> combine(Animal parent1, Animal parent2){
        List<Integer> newGenes = new ArrayList<>();

        Animal stronger = parent1.getEnergy() < parent2.getEnergy() ? parent2 : parent1;
        Animal weaker = stronger == parent1 ? parent2 : parent1;

        int geneCountFromStronger = (stronger.getEnergy() * stronger.getGenes().size()) / (parent1.getEnergy() + parent2.getEnergy());

        Random random = new Random();
        boolean strongerFirst = random.nextBoolean();

        if(strongerFirst){
            newGenes = Stream.concat(
                    stronger.getGenes().subList(
                            0,
                            geneCountFromStronger).stream(),
                    weaker.getGenes().subList(
                            geneCountFromStronger,
                            weaker.getGenes().size()).stream()
            ).collect(Collectors.toList());
        }
        else{
            newGenes = Stream.concat(
                    weaker.getGenes().subList(
                            0,
                            weaker.getGenes().size() - geneCountFromStronger).stream(),
                    stronger.getGenes().subList(
                            weaker.getGenes().size() - geneCountFromStronger,
                            stronger.getGenes().size()).stream()
            ).collect(Collectors.toList());
        }

        int randomGenesCount = random.nextInt() % weaker.getGenes().size();
        List<Integer> randomizedIntegers = new ArrayList<>();

        for(int i = 0; i != randomGenesCount; ++i){
            int nextGene = randomExcluding(random, 0, weaker.getGenes().size(), randomizedIntegers);
            newGenes.set(nextGene, randomExcluding(random, 0, 8, newGenes.get(nextGene)));
            randomizedIntegers.add(nextGene);
        }

        return newGenes;
    }
}
