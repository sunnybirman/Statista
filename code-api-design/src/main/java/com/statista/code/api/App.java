package com.statista.code.api;

/**
 *
 * Please review this application and the API design of the StringProcessor.
 * What can you spot when reviewing the design and how could the design be changed
 * so that the API is more robust? When changing the API also change the unit tests.
 *
 */
public class App {

    public static void main(String[] args) {

        if(args.length == 0) {
            args = new String[] { "foobar:moin" };
        }

        String input = args[0];

        System.out.println("Processing input: "+input);

        StringProcessor processor = new FoobarStringProcessor();

        if (processor.appliesToValue(input)) {
            System.out.println("Processed input to: " + processor.processValue(input));
        } else {
            System.out.println("Nothing to do");
        }


    }


}
