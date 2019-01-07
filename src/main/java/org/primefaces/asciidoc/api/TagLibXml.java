package org.primefaces.asciidoc.api;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.primefaces.taglib.FaceletTaglib;
import org.primefaces.taglib.Function;
import org.primefaces.taglib.Tag;

public class TagLibXml {

    private final FaceletTaglib taglib;

    public TagLibXml(String path) {
        File file = new File(path);
        try {
            JAXBContext jContext = JAXBContext.newInstance(FaceletTaglib.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            taglib = (FaceletTaglib) unmarshallerObj.unmarshal(file);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Tag findTag(String name) {
        for(Tag tag : taglib.getTag()) {
            if (tag.getTagName().equals(name)) {
                return tag;
            }
        }

        return null;
    }

    public List<Function> getAllFunctions() {
        return taglib.getFunction();
    }
}
