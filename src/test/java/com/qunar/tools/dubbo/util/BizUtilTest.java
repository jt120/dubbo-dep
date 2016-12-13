package com.qunar.tools.dubbo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qunar.tools.dubbo.bean.App;
import com.qunar.tools.dubbo.bean.AppFile;
import com.qunar.tools.dubbo.bean.Relation;
import com.qunar.tools.dubbo.bean.ServiceInfo;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BizUtilTest {

    @Test
    public void testBuildApp() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<App> collect = mapper.readValue(ClassLoader.getSystemResource("app_dep.json"),
                new TypeReference<List<App>>() {
        });
        List<Relation> relations = new ArrayList<>();
        for (App outApp : collect) {
            Relation relation = new Relation();
            relations.add(relation);
            relation.setApp(outApp);

            for (App inApp : collect) {
                for (ServiceInfo consumer : outApp.getConsumerInfos()) {
                    for (ServiceInfo provider : inApp.getProviderInfos()) {
                        if (consumer.getName().equals(provider.getName()) && consumer.getGroup().equals(provider
                                .getGroup())) {
                            System.out.println("interface " + consumer + " consumer " + outApp.getName() + " provider" +
                                    " " + inApp.getName());
                            relation.getDepApp().add(inApp);
                        }
                    }
                }
            }
        }

        for (Relation relation : relations) {
            for (App app : relation.getDepApp()) {
                System.out.println(relation.getApp().getName() + "###" + app.getName());
            }
        }
    }

    @Test
    public void testBuildAppFile() throws Exception {
        final String rootPath = "D:\\project\\qunar\\flight";
        //final String rootPath = "D:\\test\\cc";
        final Map<String, AppFile> stringAppFileMap = BizUtil.buildAppFile(rootPath);
        System.out.println("=================");
        final List<App> collect = stringAppFileMap.values().stream().map(appFile -> BizUtil.buildApp(appFile))
                .collect(Collectors.toList());
        System.out.println("=================");
        ObjectMapper objectMapper = new ObjectMapper();
        final String s = objectMapper.writeValueAsString(collect);
        System.out.println(s);
    }

    @Test
    public void testPath1() throws Exception {
        String pattern = "^(?!.*(beta|dev|docker)).*$";
        String s1 = "D:\\test\\cc\\ui\\src\\main\\profiles\\betab";
        String s2 = "D:\\test\\cc\\ui\\src\\main\\profiles\\prod";
        String s3 = "D:\\test\\cc\\ui\\src\\main\\resources";
        System.out.println(s1.matches(pattern));
        System.out.println(s2.matches(pattern));
        System.out.println(s3.matches(pattern));

        System.out.println("abcd".matches("[ab].*"));
        System.out.println("abcd".matches("[a].*"));
    }
}