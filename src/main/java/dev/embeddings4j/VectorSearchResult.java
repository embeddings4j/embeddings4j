package dev.embeddings4j;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VectorSearchResult {
    private int id;
    private double[] values;
    private double distance;
}
