package org.example;

public class Revision {
    private final String user;
    private final String timestamp;

    public Revision(String user, String timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }
}
