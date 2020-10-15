package com.statista.code.refactoring.api;

import javax.validation.constraints.NotNull;

public class BusinessCommand {

    @NotNull
    private String department;

    @NotNull
    private String processKey;

    public BusinessCommand() {
    }

    public BusinessCommand(String department, String processKey) {
        this.department = department;
        this.processKey = processKey;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }
}
