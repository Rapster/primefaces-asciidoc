package org.primefaces.asciidoc;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.PreprocessorReader;

public class IncludeFunctionsProcessor extends PFIncludeProcessor {

    public IncludeFunctionsProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public boolean handles(String s) {
        return "pf-functions".equals(s);
    }

    @Override
    public Object getDataModel(DocumentRuby documentRuby, PreprocessorReader reader, String s, Map<String, Object> config) {
        return processDataModel();
    }

    @Override
    protected String getTemplate() {
        return "functions.ftl";
    }

    protected Map<String, Object> processDataModel() {
        Map<String, Object> map = new HashMap<>();
        map.put("functions", PFAsciiDoc.INSTANCE.getAllFunctions());
        return map;
    }
}
