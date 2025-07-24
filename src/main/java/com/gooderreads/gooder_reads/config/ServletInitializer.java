package com.gooderreads.gooder_reads.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Return classes providing configuration for the root application context
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Return classes providing configuration for the DispatcherServlet's application context
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        // Map the DispatcherServlet to handle all requests
        return new String[] { "/" };
    }

    
}
