package org.example;

import java.util.List;

public class Output {
    public void print(String message) {
        System.out.println(message);
    }

    public void printErorrMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    public void print(List<Revision> revisions) {
        if (revisions.isEmpty()) {
            System.out.println("No revisions found");
            return;
        }
        int i = 1;
        for (Revision revision : revisions) {
            System.out.printf("%d: %s %s\n", i, revision.getTimestamp(), revision.getUser());
            i++;
        }
    }

    public void printRedirect(String origin, String target) {
        if (target != null) {
            System.out.println("Redirect: " + origin + " -> " + target);
        } else {
            System.out.println("No Redirect found");
        }
    }
}
