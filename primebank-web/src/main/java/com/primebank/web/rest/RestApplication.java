package com.primebank.web.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApplication extends ResourceConfig  {

    public RestApplication() {
        packages("com.primebank.web.rest");
        register(MultiPartFeature.class);
    }

}
