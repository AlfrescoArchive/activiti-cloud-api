package org.activiti.cloud.api.process.model.impl;

public class CandidateUser {

    String user;

    public CandidateUser(String users) {
        this.user = users;
    }

    public CandidateUser() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
