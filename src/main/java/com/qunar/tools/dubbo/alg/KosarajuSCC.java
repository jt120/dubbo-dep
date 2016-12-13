package com.qunar.tools.dubbo.alg;

/**
 * since 2016/12/8.
 */
public class KosarajuSCC {

    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSCC(Digraph g) {
        marked = new boolean[g.V()];
        id = new int[g.V()];
        DepthFirstOrder order = new DepthFirstOrder(g.reverse());
        for (int s : order.getReversePost()) {
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
        }
    }

    public void dfs(Digraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int id(int v) {
        return id[v];
    }

    public boolean stronglyConnect(int v, int w) {
        return id[v] == id[w];
    }

    public int getCount() {
        return count;
    }

    public Queue<Integer>[] group() {
        Queue<Integer>[] queues = new Queue[getCount()];
        for (int i = 0; i < getCount(); i++) {
            queues[i] = new Queue<>();
        }

        for (int v = 0; v < id.length; v++) {
            queues[id(v)].enqueue(v);
        }
        return queues;
    }

    public static void main(String[] args) throws Exception {
        Digraph digraph = new Digraph(new In("D:\\project\\a\\algs-student\\algs4-data\\tinyDG.txt"));
        KosarajuSCC kosarajuSCC = new KosarajuSCC(digraph);
        System.out.println(kosarajuSCC.getCount());

        Queue<Integer>[] queues = new Queue[kosarajuSCC.getCount()];
        for (int i = 0; i < kosarajuSCC.getCount(); i++) {
            queues[i] = new Queue<>();
        }

        for (int v = 0; v < digraph.V(); v++) {
            queues[kosarajuSCC.id(v)].enqueue(v);
        }

        for (Queue<Integer> queue : queues) {
            for (int i : queue) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
