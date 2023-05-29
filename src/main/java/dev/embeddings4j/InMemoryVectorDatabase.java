package dev.embeddings4j;

import com.github.jelmerk.knn.DistanceFunction;
import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InMemoryVectorDatabase<IdType, ContentType, VectorType extends Comparable<VectorType>> {

    private final HnswIndex<IdType, List<VectorType>, Embedding<IdType, ContentType, VectorType>, VectorType> index;

    // TODO how to setup dimensions
    protected InMemoryVectorDatabase(int dimensions, DistanceFunction<List<VectorType>, VectorType> distanceFunction) {
        // TODO which distance by default? check pinecone and chroma and FAISS and Annoy

        this.index = HnswIndex.newBuilder(dimensions, distanceFunction, 10_000) // TODO implement resizeable index
//                TODO other params?
                .build();
    }

    public List<VectorSearchResult<IdType, ContentType, VectorType>> searchNearest(VectorSearchQuery<IdType, ContentType, VectorType> query) {
        List<SearchResult<Embedding<IdType, ContentType, VectorType>, VectorType>> results = index.findNearest(query.getTargetVector().getVector(), query.getNumberOfResults());
        return results.stream()
                .map(result -> new VectorSearchResult<>(result.item(), result.distance()))
                .collect(Collectors.<VectorSearchResult<IdType, ContentType, VectorType>>toList());
    }

    public void insert(Embedding<IdType, ContentType, VectorType> vector) {
        index.add(vector);
    }

    public void insertAll(Collection<Embedding<IdType, ContentType, VectorType>> vector) throws InterruptedException {
        index.addAll(vector); // TODO make sure this is blocking
    }
}
