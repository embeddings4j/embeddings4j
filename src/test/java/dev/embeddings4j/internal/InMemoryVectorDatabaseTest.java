package dev.embeddings4j.internal;

import dev.embeddings4j.Vector;
import dev.embeddings4j.VectorSearchQuery;
import dev.embeddings4j.VectorSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryVectorDatabaseTest {
    private InMemoryVectorDatabase vectorDatabase;

    @BeforeEach
    public void setUp() {
        vectorDatabase = new InMemoryVectorDatabase(new BruteForceSearchVectorIndex(3));
    }

    @Test
    public void testSearchNearest() {
        vectorDatabase.insert(new Vector(0, new double[]{1.0f, 2.0f, 3.0f}));
        vectorDatabase.insert(new Vector(1, new double[]{4.0f, 5.0f, 6.0f}));
        vectorDatabase.insert(new Vector(2, new double[]{7.0f, 8.0f, 9.0f}));
        vectorDatabase.insert(new Vector(3, new double[]{9.0f, 11.0f, 12.0f}));

        VectorSearchQuery query = VectorSearchQuery.builder()
                .targetVector(new Vector(4, new double[]{5.5f, 7.5f, 6.5f}))
                .numberResults(2)
                .distanceThreshold(1000.0d)
                .build();
        List<VectorSearchResult> searchResults = vectorDatabase.searchNearest(query);

        assertEquals(2, searchResults.size());
        assertTrue(Arrays.asList(1, 2)
                .containsAll(searchResults.stream().map(VectorSearchResult::getId).collect(Collectors.toList())));
    }
}