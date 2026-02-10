import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.example.ParseException;
import org.example.RevisionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RevisionParserTest {

    @Test
    public void getRedirectTargetTestNullFile() throws ParseException {
        RevisionParser revisionParser = new RevisionParser();
        String json = null;
        assertThrows(ParseException.class, () -> {
            String result = revisionParser.getRedirectTarget(json);
        });
    }

    @Test
    public void getRedirectTargetTestEmptyFile() throws ParseException {
        RevisionParser revisionParser = new RevisionParser();
        String json = "";
        assertThrows(ParseException.class, () -> {
            String result = revisionParser.getRedirectTarget(json);
        });
    }

    @Test
    public void getRedirectTargetTestMismatchFile() throws ParseException {
        RevisionParser revisionParser = new RevisionParser();
        String json = "asdfasdfwegaehgWGBN";
        String result = revisionParser.getRedirectTarget(json);
        Assertions.assertNull(result);
    }

    @Test
    public void getRedirectTargetTestTrueJsonFile() throws ParseException {
        RevisionParser revisionParser = new RevisionParser();
        String json = "{\n" +
                "  \"continue\": {\n" +
                "    \"rvcontinue\": \"20260112014711|1332469665\",\n" +
                "    \"continue\": \"||\"\n" +
                "  },\n" +
                "  \"query\": {\n" +
                "    \"redirects\": [\n" +
                "      {\n" +
                "        \"from\": \"Obama\",\n" +
                "        \"to\": \"Barack Obama\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"pages\": {\n" +
                "      \"534366\": {\n" +
                "        \"pageid\": 534366,\n" +
                "        \"ns\": 0,\n" +
                "        \"title\": \"Barack Obama\",\n" +
                "        \"revisions\": [\n" +
                "          {\n" +
                "            \"user\": \"LWG\",\n" +
                "            \"timestamp\": \"2026-02-08T02:25:53Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Gråbergs Gråa Sång\",\n" +
                "            \"timestamp\": \"2026-02-02T05:56:44Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Kpop777\",\n" +
                "            \"timestamp\": \"2026-02-01T23:12:05Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"ChomponU Pongsawet\",\n" +
                "            \"timestamp\": \"2026-01-30T10:56:29Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Vycl1994\",\n" +
                "            \"timestamp\": \"2026-01-29T00:14:14Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"P. Waffleton\",\n" +
                "            \"timestamp\": \"2026-01-19T18:32:36Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Huldra\",\n" +
                "            \"timestamp\": \"2026-01-16T22:36:43Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Muboshgu\",\n" +
                "            \"timestamp\": \"2026-01-12T20:43:29Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"GRuban\",\n" +
                "            \"timestamp\": \"2026-01-12T20:00:29Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"IrisChronomia\",\n" +
                "            \"timestamp\": \"2026-01-12T17:23:51Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Isjadd773\",\n" +
                "            \"timestamp\": \"2026-01-12T16:40:26Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"Isjadd773\",\n" +
                "            \"timestamp\": \"2026-01-12T16:35:33Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"IrisChronomia\",\n" +
                "            \"timestamp\": \"2026-01-12T06:25:04Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"IrisChronomia\",\n" +
                "            \"timestamp\": \"2026-01-12T06:23:04Z\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"user\": \"IrisChronomia\",\n" +
                "            \"timestamp\": \"2026-01-12T06:20:13Z\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String result = revisionParser.getRedirectTarget(json);
        Assertions.assertEquals(result,"Barack Obama");
    }
}
