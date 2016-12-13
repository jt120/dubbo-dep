package com.qunar.tools.dubbo.bean;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AppFile {
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