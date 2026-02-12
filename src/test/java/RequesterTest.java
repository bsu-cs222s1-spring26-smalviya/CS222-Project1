import org.example.ParseException;
import org.example.Requester;
import org.example.WikiException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RequesterTest {
    @Test
    void fetchRecentRevisions_blankTitle_throwsParseException() {
        Requester requester = new Requester();
        assertThrows(ParseException.class, () -> requester.fetchRecentRevisions("   "));
    }

    @Test
    void fetchRecentRevisions_nullTitle_throwsParseException() {
        Requester requester = new Requester();
        assertThrows(ParseException.class, () -> requester.fetchRecentRevisions(null));
    }
    @Test
    void fetchRecentRevisions_java_returnsJson_NotNull() throws WikiException {
        Requester requester = new Requester();

        String body = requester.fetchRecentRevisions("Java");

        assertNotNull(body);

    }
    @Test
    void fetchRecentRevisions_java_returnsJson_Body_Not_Blank() throws WikiException {
        Requester requester = new Requester();

        String body = requester.fetchRecentRevisions("Java");


        assertFalse(body.isBlank());

    }
    @Test
    void fetchRecentRevisions_java_returnsJson() throws WikiException {
        Requester requester = new Requester();

        String body = requester.fetchRecentRevisions("Java");



        assertTrue(body.trim().startsWith("{"));
        assertTrue(body.contains("\"query\""));
        assertTrue(body.contains("\"revisions\""));
    }
}
