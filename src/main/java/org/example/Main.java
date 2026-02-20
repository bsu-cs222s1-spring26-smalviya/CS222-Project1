package org.example;

public class Main {
    public static void main(String[] args) {
        Input input = new Input();
        Output output = new Output();
        Requester requester = new Requester();
        RevisionParser parser = new RevisionParser();

        try {
            String title = input.getUserInput();

            String json = requester.fetchRecentRevisions(title);

            output.printRedirect(parser.getRedirectTarget(json));

            output.printRevisions(parser.parse(json));

        } catch (WikiException e) {
            output.printErrorMessage(e);
        }

    }
}