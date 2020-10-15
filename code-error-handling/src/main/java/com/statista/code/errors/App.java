package com.statista.code.errors;

import com.statista.code.errors.app.FoobarApp;

/**
 *
 * Getting started
 * - At first have a look at this small Dropwizard application
 * - The application serves a single API via the AcmeController
 * - Please try to start the application locally and call the single API endpoint (see api.md for examples)
 * - In IntelliJ it's sufficient to right click on the main method and select "Run|Debug 'App.main()'"
 * - The application starts a local http server, listening on port 8080 and 8081 (the later is not of interest for the task)
 * - I recommend using curl or postman to interact with server
 *
 * Docs
 * - Dropwizard https://www.dropwizard.io/en/latest/
 * - Curl https://gist.github.com/subfuzion/08c5d85437d5d4f00e58
 *
 * Challenge
 * - What problems can you spot, when reviewing the AcmeController?
 * - Make the controller more robust to errors and return proper response values depending on the outcome.
 *
 */
public class App {

    public static void main(String[] args) throws Exception {

        if(args.length == 0) {
            args = new String[] { "server" };
        }

        new FoobarApp().run(args);
    }

}
