package com.qunar.tools.dubbo;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.qunar.tools.dubbo.bean.App;
import com.qunar.tools.dubbo.util.BizUtil;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * since 2016/11/29.
 */
public class TestXml {


    @Test
    public void testDom() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        final Document parse = builder.parse(ClassLoader.getSystemResourceAsStream("users.xml"));
        final NodeList nodes = parse.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            final NodeList childNode = node.getChildNodes();
            for (int j = 0; j < childNode.getLength(); j++) {
                final Node child = childNode.item(j);
                System.out.println(child.getNodeName() + ":" + child.getTextContent());
            }
        }
    }

    @Test
    public void testDom4j() throws Exception {
        SAXReader saxReader = new SAXReader();
        final org.dom4j.Document docu = saxReader.read(ClassLoader.getSystemResourceAsStream("users.xml"));
        final Element rootElement = docu.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
            final Element next = (Element) i.next();
            System.out.println(next.getName() + "," + next.attribute("id").getValue());
        }
    }

    @Test
    public void testDom4jXPath() throws Exception {
        SAXReader saxReader = new SAXReader();
        final org.dom4j.Document docu = saxReader.read(ClassLoader.getSystemResourceAsStream("users.xml"));
        final List list = docu.selectNodes("//users/user");
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void testDubbo() throws Exception {
        final App app = BizUtil.buildApp(null);
        System.out.println(app);
    }

    @Test
    public void testGroup() throws Exception {
        String s = "${dubbo.provider.group}";
        Pattern pattern = Pattern.compile("^\\$\\{([\\w\\.]+)\\}");
        final Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    @Test
    public void testPath() throws Exception {
        final Stream<Path> walk = Files.walk(Paths.get("D:\\project\\qunar\\flight"));
        walk.filter(s -> s.endsWith("src/main/resources/qunar-app.properties")).forEach(s -> {


        });
    }


    @Test
    public void testpath01() throws Exception {
        final Stream<Path> walk = Files.walk(Paths.get("D:\\project\\qunar\\flight\\ttsi_charge_money"));
        walk.filter(s -> s.endsWith("src/main/resources/qunar-app.properties")).forEach(s -> {
            System.out.println(s.subpath(0, 1).toAbsolutePath());
            System.out.println(s.subpath(0, 2).toAbsolutePath());
            System.out.println(s.subpath(0, 4).toAbsolutePath());

        });
    }

    //D:\project\qunar\flight\xxx\transaction_pnr\pnrcore\src\main\resources\qunar-app.properties
    @Test
    public void testPath01() throws Exception {
        Map<String, String> appPath = new HashMap<>();
        List<Path> paths = new ArrayList<>();
        Files.walk(Paths.get("D:\\project\\qunar\\flight"))
                .filter(s -> s.toAbsolutePath().toAbsolutePath().toString().matches(".*src.*"))
                .forEach(s -> {
                    if (s.getFileName().toString().equals("qunar-app.properties")) {
                        try {
                            System.out.println("found " + s);
                            final Path parent = s.getParent().getParent().getParent().getParent().getParent();
                            System.out.println("parent " + parent);
                            Properties properties = new Properties();
                            properties.load(new FileReader(s.toFile()));
                            final String appName = properties.getProperty("name");
                            System.out.println(appName);
                            appPath.put(appName, parent.getFileName().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (s.getFileName().endsWith(".xml")) {
                        paths.add(s);
                    }

                });

        System.out.println(paths);
        System.out.println(appPath);
    }

    @Test
    public void testP1() throws Exception {
        Path path = Paths.get("D:\\project\\qunar\\flight");
        final File[] files = path.toFile().listFiles();
        Map<String, AppFile> appMap = new HashMap<>();
        for (File file : files) {
            AppFile app = new AppFile();
            Files.walk(Paths.get(file.toURI())).filter(s -> s.toAbsolutePath().toString().matches("" +
                    ".*src\\\\main\\\\resource.*"))
                    .forEach(s -> {
                        if (s.getFileName().toString().equals("qunar-app.properties")) {
                            try {
                                Properties properties = new Properties();
                                properties.load(new FileReader(s.toFile()));
                                final String appName = properties.getProperty("name");
                                app.setName(appName);
                                appMap.put(appName, app);
                                System.out.println(s+ "###"+appName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (s.getFileName().toString().endsWith(".xml")) {
                            app.getXmlFiles().add(s);
                        }

                    });


        }


        System.out.println(appMap);
    }

    @Test
    public void testMat() throws Exception {
        Pattern p = Pattern.compile("\\d");
    }

    private static class AppFile {
        private String name;
        private List<Path> xmlFiles = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Path> getXmlFiles() {
            return xmlFiles;
        }

        public void setXmlFiles(List<Path> xmlFiles) {
            this.xmlFiles = xmlFiles;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("App{");
            sb.append("name='").append(name).append('\'');
            sb.append(", xmlFiles=").append(xmlFiles);
            sb.append('}');
            return sb.toString();
        }
    }


}
