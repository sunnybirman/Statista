package com.statista.code.refactoring;

import com.statista.code.refactoring.app.FoobarApp;

/**
 *
 * Getting started
 * - At first have a look at this small Dropwizard application
 * - The application serves a single API via the FoobarController
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
 * - Review the FoobarController, what issues do you spot here?
 * - Please refactor the controller, so that the pseudo business logic can be tested independently. Provide appropriate unit tests.
 * - Consider that in the future processes of another department (hr, human resources) should also be processable.
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
