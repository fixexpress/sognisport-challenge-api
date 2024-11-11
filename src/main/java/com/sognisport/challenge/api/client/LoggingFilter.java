package com.sognisport.challenge.api.client;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class LoggingFilter implements ClientRequestFilter {

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class);

    @Override
    public void filter(ClientRequestContext requestContext) {
        String url = requestContext.getUri().toString();
        LOG.info("Chamando a URL: " + url);
    }
}
