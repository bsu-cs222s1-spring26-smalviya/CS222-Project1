package org.example;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
public class Requester {

    private static final String Web = "https://en.wikipedia.org/w/api.php";
    private static final int numbersOfRevisionReturned = 15;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String fetchRecentRevisions(String title) throws WikiException {

        if (title == null || title.trim().isEmpty()) {
            throw new ParseException("empty title");
        }

        String encoded = URLEncoder.encode(title.trim(), StandardCharsets.UTF_8);


        String url = Web
                + "?action=query"
                + "&format=json"
                + "&prop=revisions"
                + "&titles=" + encoded
                + "&rvprop=timestamp%7Cuser"
                + "&rvlimit=" + numbersOfRevisionReturned
                + "&redirects";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().header("Accept", "application/json").header("User-Agent", "RevisionReporter/1.0 (BSU CS222)").build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ApiException("Wikipedia API returned HTTP " + response.statusCode() + ".");
            }

            String body = response.body();
            if (body == null || body.isBlank()) {
                throw new ApiException("Wikipedia API returned empty body.");
            }

            return body;

        } catch (IOException e) {
            throw new NetworkException("Network failure while calling Wikipedia.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new NetworkException("Request interrupted.", e);
        }
    }
}
