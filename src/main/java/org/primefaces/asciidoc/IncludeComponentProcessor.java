package org.primefaces.asciidoc;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;
import org.primefaces.facesconfig.Component;
import org.primefaces.facesconfig.Renderer;
import org.primefaces.taglib.Tag;

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
        Template tpl = PFAsciiDoc.INSTANCE.getTemplate("component.ftl");
        Tag tag =  PFAsciiDoc.INSTANCE.findTag(tagName);
        if (tag == null) {
            throw new IllegalArgumentException("Tag name not found: " + tagName);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("tag", tag);

        if (tag.getComponent() != null) {
            Component component = PFAsciiDoc.INSTANCE.findComponent(tag.getComponent().getComponentType());
            Renderer renderer = PFAsciiDoc.INSTANCE.findRenderer(tag.getComponent().getRendererType());
            map.put("component", component);
            map.put("renderer", renderer);
        }

        if (tag.getBehavior() != null) {
            map.put("behavior", tag.getBehavior());
        }


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
