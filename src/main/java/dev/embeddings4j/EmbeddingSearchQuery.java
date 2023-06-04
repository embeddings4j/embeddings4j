package dev.embeddings4j;

public class EmbeddingSearchQuery<IdType, ContentType, VectorType> {

    private final Embedding<IdType, ContentType, VectorType> reference; // rename
    private final Integer maxResults;

    public EmbeddingSearchQuery(Embedding<IdType, ContentType, VectorType> reference, Integer maxResults) {
        this.reference = reference;
        this.maxResults = maxResults;
    }

    public Embedding<IdType, ContentType, VectorType> reference() {
        return reference;
    }

    public Integer maxResults() {
        return maxResults;
    }
}
