package com.wave.apigateway;

import java.util.regex.Pattern;

public class Route {

    private String id;
    private String prefix;
    private String path;
    private String url;
    private boolean stripPrefix = true;
    private Pattern pattern;

    public Route() {}

    public Route(final Route route, final String url) {
        this.id = route.getId();
        this.prefix = route.getPrefix();
        this.path = route.getPath();
        this.url = url;
        this.pattern = route.getPattern();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}