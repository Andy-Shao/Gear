package com.github.andyshao.system;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.github.andyshao.lang.GeneralSystemProperty;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class VersionTask implements Task {
    public static final String KEY_WORDS = "-version";
    public static final String TARGET = "META-INF/maven/com.github.Andy-Shao/Gear/pom.xml";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(VersionTask.KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        try (final ZipFile zipFile = new ZipFile(new File(GeneralSystemProperty.JAVA_CLASS_PATH.value()))) {
            final ZipEntry zipEntry = zipFile.getEntry(VersionTask.TARGET);
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = factory.newDocumentBuilder();
            final Document xmlDoc = db.parse(zipFile.getInputStream(zipEntry));
            final Element root = xmlDoc.getDocumentElement();

            System.out.println("groupId: " + root.getElementsByTagName("groupId").item(0).getTextContent());
            System.out.println("artifactId: " + root.getElementsByTagName("artifactId").item(0).getTextContent());
            System.out.println("version: " + root.getElementsByTagName("version").item(0).getTextContent());
            System.out.println("packaging: " + root.getElementsByTagName("packaging").item(0).getTextContent());
            System.out.println("description: " + root.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("url: " + root.getElementsByTagName("url").item(0).getTextContent());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
