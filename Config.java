import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Config {
    static class ConfigElemet {
        boolean enabled = false;
        String fileName;
        String format;

        @Override
        public String toString() {
            return "enabled:" + enabled + ", fileName:" + fileName + ", format:" + format;
        }
    }

    public Config() {

    }

    ConfigElemet load = new ConfigElemet();
    ConfigElemet save = new ConfigElemet();
    ConfigElemet log = new ConfigElemet();

    public ConfigElemet getLoad() {
        return load;
    }

    public ConfigElemet getSave() {
        return save;
    }

    public ConfigElemet getLog() {
        return log;
    }

    public void loadFromXml(File file) {
        if(!file.exists()) {
            System.out.println("loadFromXml: file not exist");
            return;
        }
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(file);
            Node documentElement = doc.getDocumentElement();
            parseDocument(documentElement);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseDocument(Node docElement) {
        NodeList childNodes = docElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                if ("load".compareTo(node.getNodeName()) == 0)
                    parseConfigElement(node, load);
                else if ("save".compareTo(node.getNodeName()) == 0)
                    parseConfigElement(node, save);
                else if ("log".compareTo(node.getNodeName()) == 0)
                    parseConfigElement(node, log);
            }
        }
    }

    private void parseConfigElement(Node nodeElement, ConfigElemet configElemet) {
        NodeList childNodes = nodeElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                String value = node.getChildNodes().item(0).getTextContent();
                if ("enabled".compareTo(node.getNodeName()) == 0)
                    configElemet.enabled = Boolean.parseBoolean(value);
                else if ("fileName".compareTo(node.getNodeName()) == 0)
                    configElemet.fileName = value;
                else if ("format".compareTo(node.getNodeName()) == 0)
                    configElemet.format = value;
            }
        }
        //System.out.println(nodeElement.getNodeName() + ": " + configElemet.toString());
    }
}
