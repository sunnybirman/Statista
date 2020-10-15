package com.statista.code.errors.controller;

import com.statista.code.errors.api.SendMailCommand;
import com.statista.code.errors.services.MailService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@Path("/acme")
public class AcmeController {

    private final MailService mailService;

    public AcmeController(MailService mailService) {
        this.mailService = mailService;
    }

    @GET
    @Path("/ping")
    public Response pong() {
        return Response.ok("{\"message\": \"Hi!\"}").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/send-invoice")
    public Response sendInvoice(SendMailCommand command) {
        sendInvoiceViaMail(command.getTo());
        return Response.ok().build();
    }

    private void sendInvoiceViaMail(String to) {
        CompletableFuture.runAsync(() -> mailService.sendMail(to, "Your Invoice", new Object()));
    }

}
