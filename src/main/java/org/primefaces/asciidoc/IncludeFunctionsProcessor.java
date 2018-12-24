package org.primefaces.asciidoc;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;

public class IncludeFunctionsProcessor extends IncludeProcessor {

    public IncludeFunctionsProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public boolean handles(String s) {
        return "pf-functions".equals(s);
    }

    @Override
    public void process(DocumentRuby documentRuby, PreprocessorReader reader, String s, Map<String, Object> config) {
        if (!PFAsciiDoc.INSTANCE.isReady()) {
            PFAsciiDoc.INSTANCE.init(documentRuby.getAttributes());
        }

        String out = processFunctions();
        reader.push_include(out, s, s, 1, config);
    }

    protected String processFunctions() {
        Template tpl = PFAsciiDoc.INSTANCE.getTemplate("functions.ftl");
        Map<String, Object> map = new HashMap<>();
        map.put("functions", PFAsciiDoc.INSTANCE.getAllFunctions());

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
