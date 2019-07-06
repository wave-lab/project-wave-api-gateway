package com.wave.apigateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("routing")
public class RoutingTable {

    private Map<String, Route> paths = new LinkedHashMap<>();

    public Map<String, Route> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Route> paths) {
        this.paths = paths;
    }

    public void registryRouting(final Route route) {
        this.paths.put(route.getPrefix(), route);
    }

    public void deleteRouting(final String id) {
        this.paths.remove("/" + id);
    }

}