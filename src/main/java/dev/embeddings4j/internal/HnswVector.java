package dev.embeddings4j.internal;

import com.github.jelmerk.knn.Item;
import dev.embeddings4j.Vector;

public class HnswVector implements Item<Integer, double[]> {
    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final double[] vector;

    public HnswVector(Integer id, double[] vector) {
        this.id = id;
        this.vector = vector;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public double[] vector() {
        return vector;
    }

    @Override
    public int dimensions() {
        return vector.length;
    }

    public static HnswVector from(Vector source) {
        return new HnswVector(source.getId(), source.getValues());
    }
}
