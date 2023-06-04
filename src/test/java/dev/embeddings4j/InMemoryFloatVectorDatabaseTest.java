package dev.embeddings4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryFloatVectorDatabaseTest {

    private InMemoryFloatVectorDatabase vectorDatabase;

    @BeforeEach
    public void setUp() {
        vectorDatabase = new InMemoryFloatVectorDatabase(2);
    }

    @Test
    public void testSearchNearest() {
        Embedding<Integer, String, Float> embedding1 = new Embedding<>(1, "text1", asList(1.0f, 3.0f));

        Embedding<Integer, String, Float> embedding2 = new Embedding<>(2, "text2", asList(3.0f, 5.0f));
        Embedding<Integer, String, Float> embedding3 = new Embedding<>(3, "text3", asList(2.0f, 2.0f));
        Embedding<Integer, String, Float> embedding4 = new Embedding<>(4, "text4", asList(4.0f, 2.0f));
        Embedding<Integer, String, Float> embedding5 = new Embedding<>(5, "text5", asList(3.0f, 1.0f));

        vectorDatabase.insert(embedding2);
        vectorDatabase.insert(embedding3);
        vectorDatabase.insert(embedding4);
        vectorDatabase.insert(embedding5);

        EmbeddingSearchQuery<Integer, String, Float> query = new EmbeddingSearchQuery(embedding1, 2);

        List<EmbeddingSearchResult<Integer, String, Float>> searchResults = vectorDatabase.searchNearest(query);


        List<Embedding<?, ?, ?>> resultVectors = searchResults.stream()
                .map(EmbeddingSearchResult::embedding)
                .collect(toList());

        assertThat(searchResults).hasSize(2);
        assertThat(resultVectors).containsExactly(embedding2, embedding3);
    }

    @Test
    void loadTest() {

        for (int i = 0; i < 5; i++) {

            int numberOfEmbeddings = 1000;
            int dimensions = (int) Math.pow(10, i);

            InMemoryFloatVectorDatabase vectorDatabase = new InMemoryFloatVectorDatabase(dimensions);

            List<Embedding<Integer, String, Float>> embeddings = new ArrayList<>();

            Random random = new Random();

            for (int j = 0; j < numberOfEmbeddings; j++) {
                int id = random.nextInt(Integer.MAX_VALUE);
                String text = "text" + random.nextInt(Integer.MAX_VALUE);

                List<Float> vector = random.ints(dimensions, 0, Integer.MAX_VALUE)
                        .asDoubleStream()
                        .mapToObj(d -> (float) d)
                        .collect(toList());

                embeddings.add(new Embedding<>(id, text, vector));
            }

            try {
                long now = System.currentTimeMillis();
                System.out.println("dimensions: " + dimensions);

                vectorDatabase.insertAll(embeddings);

                long time = System.currentTimeMillis() - now;

                System.out.println("time: " + time);
                System.out.println("k: " + (double) time / dimensions);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("done");
        }
    }
}