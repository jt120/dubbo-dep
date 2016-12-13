package com.qunar.tools.dubbo;

import com.qunar.tools.dubbo.alg.Cycle;
import com.qunar.tools.dubbo.alg.Digraph;
import com.qunar.tools.dubbo.alg.DirectedCycle;
import com.qunar.tools.dubbo.alg.Graph;
import com.qunar.tools.dubbo.alg.KosarajuSCC;
import com.qunar.tools.dubbo.alg.Pair;
import com.qunar.tools.dubbo.alg.Queue;
import com.qunar.tools.dubbo.alg.SymbolDigraph;
import com.qunar.tools.dubbo.alg.SymbolGraph;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * since 2016/12/8.
 */
public class TestCc {

    @Test
    public void testSymbol2() throws Exception {
        String fileName = "dep.txt";
        String delim = "###";
        SymbolGraph symbolGraph = new SymbolGraph(fileName, delim);

        Graph g = symbolGraph.G();
        String input = "f_fuwu_iflightorder";
        System.out.println(input);
        for (int w : g.adj(symbolGraph.index(input))) {
            System.out.println("   " + symbolGraph.name(w));
        }

    }

    @Test
    public void test() throws Exception {
        String fileName = "dep.txt";
        String delim = "###";
        SymbolGraph symbolGraph = new SymbolGraph(fileName, delim);

        Graph g = symbolGraph.G();
        Cycle cycle = new Cycle(g);

        for (Pair pair : cycle.getPair()) {
            System.out.println(symbolGraph.name(pair.getOne()) + "~" + symbolGraph.name(pair.getTwo()));
        }
    }

    //找到一个循环依赖
    @Test
    public void testDicycle() throws Exception {
        String fileName = "dep.txt";
        String delim = "###";
        SymbolDigraph symbolGraph = new SymbolDigraph(fileName, delim);
        final Digraph g = symbolGraph.G();
        DirectedCycle directdCycle = new DirectedCycle(g);
        if (directdCycle.hasCycle()) {
            for (int w : directdCycle.cycle()) {
                System.out.print(symbolGraph.name(w) + "->");
            }

        } else {
            System.out.println("no cycle");
        }
    }

    //把强关联的应用分组

    /**
     * 1. 先解析目录, 提取项目
     * 2. 解析xml, 构造项目, 依赖, 服务
     * 3. 无向图
     * 4. 有向图
     * 5. 符号图
     * 6. 循环依赖
     * 7. 强关联分组
     * @throws Exception
     */
    @Test
    public void testGroup() throws Exception {
        String fileName = "dep.txt";
        String delim = "###";
        SymbolDigraph symbolGraph = new SymbolDigraph(fileName, delim);
        final Digraph g = symbolGraph.G();
        KosarajuSCC kosarajuSCC = new KosarajuSCC(g);

        final Queue<Integer>[] group = kosarajuSCC.group();
        for (Queue<Integer> queue : group) {
            for (int i : queue) {
                System.out.print(symbolGraph.name(i) + " ~ ");
            }
            System.out.println();
        }
    }

    @Test
    public void testRe() throws Exception {
        URL url = ClassLoader.getSystemResource("dep.txt");
        System.out.println(url);
    }

    @Test
    public void testString() throws Exception {
        System.out.println(String.format("xxx%ssdfasdf", 60));
        System.out.println(String.format("sdsfda%ssdfsdwe", -120/60));
    }

    @Test
    public void testFile() throws Exception {
        final String property = System.getProperty("user.dir");
        Files.write(Paths.get(property+ "/dep.json"), "hello java".getBytes());
    }
}
