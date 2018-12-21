package org.primefaces.asciidoc;

import org.primefaces.taglib.FaceletTaglib;
import org.primefaces.taglib.Tag;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class TagLibXml {

    private FaceletTaglib taglib;

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

    public boolean isReady() {
        return taglib != null;
    }

    public Tag findTag(String name) {
        for(Tag tag : taglib.getTag()) {
            if (tag.getTagName().equals(name)) {
                return tag;
            }
        }

        return null;
    }
}
