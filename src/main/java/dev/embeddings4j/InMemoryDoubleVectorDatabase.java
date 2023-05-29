package dev.embeddings4j;

public class InMemoryDoubleVectorDatabase extends InMemoryVectorDatabase<Integer, String, Double> {

    public InMemoryDoubleVectorDatabase(int dimensions) {
        super(dimensions, new DoubleCosineDistance());
    }
}
