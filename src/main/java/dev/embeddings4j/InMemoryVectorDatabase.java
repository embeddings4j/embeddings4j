package dev.embeddings4j;

import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryVectorDatabase implements VectorDatabase<Integer, String, Float> {

    private final HnswIndex<Integer, List<Float>, Embedding<Integer, String, Float>, Float> index;

    // TODO how to setup dimensions
    public InMemoryVectorDatabase(int dimensions) {

        this.index = HnswIndex
                .newBuilder(dimensions, new FloatCosineDistance(), 10_000) // TODO implement resizeable index
//                TODO other params?
                .build();
    }

    @Override
    public List<VectorSearchResult<Integer, String, Float>> searchNearest(VectorSearchQuery<Integer, String, Float> query) {
        List<SearchResult<Embedding<Integer, String, Float>, Float>> results = index.findNearest(query.getTargetVector().getVector(), query.getNumberOfResults());
        return results.stream()
                .map(result -> new VectorSearchResult<>(result.item(), result.distance()))
                .collect(Collectors.<VectorSearchResult<Integer, String, Float>>toList());
    }

    @Override
    public void insert(Embedding<Integer, String, Float> vector) {
        index.add(vector);
    }

    @Override
    public void insertAll(Collection<Embedding<Integer, String, Float>> vector) throws InterruptedException {
        index.addAll(vector); // TODO make sure this is blocking
    }

}
