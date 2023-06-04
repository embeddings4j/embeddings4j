package dev.embeddings4j;

import com.github.jelmerk.knn.Item;

import java.util.List;

// TODO embedding or VectorType?
public class Embedding<IdType, ContentType, VectorType> implements Item<IdType, List<VectorType>> {

    IdType id;
    ContentType content;
    List<VectorType> vector;

    public Embedding(IdType id, ContentType content, List<VectorType> vector) {
        this.id = id;
        this.content = content;
        this.vector = vector;
    }

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
