package dev.embeddings4j;

import com.github.jelmerk.knn.Item;
import lombok.Value;

import java.util.List;

@Value
// TODO embedding or VectorType?
public class Embedding<IdType, ContentType, VectorType> implements Item<IdType, List<VectorType>> {

    IdType id;
    ContentType content;
    List<VectorType> vector;

    @Override
    public IdType id() {
        return id;
    }

    @Override
    public List<VectorType> vector() {
        return vector;
    }

    @Override
    public int dimensions() {
        return vector.size();
    }
}
