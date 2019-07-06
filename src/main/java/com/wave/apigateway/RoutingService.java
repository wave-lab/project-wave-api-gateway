package com.wave.apigateway;

import org.springframework.stereotype.Service;

@Service
public class RoutingService {

    private RouteLocator locator;
    private RestTemplatePool restTemplatePool;

    public RoutingService(final RouteLocator routeLocator, final RestTemplatePool restTemplatePool) {
        this.locator = routeLocator;
        this.restTemplatePool = restTemplatePool;
    }

    void routing() {
        final RequestContext context = RequestContext.getCurrentContext();
        Route route = locator.getUri();
        context.setRouteHost(route.getPath());
        restTemplatePool.run();
    }
}