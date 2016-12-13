package com.qunar.tools.dubbo.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.nio.file.Path;

/**
 * since 2016/11/29.
 */
public class DocUtil {


    public static Document parseDoc(Path p) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        return saxReader.read(p.toFile());
    }
}
