package com.primebank.web.rest;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApplication extends ResourceConfig  {

    public RestApplication() {
        packages("com.primebank.web.rest");
        register(MultiPartFeature.class);
    }

}
