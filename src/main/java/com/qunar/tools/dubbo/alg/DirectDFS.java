package com.qunar.tools.dubbo.alg;

/**
 * since 2016/12/8.
 */
public class DirectDFS {

    private boolean[] marked;

    public DirectDFS(Digraph g, int s) {
        this.marked = new boolean[g.V()];
        dsf(g, s);
    }

    public DirectDFS(Digraph g, Iterable<Integer> sources) {
        this.marked = new boolean[g.V()];
        for (int s : sources) {
            if (!marked[s]) {
                dsf(g, s);
            }
        }
    }

    public void dsf(Digraph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dsf(g, w);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public static void main(String[] args) throws Exception {
        Digraph digraph = new Digraph(new In("D:\\project\\a\\algs-student\\algs4-data\\tinyDG.txt"));

        DirectDFS directDFS = new DirectDFS(digraph, 0);
        for (int v = 0; v < digraph.V(); v++) {

            if (directDFS.marked(v)) {
                System.out.print(v + " ");
            }
        }

    }
}
