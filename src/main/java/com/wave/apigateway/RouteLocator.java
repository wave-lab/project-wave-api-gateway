package com.wave.apigateway;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Matcher;

@Service
public class RouteLocator {

    private Map<String, Route> routeMap;

    public RouteLocator(final RoutingTable routingTable) {
        this.routeMap = routingTable.getPaths();
    }

    Route getUri() {
        final RequestContext context = RequestContext.getCurrentContext();
        final HttpServletRequest httpServletRequest = context.getRequest();

        final String originURI = httpServletRequest.getRequestURI();
        Route route = getRoute(originURI);
        if(route == null) {
            route = routeMap.get("default");
        }

        String queryAndParams = "?";
        boolean isQueryAndParams = false;
        if(httpServletRequest.getQueryString() != null) {
            queryAndParams += httpServletRequest.getQueryString();
            isQueryAndParams = true;
        }

        String targetPath = originURI;
        if(route.isStripPrefix()) {
            targetPath = targetPath.substring(route.getPrefix().length());
        }

        String uri = route.getUrl() + targetPath;
        if(isQueryAndParams) uri += queryAndParams;
        return new Route(route, uri);
    }

    private Route getRoute(final String originURI) {
        Route route;
        Matcher matcher;
        for (Map.Entry<String, Route> entry : routeMap.entrySet()) {
            route = entry.getValue();
            matcher = route.getPattern().matcher(originURI);
            if(matcher.find()) {
                return route;
            }
        }
        return null;
    }
}