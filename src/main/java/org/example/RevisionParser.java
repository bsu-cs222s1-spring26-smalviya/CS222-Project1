package org.example;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.PathNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RevisionParser {

    public String getRedirectTarget(String json) throws ParseException {
        if (json == null || json.isEmpty()) {
            throw new ParseException("JSON is empty.");
        }
        try {
            List<Map<String, String>> redirects = JsonPath.read(json, "$.query.redirects[*]");

            if (redirects != null && !redirects.isEmpty()) {
                return redirects.get(0).get("to");
            }
        } catch (PathNotFoundException e) {
            return null;
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
        return null;
    }


    public List<Revision> parse(String json) throws ParseException {
        if (json == null || json.isEmpty()) {
            throw new ParseException("JSON is empty.");
        }

        try {
            List<Map<String, String>> rawRevisions = JsonPath.read(json, "$.query.pages.*.revisions[*]");
            List<Revision> list = new ArrayList<>();

            for (Map<String, String> raw : rawRevisions) {
                String user = raw.get("user");
                String timestamp = raw.get("timestamp");

                list.add(new Revision(user, timestamp));
            }

            return list;
        } catch (Exception e) {
            throw new ParseException("Failed to parse JSON: " + e.getMessage());
        }
    }
}
