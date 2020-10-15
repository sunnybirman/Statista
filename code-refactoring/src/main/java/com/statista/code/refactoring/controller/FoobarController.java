package com.statista.code.refactoring.controller;

import com.statista.code.refactoring.api.BusinessCommand;
import com.statista.code.refactoring.api.BusinessProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ThreadLocalRandom;

@Path("/foobar")
public class FoobarController {

    private static final Logger log = LoggerFactory.getLogger(FoobarController.class);

    @GET
    @Path("/ping")
    public Response pong() {
        return Response.ok("{\"message\": \"Hi!\"}").build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/do-business")
    public Response startBusinessProcess(@Valid BusinessCommand command) {

        try {

            if(command.getDepartment().equalsIgnoreCase("sales")) {
                handleSalesProcess(command.getProcessKey());
            }

            if(command.getDepartment().equalsIgnoreCase("procurement")) {
                handleProcurementProcess(command.getProcessKey());
            }

        } catch (BusinessProcessException exception) {
            return Response.status(500).entity("{\"error\": "+exception.getMessage()+" }").build();
        }

        return Response.ok().build();
    }

    private void handleSalesProcess(String processKey) throws BusinessProcessException {

        if(processKey.equalsIgnoreCase("customer-lost")) {
            log.warn("Customer lost");
            throw new BusinessProcessException("Customer lost");
        }

        if(processKey.equalsIgnoreCase("customer-won")) {
            log.info("New customer acquired");
        }

    }

    private void handleProcurementProcess(String processKey) throws BusinessProcessException {

        int pseudoRandomNumber = ThreadLocalRandom.current().nextInt(0, 100);

        if(processKey.equalsIgnoreCase("order") && (pseudoRandomNumber % 2 == 0)) {
            log.error("Could not process order, supplier out of stock!");
            throw new BusinessProcessException("Could not process order, supplier out of stock!");
        }

        if(processKey.equalsIgnoreCase("order")) {
            log.info("Order processed");
        }
    }

}
