package dev.embeddings4j.internal;

import dev.embeddings4j.VectorIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BruteForceSearchVectorIndex implements VectorIndex {
    private final int vectorDimension;
    private final List<float[]> vectors;

    public BruteForceSearchVectorIndex(int vectorDimension) {
        this.vectorDimension = vectorDimension;
        this.vectors = new ArrayList<>();
    }

    public void addItem(int index, float[] values) {
        if (values.length != vectorDimension) {
            throw new IllegalArgumentException("Vector dimensions do not match");
        }
        vectors.add(index, values);
    }

    public List<Integer> searchNearest(float[] values, int numResults) {
        if (values.length != vectorDimension) {
            throw new IllegalArgumentException("Vector dimensions do not match");
        }

        PriorityQueue<DistanceIndexPair> maxHeap = new PriorityQueue<>((a, b) -> Float.compare(b.distance, a.distance));
        for (int i = 0; i < vectors.size(); i++) {
            float distance = euclideanDistance(values, vectors.get(i));
            maxHeap.offer(new DistanceIndexPair(distance, i));

            if (maxHeap.size() > numResults) {
                maxHeap.poll();
            }
        }

        List<Integer> nearestIndices = new ArrayList<>(numResults);
        while (!maxHeap.isEmpty()) {
            nearestIndices.add(0, maxHeap.poll().index);
        }

        return nearestIndices;
    }

    private float euclideanDistance(float[] vector1, float[] vector2) {
        float sum = 0;
        for (int i = 0; i < vector1.length; i++) {
            float diff = vector1[i] - vector2[i];
            sum += diff * diff;
        }
        return (float) Math.sqrt(sum);
    }

    private static class DistanceIndexPair {
        float distance;
        int index;

        DistanceIndexPair(float distance, int index) {
            this.distance = distance;
            this.index = index;
        }
    }
}