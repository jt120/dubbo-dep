package com.qunar.tools.dubbo.alg;


/**
 * since 2016/12/8.
 */
public class DirectedCycle {

    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack;
    private Stack<Integer> cycle;


    public DirectedCycle(Digraph g) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];
        for (int i = 0; i < g.V(); i++) {
            if (!marked[i]) {
                dfs(g, i);
            }
        }
    }

    public void dfs(Digraph g, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : g.adj(v)) {
            if (hasCycle()) {
                return;
            }

            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public static void main(String[] args) throws Exception {
        Digraph digraph = new Digraph(new In("D:\\project\\a\\algs-student\\algs4-data\\tinyDAG.txt"));

        DirectedCycle c = new DirectedCycle(digraph);
        if (c.hasCycle()) {
            for (int i : c.cycle()) {
                System.out.print(i + " ");
            }
        } else {
            System.out.println("no cycle");
        }

    }
}
