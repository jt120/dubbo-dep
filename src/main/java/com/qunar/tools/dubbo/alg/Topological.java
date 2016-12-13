package com.qunar.tools.dubbo.alg;

/**
 * since 2016/12/8.
 */
public class Topological {

    private Iterable<Integer> order;

    public Topological(Digraph g) {
        DirectedCycle cycle = new DirectedCycle(g);
        if (!cycle.hasCycle()) {
            DepthFirstOrder o = new DepthFirstOrder(g);
            order = o.getReversePost();
        }
    }

    public Iterable<Integer> getOrder() {
        return order;
    }

    public boolean isDAG() {
        return order == null;
    }

    public static void main(String[] args) throws Exception {
        SymbolDigraph g = new SymbolDigraph("D:\\project\\a\\algs-student\\algs4-data\\jobs.txt", "/");
        Topological t = new Topological(g.G());
        for (int i : t.getOrder()) {
            System.out.println(g.name(i));
        }
    }
}
