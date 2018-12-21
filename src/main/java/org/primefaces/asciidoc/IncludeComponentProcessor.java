package org.primefaces.asciidoc;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;
import org.primefaces.taglib.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeComponentProcessor extends IncludeProcessor {

    private static final Pattern COMPONENT_PATTERN = Pattern.compile("^p-(\\w+)$");

    public IncludeComponentProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public boolean handles(String s) {
        return s != null && s.startsWith("p-");
    }

    @Override
    public void process(DocumentRuby documentRuby, PreprocessorReader reader, String s, Map<String, Object> config) {
        if (!PFAsciiDoc.INSTANCE.isReady()) {
            PFAsciiDoc.INSTANCE.init(documentRuby.getAttributes());
        }

        Matcher matcher = COMPONENT_PATTERN.matcher(s);
        if (matcher.matches()) {
            String data = processComponent(matcher.group(1));
            reader.push_include(data, s, s, 1, config);
        }
    }

    protected String processComponent(String tagName) {
        Tag tag =  PFAsciiDoc.INSTANCE.findTag(tagName);
        Template tpl = PFAsciiDoc.INSTANCE.getTemplate("component.ftl");
        Map<String, Tag> map = Collections.singletonMap("tag", tag);
        Writer writer = new StringWriter();
        try {
            tpl.process(map, writer);
        }
        catch (TemplateException | IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return writer.toString();
    }
}
