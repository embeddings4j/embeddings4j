package dev.embeddings4j;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VectorSearchQuery {
    private Vector targetVector;
    private int numberResults;
    private double distanceThreshold;
}
