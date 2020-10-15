package com.statista.code.api.tests;

import com.statista.code.api.FoobarStringProcessor;
import com.statista.code.api.StringProcessor;
import org.junit.Assert;
import org.junit.Test;

public class FoobarStringProcessorTests {

    @Test
    public void foobarStringGetProcessed() {

        StringProcessor foobarStringProcessor = new FoobarStringProcessor();

        String foobarString = "foobar:moin";

        String processedString = foobarStringProcessor.processValue(foobarString);

        Assert.assertEquals("acme:moin", processedString);
    }

    @Test
    public void onlyFoobarStringIsApplicable() {

        StringProcessor foobarStringProcessor = new FoobarStringProcessor();

        String foobarString = "foobar:moin";
        String noneFoobarString = "acme:inc";

        Assert.assertTrue(foobarStringProcessor.appliesToValue(foobarString));
        Assert.assertFalse(foobarStringProcessor.appliesToValue(noneFoobarString));
    }

}
