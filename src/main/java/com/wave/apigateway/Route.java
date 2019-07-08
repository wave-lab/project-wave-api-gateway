package com.wave.apigateway;

import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Route {

    private String id;
    private String prefix;
    private String path;
    private String url;
    private boolean stripPrefix = true;
    private Pattern pattern;

    public Route() {
    }

    public Route(final Route route, final String url) {
        this.id = route.getId();
        this.prefix = route.getPrefix();
        this.url = route.getUrl();
        this.pattern = route.getPattern();
        this.path = url;
    }

    public Route(String text) {
        String location = null;
        String path = text;
        if (text.contains("=")) {
            String[] values = StringUtils
                    .trimArrayElements(StringUtils.split(text, "="));
            location = values[1];
            path = values[0];
        }
        this.id = extractId(path);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        setLocation(location);
        this.path = path;
    }

    public Route(String path, String location) {
        this.id = extractId(path);
        this.path = path;
        setLocation(location);
    }

    public String getLocation() {
        if (StringUtils.hasText(this.url)) {
            return this.url;
        }
        return this.path;
    }

    public void setLocation(String location) {
        if (location != null
                && (location.startsWith("http:") || location.startsWith("https:"))) {
            this.url = location;
        } else {
            this.path = location;
        }
    }

    private String extractId(String path) {
        path = path.startsWith("/") ? path.substring(1) : path;
        path = path.replace("/*", "").replace("*", "");
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrefix() {
        return prefix;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", prefix='" + prefix + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", stripPrefix=" + stripPrefix +
                ", pattern=" + pattern +
                '}';
    }
}