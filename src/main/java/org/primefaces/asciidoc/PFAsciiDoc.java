package org.primefaces.asciidoc;

import java.util.List;
import java.util.Map;

import freemarker.template.Template;
import org.primefaces.asciidoc.api.FTL;
import org.primefaces.asciidoc.api.FacesConfigXml;
import org.primefaces.asciidoc.api.TagLibXml;
import org.primefaces.facesconfig.Component;
import org.primefaces.facesconfig.Renderer;
import org.primefaces.taglib.Function;
import org.primefaces.taglib.Tag;

public class PFAsciiDoc {

    public static final PFAsciiDoc INSTANCE = new PFAsciiDoc();

    private TagLibXml tagLibXml;

    private FTL ftl;

    private FacesConfigXml facesConfigXml;

    private PFAsciiDoc() {
        // NOOP
    }

    public void init(Map<String, Object> config) {
        tagLibXml = new TagLibXml((String)config.get("taglib"));
        facesConfigXml = new FacesConfigXml((String) config.get("faces-config"));
        ftl = new FTL((String)config.get("ftl"));
    }

    public boolean isReady() {
        return tagLibXml != null
                && ftl != null
                && facesConfigXml != null;
    }

    public Tag findTag(String name) {
        return tagLibXml.findTag(name);
    }

    public Template getTemplate(String name) {
        return ftl.getTemplate(name);
    }

    public Component findComponent(String componentType) {
        return facesConfigXml.findComponent(componentType);
    }

    public Renderer findRenderer(String rendererType) {
        return facesConfigXml.findRenderer(rendererType);
    }

    public List<Function> getAllFunctions() {
        return tagLibXml.getAllFunctions();
    }
}
