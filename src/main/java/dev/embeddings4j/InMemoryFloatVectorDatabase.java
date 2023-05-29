package dev.embeddings4j;

public class InMemoryFloatVectorDatabase extends InMemoryVectorDatabase<Integer, String, Float> {

    public InMemoryFloatVectorDatabase(int dimensions) {
        super(dimensions, new FloatCosineDistance());
    }
}
