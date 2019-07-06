package com.wave.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FrontController {

    private RoutingService routingService;

    public FrontController(final RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/**")
    public ResponseEntity apiGateway(final HttpServletRequest httpServletRequest,
                                     @RequestBody(required = false) final Object obj) {
        RequestContext context = RequestContext.getCurrentContext();
        context.setRequest(httpServletRequest);
        context.setRequestBody(obj);
        try {
            routingService.routing();
            return new ResponseEntity<>(context.getResponseBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class DefaultRes<T> {

        private int status;

        private String message;

        private T data;

        public DefaultRes(final int status, final String message) {
            this.status = status;
            this.message = message;
            this.data = null;
        }

        public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(500, "INTERNAL_SERVER_ERROR");
    }
}