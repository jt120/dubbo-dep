package com.qunar.tools.dubbo.alg;

import java.util.Iterator;

/**
 * since 2016/12/9.
 */
public class EdgeWeightedGrap {

    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGrap(int v) {
        this.V = v;
        adj = new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedGrap(In in) {
        this(in.readInt());
        int c = in.readInt();
        for (int i = 0; i < c; i++) {
            Edge e = new Edge(in.readInt(), in.readInt(), in.readDouble());
            this.addEdge(e);
        }
    }

    public int V() {
        return V;

    }

    public int E() {
        return E;
    }


    public void addEdge(Edge e) {
        this.adj[e.either()].add(e);
        this.adj[e.other(e.either())].add(e);
        this.E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> bag = new Bag<>();
        for (int i = 0; i < V; i++) {
            for (Edge e : this.adj(i)) {
                if(i<e.other(i)) bag.add(e);
            }
        }
        return bag;
    }


}
