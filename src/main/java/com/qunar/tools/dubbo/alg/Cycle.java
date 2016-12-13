package com.qunar.tools.dubbo.alg;

import java.util.ArrayList;
import java.util.List;

/**
 * since 2016/12/7.
 */
public class Cycle {

    private boolean[] marked;
    private boolean hasCycle;
    private List<Pair> pair = new ArrayList<>();

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) {
                dfs(G, i, i);
            }
        }
    }

    public void dfs(Graph G, int v, int u) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, v);
            } else if (w != u) {
                pair.add(new Pair(w, u));
                System.out.println("cycle " + w + " and " + u);
                hasCycle = true;
            }
        }
    }

    public List<Pair> getPair() {
        return pair;
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
