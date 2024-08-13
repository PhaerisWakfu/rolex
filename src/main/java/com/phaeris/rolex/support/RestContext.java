package com.phaeris.rolex.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author wyh
 * @since 2023/11/13
 */
public class RestContext {

    private static RestTemplate restTemplate;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    public void setUp(RestTemplate restTemplate) {
        RestContext.restTemplate = restTemplate;
    }

    public static RestTemplate get() {
        return restTemplate;
    }
}
