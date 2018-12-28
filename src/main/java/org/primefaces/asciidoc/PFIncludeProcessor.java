package org.primefaces.asciidoc;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;

public abstract class PFIncludeProcessor extends IncludeProcessor {

    public PFIncludeProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public final void process(DocumentRuby document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
        if (!PFAsciiDoc.INSTANCE.isReady()) {
            PFAsciiDoc.INSTANCE.init(document.getAttributes());
        }

        Object dataModel = getDataModel(document, reader, target, attributes);
        String out = ftl(getTemplate(), dataModel);
        reader.push_include(out, target, target, 1, attributes);
    }

    protected String ftl(String template, Object dataModel) {
        Template tpl = PFAsciiDoc.INSTANCE.getTemplate(template);
        Writer writer = new StringWriter();
        try {
            tpl.process(dataModel, writer);
        }
        catch (TemplateException | IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return writer.toString();
    }

    protected abstract Object getDataModel(DocumentRuby document, PreprocessorReader reader, String target, Map<String, Object> attributes);

    protected abstract String getTemplate();
}
