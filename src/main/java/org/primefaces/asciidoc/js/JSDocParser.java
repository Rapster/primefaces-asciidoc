package org.primefaces.asciidoc.js;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.primefaces.asciidoc.js.model.ClientAPI;
import org.primefaces.asciidoc.js.model.FunctionDescription;
import org.primefaces.asciidoc.js.model.FunctionParam;
import org.primefaces.asciidoc.js.model.Returns;

public final class JSDocParser {

    public static final Pattern WIDGET_REGEX = Pattern.compile("(PrimeFaces\\.widget\\.\\w*)");

    public static final Pattern PARAM_REGEX = Pattern.compile("@param \\{(?<type>\\w*)\\} (?<name>\\w*) - (?<desc>[\\s\\w\\W]*)");

    public static final Pattern RETURN_LINE_REGEX = Pattern.compile("@return \\{(?<type>\\w*)\\} (?<desc>[\\s\\w\\W]*)");

    public static final Pattern JS_DOC_REGEX = Pattern.compile("\\* @public(?<desc>[\\s\\w\\W]+?)(?<param>@param[\\s\\w\\W]+?)?(?<return>@return[\\s\\w\\W]+?)?\\*\\/\\s*(?<name>\\w+): function");

    public JSDocParser() {
        // NOOP
    }

    public static ClientAPI parseClientAPI(Path jsFile) {
        ClientAPI clientAPI = new ClientAPI();

        try {
            String content = new String(Files.readAllBytes(jsFile), StandardCharsets.UTF_8);
            Matcher wMatcher = WIDGET_REGEX.matcher(content);
            if (wMatcher.find()) {
                clientAPI.setWidget(wMatcher.group(0));
            }

            Matcher fMatcher = JS_DOC_REGEX.matcher(content);
            List<FunctionDescription> fDescriptions = new ArrayList<>();

            while (fMatcher.find()) {
                FunctionDescription fd = new FunctionDescription();

                String desc = cleanUp(fMatcher.group("desc"));
                fd.setDescription(desc);

                String name = cleanUp(fMatcher.group("name"));
                fd.setName(name);

                String param = optionalGroup(fMatcher, "param");
                if (param != null) {
                    List<FunctionParam> fParams = new ArrayList<>();
                    String[] params = param.split("@param");
                    for (String p : params) {
                        Matcher pMatcher = PARAM_REGEX.matcher("@param " + cleanUp(p));
                        if (pMatcher.find()) {
                            FunctionParam fParam = new FunctionParam(pMatcher.group("name"), pMatcher.group("desc"), pMatcher.group("type"));
                            fParams.add(fParam);
                        }
                    }

                    fd.setParams(fParams);
                }

                String returns = optionalGroup(fMatcher, "return");
                if (returns != null) {
                    Matcher rMatcher = RETURN_LINE_REGEX.matcher(cleanUp(returns));
                    if (rMatcher.find()) {
                        Returns ret = new Returns(rMatcher.group("type"), rMatcher.group("desc"));
                        fd.setReturns(ret);
                    }
                }

                fDescriptions.add(fd);
            }

            clientAPI.setFunctions(fDescriptions);
        }
        catch (IOException e) {
            // throw new IllegalArgumentException(e);
        }

        return clientAPI;
    }

    public static final String cleanUp(String value) {
        return value.replaceAll("(\\s*\\*\\s*)", "").trim();
    }

    public static String optionalGroup(Matcher m, String group) {
        try {
            return m.group(group);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}
