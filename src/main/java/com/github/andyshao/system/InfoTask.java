package com.github.andyshao.system;

import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.github.andyshao.lang.GeneralSystemProperty;
import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.StringBufferReader;

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
public class InfoTask implements Task {
    public static final String KEY_WORDS = "-info";
    private static final String TARGET = "META-INF/MANIFEST.MF";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(InfoTask.KEY_WORDS);
    }

    private void localProcess(String[] args) throws ZipException , IOException , ParserConfigurationException ,
        SAXException {
        try (final ZipFile zipFile = new ZipFile(new File(GeneralSystemProperty.JAVA_CLASS_PATH.value()))) {
            final ZipEntry manifest = zipFile.getEntry(InfoTask.TARGET);
            try (
                final StringBufferReader reader =
                    new StringBufferReader(Channels.newChannel(zipFile.getInputStream(manifest)));) {
                reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
                System.out.println(reader.read());
            }

            final ZipEntry pom = zipFile.getEntry(VersionTask.TARGET);
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = factory.newDocumentBuilder();
            final Document xmlDoc = db.parse(zipFile.getInputStream(pom));
            final Element root = xmlDoc.getDocumentElement();
            System.out.println("groupId: " + root.getElementsByTagName("groupId").item(0).getTextContent());
            System.out.println("artifactId: " + root.getElementsByTagName("artifactId").item(0).getTextContent());
            System.out.println("version: " + root.getElementsByTagName("version").item(0).getTextContent());
            System.out.println("packaging: " + root.getElementsByTagName("packaging").item(0).getTextContent());
            System.out.println("description: " + root.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("url: " + root.getElementsByTagName("url").item(0).getTextContent());
            System.out.println("inceptionYear: " + root.getElementsByTagName("inceptionYear").item(0).getTextContent());

            final NodeList licenses =
                ((Element) root.getElementsByTagName("licenses").item(0)).getElementsByTagName("license");
            for (int i = 0 ; i < licenses.getLength() ; i++) {
                final Element license = (Element) licenses.item(i);
                System.out.println("license.name: " + license.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("license.url: " + license.getElementsByTagName("url").item(0).getTextContent());
                System.out.println("license.distribution: "
                    + license.getElementsByTagName("distribution").item(0).getTextContent());
            }

            final NodeList developers =
                ((Element) root.getElementsByTagName("developers").item(0)).getElementsByTagName("developer");
            for (int i = 0 ; i < developers.getLength() ; i++) {
                final Element developer = (Element) developers.item(i);
                System.out
                    .println("developer.name: " + developer.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("developer.email: "
                    + developer.getElementsByTagName("email").item(0).getTextContent());
                System.out.println("developer.organization: "
                    + developer.getElementsByTagName("organization").item(0).getTextContent());
                System.out.println("developer.organizationUrl: "
                    + developer.getElementsByTagName("organizationUrl").item(0).getTextContent());
            }

            final Element scm = (Element) root.getElementsByTagName("scm").item(0);
            System.out.println("scm.connection: " + scm.getElementsByTagName("connection").item(0).getTextContent());
            System.out.println("scm.developerConnection: "
                + scm.getElementsByTagName("developerConnection").item(0).getTextContent());
            System.out.println("scm.url: " + scm.getElementsByTagName("url").item(0).getTextContent());

            final Element issueManagement = (Element) root.getElementsByTagName("issueManagement").item(0);
            System.out.println("issueManagement.system: "
                + issueManagement.getElementsByTagName("system").item(0).getTextContent());
            System.out.println("system.url: " + issueManagement.getElementsByTagName("url").item(0).getTextContent());

            final Element distributionManagement =
                (Element) root.getElementsByTagName("distributionManagement").item(0);
            final Element snapshotRepository =
                (Element) distributionManagement.getElementsByTagName("snapshotRepository").item(0);
            System.out.println("distributionManagement.snapshotRepository.id: "
                + snapshotRepository.getElementsByTagName("id").item(0).getTextContent());
            System.out.println("distributionManagement.snapshotRepository.url: "
                + snapshotRepository.getElementsByTagName("url").item(0).getTextContent());
            final Element repository = (Element) distributionManagement.getElementsByTagName("repository").item(0);
            System.out.println("distributionManagement.repository.id: "
                + repository.getElementsByTagName("id").item(0).getTextContent());
            System.out.println("distributionManagement.repository.url: "
                + repository.getElementsByTagName("url").item(0).getTextContent());

            final NodeList dependencies =
                ((Element) root.getElementsByTagName("dependencies").item(0)).getElementsByTagName("dependency");
            for (int i = 0 ; i < dependencies.getLength() ; i++) {
                final Element dependency = (Element) dependencies.item(i);
                System.out.println("dependency.groupId: "
                    + dependency.getElementsByTagName("groupId").item(0).getTextContent());
                System.out.println("dependency.artifactId: "
                    + dependency.getElementsByTagName("artifactId").item(0).getTextContent());
                System.out.println("dependency.version: "
                    + dependency.getElementsByTagName("version").item(0).getTextContent());
                final NodeList scope = dependency.getElementsByTagName("scope");
                if (scope == null || scope.getLength() == 0)
                ;
                else System.out.println("dependency.scope: "
                    + dependency.getElementsByTagName("scope").item(0).getTextContent());
            }
        }
    }

    @Override
    public void process(String[] args) {
        try {
            this.localProcess(args);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
