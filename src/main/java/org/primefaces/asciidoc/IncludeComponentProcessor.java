package org.primefaces.asciidoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.PreprocessorReader;
import org.primefaces.facesconfig.Component;
import org.primefaces.facesconfig.Renderer;
import org.primefaces.taglib.Tag;

public class IncludeComponentProcessor extends PFIncludeProcessor {

    private static final Pattern COMPONENT_PATTERN = Pattern.compile("^p-(\\w+)$");

    public IncludeComponentProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public boolean handles(String s) {
        return s != null && s.startsWith("p-");
    }

    @Override
    public Object getDataModel(DocumentRuby documentRuby, PreprocessorReader reader, String s, Map<String, Object> config) {
        Matcher matcher = COMPONENT_PATTERN.matcher(s);
        if (matcher.matches()) {
            return processDataModel(matcher.group(1), documentRuby.getAttributes());
        }

        return Collections.emptyMap();
    }

    @Override
    protected String getTemplate() {
        return "component.ftl";
    }

    protected Map<String, Object> processDataModel(String tagName, Map<String, Object> config) {
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

        String content = readADOCComponentFile(tagName, config);
        map.put("content", content);
        return map;
    }

    protected String readADOCComponentFile(String tagName, Map<String, Object> config) {
        String pathDoc = (String) config.get("components");
        if (pathDoc == null) {
            return null;
        }

        Path pathFile = Paths.get(pathDoc, "_" + tagName + ".adoc");
        try {
            byte[] bytes = Files.readAllBytes(pathFile);
            return new String(bytes, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Can't read " + pathDoc, e);
        }
    }
}
