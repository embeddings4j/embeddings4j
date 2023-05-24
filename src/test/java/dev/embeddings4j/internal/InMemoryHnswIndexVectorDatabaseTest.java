package dev.embeddings4j.internal;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.hnsw.HnswIndex;
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

class InMemoryHnswIndexVectorDatabaseTest {
    private InMemoryHnswIndexVectorDatabase vectorDatabase;

    @BeforeEach
    public void setUp() {
        vectorDatabase = new InMemoryHnswIndexVectorDatabase(HnswIndex
                .newBuilder(3, DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, 400)
//                .withM(16)
//                .withEf(200)
//                .withEfConstruction(200)
                .build());
    }

    @Test
    public void testSearchNearest() {
        testSearchNearestBy(Arrays.asList(0, 1), 2);
    }

    @Test
    public void testThresholdDistanceIsApplied() {
        testSearchNearestBy(Arrays.asList(0, 1), 3);
    }

    private void testSearchNearestBy(List<Integer> expectedResults, Integer numberResults) {
        vectorDatabase.insert(new Vector(0, new double[]{1.0f, 1.0f, 1.0f}));
        vectorDatabase.insert(new Vector(1, new double[]{2.0f, 2.0f, 2.0f}));
        vectorDatabase.insert(new Vector(2, new double[]{150.0f, 15.0f, 15.0f}));
        vectorDatabase.insert(new Vector(3, new double[]{160.0f, 16.0f, 16.0f}));

        VectorSearchQuery query = VectorSearchQuery.builder()
                .targetVector(new Vector(4, new double[]{3.0f, 3.0f, 3.0f}))
                .numberResults(numberResults)
                .distanceThreshold(20.0d)
                .build();
        List<VectorSearchResult> searchResults = vectorDatabase.searchNearest(query);

        assertEquals(2, searchResults.size());
        assertTrue(expectedResults.containsAll(searchResults.stream().map(VectorSearchResult::getId).collect(Collectors.toList())));
    }
}