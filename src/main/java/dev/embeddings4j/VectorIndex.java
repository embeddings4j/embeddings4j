package dev.embeddings4j;

import java.util.List;

public interface VectorIndex {
    void addItem(int index, double[] values);
    List<Integer> searchNearest(double[] values, int numResults);
}
