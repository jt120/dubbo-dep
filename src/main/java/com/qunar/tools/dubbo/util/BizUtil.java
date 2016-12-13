package com.qunar.tools.dubbo.util;


import com.qunar.tools.dubbo.bean.App;
import com.qunar.tools.dubbo.bean.AppFile;
import com.qunar.tools.dubbo.bean.RegistryInfo;
import com.qunar.tools.dubbo.bean.ServiceInfo;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * since 2016/11/29.
 */
public class BizUtil {

    public static Optional<RegistryInfo> findReg(Set<RegistryInfo> registryInfos, String name) {
        return registryInfos.stream().filter(r -> r.getName().equals(name)).findAny();
    }


    public static App buildApp(AppFile appFile) {
        App app = new App();
        app.setName(appFile.getName());
        Properties properties = new Properties();

        appFile.getXmlFiles().stream().filter(s -> s.toAbsolutePath().toString().matches(".*prod.*\\.properties$")).forEach(
                s -> {
                    try {
                        properties.load(new FileReader(s.toFile()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        Set<ServiceInfo> serviceInfos = new HashSet<>();
        Set<RegistryInfo> registryInfos = new HashSet<>();


        appFile.getXmlFiles().stream().filter(s -> s.toAbsolutePath().toString().endsWith(".xml")).forEach(
                s -> {
                    try {
                        final Document document = DocUtil.parseDoc(s);
                        final Element rootElement = document.getRootElement();
                        parseApp(properties, rootElement, serviceInfos, registryInfos);
                    } catch (Exception e) {
                        System.err.println("parse xml fail " + s);
                        e.printStackTrace();
                    }
                }
        );

        app.setRegistryInfos(registryInfos);
        app.setProviderInfos(serviceInfos.stream().filter(s -> s.isProvider()).collect(Collectors.toSet()));
        app.setConsumerInfos(serviceInfos.stream().filter(s -> !s.isProvider()).collect(Collectors.toSet()));
        System.out.println("build app " + app.getName());
        return app;
    }


    public static Map<String, AppFile> buildAppFile(String rootPath) throws Exception {
        Path path = Paths.get(rootPath);
        final File[] files = path.toFile().listFiles();
        Map<String, AppFile> appMap = new HashMap<>();
        for (File file : files) {
            AppFile app = new AppFile();
            Files.walk(Paths.get(file.toURI()))
                    .filter(s -> s.toAbsolutePath().toString().matches(".*src\\\\main.*"))
                    .filter(s-> s.toAbsolutePath().toString().matches("^(?!.*(beta|dev|docker|META-INF)).*$"))

                    .forEach(s -> {
                        if (s.getFileName().toString().equals("qunar-app.properties")) {
                            try {
                                Properties properties = new Properties();
                                properties.load(new FileReader(s.toFile()));
                                final String appName = properties.getProperty("name");
                                app.setName(appName);
                                appMap.put(appName, app);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (s.getFileName().toString().matches(".*[\\.xml|\\.properties]$")) {
                            app.getXmlFiles().add(s);
                        }
                    });
            System.out.println("build appFile " + app.getName());

        }
        return appMap;

    }

    private static void parseApp(Properties properties, Element rootElement, Set<ServiceInfo> serviceInfos,
                                 Set<RegistryInfo> registryInfos) {
        for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            if (element.getQualifiedName().equalsIgnoreCase("dubbo:service")) {
                ServiceInfo serviceInfo = getServiceInfo(element.attributeValue("interface"),
                        element.attributeValue("registry"));
                serviceInfo.setProvider(true);
                serviceInfos.add(serviceInfo);

            } else if (element.getQualifiedName().equalsIgnoreCase("dubbo:registry")) {
                String name = element.attributeValue("id");
                String group = element.attributeValue("group");
                final String groupKey = RegexUtil.parseGroup(group);

                RegistryInfo registryInfo = new RegistryInfo();
                registryInfo.setName(name);
                registryInfo.setGroup((String) properties.getOrDefault(groupKey, groupKey));

                registryInfos.add(registryInfo);
            } else if (element.getQualifiedName().equalsIgnoreCase("dubbo:reference")) {
                ServiceInfo serviceInfo = getServiceInfo(element.attributeValue("interface"),
                        element.attributeValue("registry"));
                serviceInfos.add(serviceInfo);
            }
        }

        //注入group
        for (ServiceInfo serviceInfo : serviceInfos) {
            serviceInfo.setGroup(BizUtil.findReg(registryInfos, serviceInfo.getRegName()).orElseGet(() ->
                    RegistryInfo.empty).getGroup());
        }
    }

    private static ServiceInfo getServiceInfo(String name, String regName) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setName(name);
        serviceInfo.setRegName(regName);
        return serviceInfo;
    }
}
