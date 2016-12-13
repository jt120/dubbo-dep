package com.qunar.tools.dubbo.alg;

/**
 * since 2016/12/8.
 */
public class DepthFirstOrder {

    private boolean[] marked;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    //private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph g) {
        marked = new boolean[g.V()];
        pre = new Queue<>();
        post = new Queue<>();
        //reversePost = new Stack<>();
        for (int i = 0; i < g.V(); i++) {
            if (!marked[i]) {
                dfs(g, i);
            }
        }
    }

    public void dfs(Digraph g, int v) {
        pre.enqueue(v);
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }

        post.enqueue(v);
        //reversePost.push(v);
    }


    public Queue<Integer> getPre() {
        return pre;
    }

    public Queue<Integer> getPost() {
        return post;
    }

    public Stack<Integer> getReversePost() {
        Stack<Integer> reverse = new Stack<>();
        for (int i : post) {
            reverse.push(i);
        }
        return reverse;
    }

    public static void main(String[] args) throws Exception {
        Digraph g = new Digraph(new In("D:\\project\\a\\algs-student\\algs4-data\\tinyDG.txt"));
        DepthFirstOrder o = new DepthFirstOrder(g.reverse());
        for (int i : o.getReversePost()) {
            System.out.print(i+" ");
        }
    }
}
