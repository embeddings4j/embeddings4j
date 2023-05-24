package dev.embeddings4j.internal;

import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;
import dev.embeddings4j.Vector;
import dev.embeddings4j.VectorDatabase;
import dev.embeddings4j.VectorSearchQuery;
import dev.embeddings4j.VectorSearchResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryHnswIndexVectorDatabase implements VectorDatabase {
    private final HnswIndex index;

    public InMemoryHnswIndexVectorDatabase(HnswIndex index) {
        this.index = index;
    }

    @Override
    public void insert(Vector vector) {
        index.add(HnswVector.from(vector));
    }

    @Override
    public Vector get(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<VectorSearchResult> searchNearest(VectorSearchQuery query) {
        List<SearchResult<HnswVector, Double>> results = index.findNearest(query.getTargetVector().getValues(), query.getNumberResults());
        return results.stream()
                .filter(i -> Math.abs(i.distance()) <= Math.abs(query.getDistanceThreshold()))
                .map(i -> new VectorSearchResult(i.item().id(), i.item().vector(), i.distance()))
                .collect(Collectors.toList());
    }
}
