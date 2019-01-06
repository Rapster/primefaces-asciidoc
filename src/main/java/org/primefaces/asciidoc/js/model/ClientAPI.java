package org.primefaces.asciidoc.js.model;

import java.util.Collections;
import java.util.List;

public class ClientAPI {

    private List<FunctionDescription> functions;

    private String widget;

    public ClientAPI() {
        functions = Collections.emptyList();
    }

    public List<FunctionDescription> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionDescription> functions) {
        this.functions = functions;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }
}
