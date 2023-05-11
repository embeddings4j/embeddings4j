package dev.embeddings4j.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BruteForceSearchVectorIndexTest {
    private BruteForceSearchVectorIndex vectorIndex;

    @BeforeEach
    public void setUp() {
        vectorIndex = new BruteForceSearchVectorIndex(3);
    }

    @Test
    public void testFindNearest() {
        vectorIndex.addItem(0, new double[]{1.0f, 2.0f, 3.0f});
        vectorIndex.addItem(1, new double[]{4.0f, 5.0f, 6.0f});
        vectorIndex.addItem(2, new double[]{7.0f, 8.0f, 9.0f});

        List<Integer> nearestIndices = vectorIndex.searchNearest(new double[]{1.5f, 2.5f, 3.5f}, 2);

        assertEquals(2, nearestIndices.size());

        assertEquals(0, nearestIndices.get(0));
        assertEquals(1, nearestIndices.get(1));
    }
}