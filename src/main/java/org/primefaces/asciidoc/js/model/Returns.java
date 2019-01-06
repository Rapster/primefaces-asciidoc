package org.primefaces.asciidoc.js.model;

public class Returns {

    public static final Returns VOID = new Returns("void", null);

    private final String type;
    private String description;

    public Returns(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
