package dev.embeddings4j;

import java.util.List;

public interface VectorIndex {
    void addItem(int index, float[] values);
    List<Integer> searchNearest(float[] values, int numResults);
}
