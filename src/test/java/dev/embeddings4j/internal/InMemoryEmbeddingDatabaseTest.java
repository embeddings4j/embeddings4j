package dev.embeddings4j.internal;

import dev.embeddings4j.Embedding;
import dev.embeddings4j.InMemoryVectorDatabase;
import dev.embeddings4j.VectorSearchQuery;
import dev.embeddings4j.VectorSearchResult;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryEmbeddingDatabaseTest {

    private InMemoryVectorDatabase vectorDatabase;

    @BeforeEach
    public void setUp() {
        vectorDatabase = new InMemoryVectorDatabase(2);
    }

    @Test
    public void testSearchNearest() {
        Embedding<Integer, String, Float> vector1 = new Embedding<>(1, "text1", asList(1.0f, 3.0f));

        Embedding<Integer, String, Float> vector2 = new Embedding<>(2, "text2", asList(3.0f, 5.0f));
        Embedding<Integer, String, Float> vector3 = new Embedding<>(3, "text3", asList(2.0f, 2.0f));
        Embedding<Integer, String, Float> vector4 = new Embedding<>(4, "text4", asList(4.0f, 2.0f));
        Embedding<Integer, String, Float> vector5 = new Embedding<>(5, "text5", asList(3.0f, 1.0f));

        vectorDatabase.insert(vector2);
        vectorDatabase.insert(vector3);
        vectorDatabase.insert(vector4);
        vectorDatabase.insert(vector5);

        VectorSearchQuery<Integer, String, Float> query = VectorSearchQuery.<Integer, String, Float>builder()
                .targetVector(vector1)
                .numberOfResults(2)
                .build();


        List<VectorSearchResult<Integer, String, Float>> searchResults = vectorDatabase.searchNearest(query);


        val resultVectors = searchResults.stream()
                .map(VectorSearchResult::getVector)
                .collect(toList());

        assertThat(searchResults).hasSize(2);
        assertThat(resultVectors).containsExactly(vector2, vector3);
    }
}