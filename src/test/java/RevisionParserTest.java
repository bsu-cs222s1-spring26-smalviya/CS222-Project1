
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.example.ParseException;
import org.example.Revision;
import org.example.RevisionParser;
import org.example.WikiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RevisionParserTest {


    @Test
    public void getRedirectTarget_givenNull_throwsException() {
        RevisionParser revisionParser = new RevisionParser();
        String json = null;
        assertThrows(ParseException.class, () -> {
            revisionParser.getRedirectTarget(json);
        });
    }

    @Test
    public void getRedirectTarget_givenEmptyString_throwsException() {
        RevisionParser revisionParser = new RevisionParser();
        String json = "";
        assertThrows(ParseException.class, () -> {
            revisionParser.getRedirectTarget(json);
        });
    }

    @Test
    public void getRedirectTarget_givenMalformedJson_returnsNull() throws ParseException {
        RevisionParser revisionParser = new RevisionParser();
        String json = "asdfasdfwegaehgWGBN";
        String result = revisionParser.getRedirectTarget(json);
        Assertions.assertNull(result);
    }

    @Test
    public void getRedirectTarget_givenValidJson_returnsTargetTitle() throws Exception {
        RevisionParser revisionParser = new RevisionParser();
        String json = readJsonFromFile("test.json");
        String result = revisionParser.getRedirectTarget(json);
        Assertions.assertEquals("Barack Obama", result);
    }

    @Test
    public void parse_givenNull_throwsException() {
        RevisionParser revisionParser = new RevisionParser();
        String json = null;
        assertThrows(ParseException.class, () -> {
            revisionParser.parse(json);
        });
    }

    @Test
    public void parse_givenEmptyString_throwsException() {
        RevisionParser revisionParser = new RevisionParser();
        String json = "";
        assertThrows(ParseException.class, () -> {
            revisionParser.parse(json);
        });
    }

    @Test
    public void parse_givenMalformedJson_throwsException() {
        RevisionParser revisionParser = new RevisionParser();
        String json = "asgsrht6uybwetqmw64ubw35gtfcw34w";
        assertThrows(ParseException.class, () -> {
            revisionParser.parse(json);
        });
    }

    @Test
    public void parse_givenValidJson_returnsRevisionList() throws Exception {
        RevisionParser revisionParser = new RevisionParser();
        String json = readJsonFromFile("test.json");

        List<Revision> result = revisionParser.parse(json);

        String stringResult = "";
        for (Revision revision : result) {
            stringResult = stringResult + revision.getTimestamp() + " " + revision.getUser() + "\n";
        }
        Assertions.assertEquals(stringResult, "2026-02-08T02:25:53Z LWG\n" +
                "2026-02-02T05:56:44Z Gråbergs Gråa Sång\n" +
                "2026-02-01T23:12:05Z Kpop777\n" +
                "2026-01-30T10:56:29Z ChomponU Pongsawet\n" +
                "2026-01-29T00:14:14Z Vycl1994\n" +
                "2026-01-19T18:32:36Z P. Waffleton\n" +
                "2026-01-16T22:36:43Z Huldra\n" +
                "2026-01-12T20:43:29Z Muboshgu\n" +
                "2026-01-12T20:00:29Z GRuban\n" +
                "2026-01-12T17:23:51Z IrisChronomia\n" +
                "2026-01-12T16:40:26Z Isjadd773\n" +
                "2026-01-12T16:35:33Z Isjadd773\n" +
                "2026-01-12T06:25:04Z IrisChronomia\n" +
                "2026-01-12T06:23:04Z IrisChronomia\n" +
                "2026-01-12T06:20:13Z IrisChronomia\n");
    }

    @Test
    public void parse_givenMissingPageJson_throwsNotFoundException() {
        RevisionParser revisionParser = new RevisionParser();
        String missingPageJson = "{\n" +
                "    \"batchcomplete\": \"\",\n" +
                "    \"query\": {\n" +
                "        \"pages\": {\n" +
                "            \"-1\": {\n" +
                "                \"ns\": 0,\n" +
                "                \"title\": \"Sdfgsdfg\",\n" +
                "                \"missing\": \"\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        assertThrows(org.example.NotFoundException.class, () -> {
            revisionParser.parse(missingPageJson);
        });
    }

    private String readJsonFromFile(String filename) throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (stream == null) {
                throw new IOException("File not found in resources: " + filename);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
