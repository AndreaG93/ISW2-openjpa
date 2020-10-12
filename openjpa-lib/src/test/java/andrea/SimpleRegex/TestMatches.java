package andrea.SimpleRegex;

import org.apache.openjpa.lib.util.SimpleRegex;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestMatches extends TestUtil {

    public TestMatches(boolean input) {
        super(input);
    }

    @Test
    public void validTestCase_1() {

        try {

            SimpleRegex simpleRegex = new SimpleRegex(".*", caseInsensitive);
            boolean output = simpleRegex.matches("Andrea1234\t= ");
            assertTrue(output);

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_1() {

        try {

            SimpleRegex simpleRegex = new SimpleRegex(".*", caseInsensitive);
            simpleRegex.matches(null);
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }
}
