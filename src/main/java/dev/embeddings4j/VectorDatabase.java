package dev.embeddings4j;

import java.util.Collection;
import java.util.List;

// TODO Store?
public interface VectorDatabase<IdType, ContentType, VectorType> {

    void insert(Embedding<IdType, ContentType, VectorType> vector); // TODO add?

    void insertAll(Collection<Embedding<IdType, ContentType, VectorType>> vector) throws InterruptedException;

    List<VectorSearchResult<IdType, ContentType, VectorType>> searchNearest(VectorSearchQuery<IdType, ContentType, VectorType> query);
}
