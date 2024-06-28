package com.example.gardeningPlanner;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.DELETE;
import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.POST;
import static com.example.gardeningPlanner.SecurityMockMvc.HttpMethod.PUT;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Helper class for testing web controllers.
 * 
 * Based on "portfolio-security" from "Programmieren 2"
 * and the following Links
 * https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/setup.html
 * https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework/server-performing-requests.html
 * https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework/server-defining-expectations.html
 * https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html
 * 
 * it needs the static imports and VSC doesn't load it
 */

public abstract class SecurityMockMvc {
    
    public enum Csrf {
        ENABLED, DISABLED, INVALID;
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE;
    }

    /**
     * Add an instance of Spring's MockMvc utility to enable testing of web pages.
     */
    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    protected void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    protected MockHttpServletRequestBuilder request(HttpMethod httpMethod, String endpoint) {
        return request(httpMethod, endpoint, null);
    }

    protected MockHttpServletRequestBuilder request(HttpMethod request, String endpoint, Csrf csrf) {
        var result = switch (request) {
            case GET -> get(endpoint);
            case POST -> post(endpoint);
            case PUT -> put(endpoint);
            case DELETE -> delete(endpoint);
        };

        if (notSpecified(csrf) && isModifying(request)) {
            csrf = Csrf.ENABLED;
        }

        if (notSpecified(csrf)) {
            return result;
        }

        return switch (csrf) {
            case ENABLED -> result.with(csrf());
            case INVALID -> result.with(csrf().useInvalidToken());
            case DISABLED -> result;
        };
    }

    private boolean notSpecified(Csrf csrf) {
        return csrf == null;
    }

    private boolean isModifying(HttpMethod request) {
        return request == POST || request == PUT || request == DELETE;
    }

    protected String html200From(MockHttpServletRequestBuilder request) throws Exception {
        return htmlFrom(request, status().isOk());
    }

    protected String htmlFrom(MockHttpServletRequestBuilder request, ResultMatcher resultMatcher) throws Exception {
        return responseFrom(request, resultMatcher).getContentAsString();
    }

    protected MockHttpServletResponse responseFrom(MockHttpServletRequestBuilder request, ResultMatcher resultMatcher)
            throws Exception {
        return mvc.perform(request)
                .andExpect(resultMatcher)
                .andReturn().getResponse();
    }

}
