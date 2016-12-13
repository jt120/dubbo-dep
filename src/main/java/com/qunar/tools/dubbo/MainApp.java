package com.qunar.tools.dubbo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qunar.tools.dubbo.bean.App;
import com.qunar.tools.dubbo.bean.AppFile;
import com.qunar.tools.dubbo.bean.Relation;
import com.qunar.tools.dubbo.bean.ServiceInfo;
import com.qunar.tools.dubbo.util.BizUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 解析目录下的项目dubbo配置文件, 分析项目之间的依赖关系
 * since 2016/12/13.
 */
public class MainApp {

    public static void main(String[] args) throws Exception {
        buildDepJson();
        anyCc();
    }


    private static void buildDepJson() {
        try {
            final String rootPath = "D:\\test\\cc";
            //final String rootPath = "D:\\test\\cc";
            final Map<String, AppFile> stringAppFileMap = BizUtil.buildAppFile(rootPath);
            System.out.println("=================");
            final List<App> collect = stringAppFileMap.values().stream().map(appFile -> BizUtil.buildApp(appFile))
                    .collect(Collectors.toList());
            System.out.println("=================");
            ObjectMapper objectMapper = new ObjectMapper();
            final String s = objectMapper.writeValueAsString(collect);
            System.out.println(s);
            Files.write(getFile(), s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Path getFile() {
        final String property = System.getProperty("user.dir");
        return Paths.get(property + "/dep.json");
    }

    private static void anyCc() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<App> collect = mapper.readValue(getFile().toFile(),
                    new TypeReference<List<App>>() {
                    });
            System.out.println("read apps " + collect);
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
                                System.out.println("interface " + consumer + " consumer " + outApp.getName() + " " +
                                        "provider" +
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
