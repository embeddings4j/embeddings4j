package dev.embeddings4j.internal;

import dev.embeddings4j.Vector;
import dev.embeddings4j.VectorDatabase;
import dev.embeddings4j.VectorIndex;
import dev.embeddings4j.VectorSearchQuery;
import dev.embeddings4j.VectorSearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO:
// 1. concurrency
public class InMemoryVectorDatabase implements VectorDatabase {
    private final VectorIndex index;
    private final Map<Integer, Vector> vectorMap;

    public InMemoryVectorDatabase(VectorIndex index) {
        this.index = index;
        this.vectorMap = new HashMap<>();
    }

    public void insert(Vector vector) {
        vectorMap.put(vector.getId(), vector);
        index.addItem(vector.getId(), vector.getValues());
    }

    public Vector get(int id) {
        return vectorMap.get(id);
    }

    public List<VectorSearchResult> searchNearest(VectorSearchQuery query) {
        List<Integer> nearestNeighbors = index.searchNearest(query.getTargetVector().getValues(), query.getNumberResults());
        return convertToSearchResults(nearestNeighbors, query);
    }

    private List<VectorSearchResult> convertToSearchResults(List<Integer> nearestNeighbors, VectorSearchQuery query) {
        List<VectorSearchResult> searchResults = new ArrayList<>();
        double[] targetVectorValues = query.getTargetVector().getValues();

        for (Integer index : nearestNeighbors) {
            Vector closestVector = vectorMap.get(index);
            double[] closestVectorValues = closestVector.getValues();
            double distance = computeDistance(targetVectorValues, closestVectorValues);

            if (distance <= query.getDistanceThreshold()) {
                searchResults.add(new VectorSearchResult(index, closestVectorValues, distance));
            }
        }

        return searchResults;
    }

    private double computeDistance(double[] vector1, double[] vector2) {
        double sum = 0;
        for (int i = 0; i < vector1.length; i++) {
            double diff = vector1[i] - vector2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
