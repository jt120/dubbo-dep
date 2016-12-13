package com.qunar.tools.dubbo.alg;


/**
 * since 2016/12/7.
 */
public class SymbolDigraph {

    private ST<String,Integer> st;
    private String[] keys;
    private Digraph G;

    public SymbolDigraph(String stream, String sp) {
        st = new ST<>();
        In in = new In(stream);
        while (in.hasNextLine()) {
            final String[] split = in.readLine().split(sp);
            for (int i = 0; i < split.length; i++) {
                if (!st.contains(split[i])) {
                    st.put(split[i], st.size());
                }
            }
        }

        keys = new String[st.size()];
        for (String name: st.keys()) {
            keys[st.get(name)] = name;
        }

        G = new Digraph(st.size());

        in = new In(stream);
        while (in.hasNextLine()) {
            final String[] split = in.readLine().split(sp);
            int v = st.get(split[0]);
            for (int i = 1; i < split.length; i++) {
                G.addEdge(v, st.get(split[i]));
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int index(String s) {
        return st.get(s);
    }

    public String name(int index) {
        return keys[index];
    }

    public Digraph G() {
        return G;
    }



}
