package com.wave.apigateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

@Service
public class RestTemplatePool {

    private RestTemplate restTemplate;

    private static final Log log = LogFactory.getLog(RestTemplatePool.class);

    public static final String IGNORED_HEADERS = "ignoredHeaders";

    private boolean addHostHeader = false;

    public RestTemplatePool() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(5000);
        httpRequestFactory.setReadTimeout(5000);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(300)
                .setMaxConnPerRoute(50)
                .build();

        httpRequestFactory.setHttpClient(httpClient);
        restTemplate = new RestTemplate(httpRequestFactory);
    }

    public void run() {
        final RequestContext context = RequestContext.getCurrentContext();
        final HttpServletRequest request = context.getRequest();

        final HttpMethod httpMethod = getMethod(request.getMethod());
        final String path = context.getRouteHost();
        final MultiValueMap<String, String> requestHeaders = buildRequestHeaders();
        final Object requestBody = context.getRequestBody();

        final HttpEntity requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        final ResponseEntity<Object> responseEntity =
                restTemplate.exchange(path, httpMethod, requestEntity, new ParameterizedTypeReference<Object>() {});

        context.setResponseBody(responseEntity.getBody());
    }

    private MultiValueMap<String, String> buildRequestHeaders() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        MultiValueMap<String, String> headers = new org.springframework.http.HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                if (isIncludedHeader(name)) {
                    Enumeration<String> values = request.getHeaders(name);
                    while (values.hasMoreElements()) {
                        String value = values.nextElement();
                        headers.add(name, value);
                    }
                }
            }
        }
        Map<String, String> RequestHeaders = context.getZuulRequestHeaders();
        for (String header : RequestHeaders.keySet()) {
            if (isIncludedHeader(header)) {
                headers.set(header, RequestHeaders.get(header));
            }
        }
        if (!headers.containsKey(org.springframework.http.HttpHeaders.ACCEPT_ENCODING)) {
            headers.set(org.springframework.http.HttpHeaders.ACCEPT_ENCODING, "gzip");
        }
        return headers;
    }

    public boolean isIncludedHeader(String headerName) {
        String name = headerName.toLowerCase();
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.containsKey(IGNORED_HEADERS)) {
            Object object = ctx.get(IGNORED_HEADERS);
            if (object instanceof Collection && ((Collection<?>) object).contains(name)) {
                return false;
            }
        }
        switch (name) {
            case "host":
                if (addHostHeader) {
                    return true;
                }
            case "connection":
            case "content-length":
            case "server":
            case "transfer-encoding":
            case "x-application-context":
                return false;
            default:
                return true;
        }
    }

    private HttpMethod getMethod(final String method) {
        switch (method) {
            case "GET" : {
                return HttpMethod.GET;
            }
            case "POST" : {
                return HttpMethod.POST;
            }
            case "PUT" : {
                return HttpMethod.PUT;
            }
            case "DELETE" : {
                return HttpMethod.DELETE;
            }
            default: return HttpMethod.GET;
        }
    }
}