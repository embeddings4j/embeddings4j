package dev.embeddings4j;

public class EmbeddingSearchResult<IdType, ContentType, VectorType> {

    Embedding<IdType, ContentType, VectorType> embedding;
    VectorType distance;

    public EmbeddingSearchResult(Embedding<IdType, ContentType, VectorType> embedding,
                                 VectorType distance) {
        this.embedding = embedding;
        this.distance = distance;
    }

    public Embedding<IdType, ContentType, VectorType> embedding() {
        return embedding;
    }

    public VectorType distance() {
        return distance;
    }
}
