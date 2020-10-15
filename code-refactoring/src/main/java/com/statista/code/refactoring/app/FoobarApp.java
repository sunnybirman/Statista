package com.statista.code.refactoring.app;

import com.statista.code.refactoring.controller.FoobarController;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class FoobarApp extends Application<FoobarConfig>  {

    @Override
    public void run(FoobarConfig foobarConfig, Environment environment) throws Exception {
        environment.jersey().register(FoobarController.class);
    }
}
