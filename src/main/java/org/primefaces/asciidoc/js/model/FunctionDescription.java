package org.primefaces.asciidoc.js.model;

import java.util.Collections;
import java.util.List;

public class FunctionDescription {

    private List<FunctionParam> params = Collections.emptyList();

    private String description;

    private Returns returns = Returns.VOID;

    private String name;

    private String signature;

    public FunctionDescription() {
        // NOOP
    }

    public List<FunctionParam> getParams() {
        return params;
    }

    public void setParams(List<FunctionParam> params) {
        this.params = params;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Returns getReturns() {
        return returns;
    }

    public void setReturns(Returns returns) {
        this.returns = returns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * TODO Refactor using String joining feature in Java 8
     *
     * @return signature based on params
     */
    public String getSignature() {
        if (signature == null) {
            String args = "";
            for (int i = 0; i < params.size(); i++) {
                args += params.get(i).getType() + " " + params.get(i).getName();
                if (i + 1 < params.size()) {
                    args += ", ";
                }
            }
            signature = name + "(" + args + ")";
            return signature;
        }

        return signature;
    }
}
