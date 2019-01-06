package org.primefaces.asciidoc.js.model;

public class FunctionParam {

    private final String name;
    private final String type;
    private String description;

    public FunctionParam(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
}
