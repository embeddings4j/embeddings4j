package dev.embeddings4j;

import lombok.Value;

@Value
public class VectorSearchResult<IdType, ContentType, VectorType> {

    Embedding<IdType, ContentType, VectorType> vector;
    VectorType distance;
}
