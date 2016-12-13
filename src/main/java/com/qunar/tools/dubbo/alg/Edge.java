package com.qunar.tools.dubbo.alg;

/**
 * since 2016/12/9.
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }


    public int either() {
        return v;
    }

    public double getWeight() {
        return weight;
    }

    public int other(int v) {
        return v == this.v ? w : v;
    }

    @Override
    public int compareTo(Edge that) {
        if (this.getWeight() < that.getWeight()) return -1;
        else if (this.getWeight() > that.getWeight()) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
