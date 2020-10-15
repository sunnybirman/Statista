package com.statista.code.errors.api;

public class SendMailCommand {

    private String to;

    public SendMailCommand() {
    }

    public SendMailCommand(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
