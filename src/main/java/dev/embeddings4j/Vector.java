package dev.embeddings4j;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Vector {
    private int id;
    private double[] values;
}
