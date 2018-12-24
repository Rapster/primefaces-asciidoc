package org.primefaces.asciidoc.api;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.primefaces.facesconfig.Component;
import org.primefaces.facesconfig.FacesConfig;
import org.primefaces.facesconfig.Renderer;

public class FacesConfigXml {

    private final FacesConfig facesConfig;

    public FacesConfigXml(String path) {
        File file = new File(path);
        try {
            JAXBContext jContext = JAXBContext.newInstance(FacesConfig.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            facesConfig = (FacesConfig) unmarshallerObj.unmarshal(file);
        }
        catch (JAXBException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean isReady() {
        return facesConfig != null;
    }

    public Component findComponent(String componentType) {
        for (Component component : facesConfig.getComponent()) {
            if (component.getComponentType().equals(componentType)) {
                return component;
            }
        }

        return null;
    }

    public Renderer findRenderer(String rendererType) {
        for (Renderer renderer : facesConfig.getRenderKit().getRenderer()) {
            if (renderer.getRendererType().equals(rendererType)) {
                return renderer;
            }
        }

        return null;
    }
}
