package dev.embeddings4j;

import java.util.List;

public interface VectorDatabase {
    void insert(Vector vector);

    Vector get(int id);

    List<VectorSearchResult> searchNearest(VectorSearchQuery query);
}
