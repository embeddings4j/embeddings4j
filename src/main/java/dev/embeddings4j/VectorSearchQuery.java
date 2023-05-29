package dev.embeddings4j;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VectorSearchQuery<IdType, ContentType, VectorType> {

    Embedding<IdType, ContentType, VectorType> targetVector; // rename
    Integer numberOfResults;
}
