package org.primefaces.asciidoc;

import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;

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
    public void process(DocumentRuby documentRuby, PreprocessorReader reader, String s, Map<String, Object> map) {
        Matcher matcher = COMPONENT_PATTERN.matcher(s);
        if (matcher.matches()) {
            String component = matcher.group(1);
            reader.push_include("HelloWorld", s, s, 1, map);
        }
    }
}
