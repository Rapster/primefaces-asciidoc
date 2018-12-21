package org.primefaces.asciidoc;

import freemarker.core.ParseException;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;

public class FTL {

    Configuration config;

    public FTL(String path) {
        try {
            config = new Configuration(Configuration.VERSION_2_3_27);
            config.setDirectoryForTemplateLoading(new File(path));
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setLogTemplateExceptions(false);
            config.setWrapUncheckedExceptions(true);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Template getTemplate(String name) {
        try {
            return config.getTemplate(name);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
