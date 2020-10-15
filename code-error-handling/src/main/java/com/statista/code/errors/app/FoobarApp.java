package com.statista.code.errors.app;

import com.statista.code.errors.controller.AcmeController;
import com.statista.code.errors.services.AcmeMailService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class FoobarApp extends Application<FoobarConfig>  {

    @Override
    public void run(FoobarConfig foobarConfig, Environment environment) throws Exception {
        environment.jersey().register(new AcmeController(new AcmeMailService()));
    }
}
