package org.primefaces.asciidoc;

import freemarker.template.Template;
import org.primefaces.taglib.Tag;

import java.util.Map;

public class PFAsciiDoc {

    public static final PFAsciiDoc INSTANCE = new PFAsciiDoc();

    private TagLibXml tagLibXml;

    private FTL ftl;

    private PFAsciiDoc() {
        // NOOP
    }

    public void init(Map<String, Object> config) {
        tagLibXml = new TagLibXml((String)config.get("taglib"));
        ftl = new FTL((String)config.get("ftl"));
    }

    public boolean isReady() {
        return tagLibXml != null
                && ftl != null;
    }

    public Tag findTag(String name) {
        return tagLibXml.findTag(name);
    }

    public Template getTemplate(String name) {
        return ftl.getTemplate(name);
    }
}
