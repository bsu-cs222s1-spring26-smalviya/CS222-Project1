package org.example;

import java.util.List;

public class Output {
    public void printErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }

    public void printRevisions(List<Revision> revisions) {
        if (revisions == null || revisions.isEmpty()) {
            System.out.println("No revisions found");
            return;
        }
        int i = 1;
        for (Revision revision : revisions) {
            System.out.printf("%d  %s  %s\n", i, revision.getTimestamp(), revision.getUser());
            i++;
        }
    }

    public void printRedirect(String target) {
        if (target != null) {
            System.out.println("Redirected to " + target);
        }
    }
}
