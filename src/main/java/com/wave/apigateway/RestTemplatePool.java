package com.wave.apigateway;

import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class RestTemplatePool {

    private RestTemplate restTemplate;

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
        final HttpHeaders requestHeaders = buildRequestHeaders();
        final Object requestBody = context.getRequestBody();

        final HttpEntity requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        final ResponseEntity<Object> responseEntity =
                restTemplate.exchange(path, httpMethod, requestEntity, new ParameterizedTypeReference<Object>() {});

        context.setResponseBody(responseEntity.getBody());
    }

    private HttpHeaders buildRequestHeaders() {
        return null;
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