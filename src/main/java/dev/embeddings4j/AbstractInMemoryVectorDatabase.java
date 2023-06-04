package dev.embeddings4j;

import com.github.jelmerk.knn.DistanceFunction;
import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractInMemoryVectorDatabase<IdType, ContentType, VectorType extends Comparable<VectorType>> {

    private final HnswIndex<IdType, List<VectorType>, Embedding<IdType, ContentType, VectorType>, VectorType> index;

    // TODO think about how to define dimensions
    protected AbstractInMemoryVectorDatabase(int dimensions, DistanceFunction<List<VectorType>, VectorType> distanceFunction) {
        // TODO implement resizeable index
        this.index = HnswIndex.newBuilder(dimensions, distanceFunction, 10_000)
//                TODO other params?
                .build();
    }

    public List<EmbeddingSearchResult<IdType, ContentType, VectorType>> searchNearest(EmbeddingSearchQuery<IdType, ContentType, VectorType> query) {
        List<SearchResult<Embedding<IdType, ContentType, VectorType>, VectorType>> results = index.findNearest(query.reference().vector(), query.maxResults());
        return results.stream()
                .map(result -> new EmbeddingSearchResult<>(result.item(), result.distance()))
                // TODO make sure it is sorted by distance DESC
                .collect(Collectors.<EmbeddingSearchResult<IdType, ContentType, VectorType>>toList());
    }

    public void insert(Embedding<IdType, ContentType, VectorType> vector) {
        index.add(vector);
    }

    public void insertAll(Collection<Embedding<IdType, ContentType, VectorType>> vector) throws InterruptedException {
        index.addAll(vector);
    }

    public int size() {
        return index.size();
    }
}
