package com.wave.apigateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FrontController {

    private static final Log log = LogFactory.getLog(FrontController.class);

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(500, false, "INTERNAL SERVER ERROR");

    private RoutingService routingService;
    private RoutingTable routingTable;

    public FrontController(final RoutingService routingService, final RoutingTable routingTable) {
        this.routingService = routingService;
        this.routingTable = routingTable;
    }

    @GetMapping("/routes")
    public ResponseEntity getRoutingTable() {
        return new ResponseEntity<>(routingTable.getPaths(), HttpStatus.OK);
    }

    @PostMapping("/routes")
    public ResponseEntity insertRoutingTable(@RequestBody final Route route) {
        routingTable.registryRouting(route);
        return new ResponseEntity<>(routingTable.getPaths(), HttpStatus.OK);
    }

    @DeleteMapping("/routes/{id}")
    public ResponseEntity deleteRouting(@PathVariable final String id) {
        routingTable.deleteRouting(id);
        return new ResponseEntity<>(routingTable.getPaths(), HttpStatus.OK);
    }

    @RequestMapping("/**")
    public ResponseEntity apiGateway(final HttpServletRequest httpServletRequest,
                                     @RequestBody(required = false) final Object obj) {
        RequestContext context = RequestContext.getCurrentContext();
        context.setRequest(httpServletRequest);
        context.setRequestBody(obj);
        try {
            routingService.routing();
            return new ResponseEntity<>(context.getResponseBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            context.unset();
        }
    }

}