package andrea.SimpleRegex;

import org.apache.openjpa.lib.util.SimpleRegex;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMatches extends TestUtil {

    public TestMatches(boolean input) {
        super(input);
    }

    @Test
    public void validTestCase_1() {

        SimpleRegex simpleRegex = new SimpleRegex(".*", caseInsensitive);
        boolean output = simpleRegex.matches("Andrea1234\t= ");
        assertTrue(output);
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

    @Test
    public void additionalTest_1() {

        SimpleRegex simpleRegex = new SimpleRegex("The .*br.wn.*d.g", true);
        boolean output = simpleRegex.matches("the quick BRown fox jumped over the lazy dog");
        assertTrue(output);
    }

    @Test
    public void additionalTest_2() {

        SimpleRegex simpleRegex = new SimpleRegex("h.*lo", false);
        boolean output = simpleRegex.matches("Hello");
        assertFalse(output);
    }

    @Test
    public void additionalTest_3() {

        SimpleRegex simpleRegex = new SimpleRegex(".*YoHello", false);
        boolean output = simpleRegex.matches("Hellow");
        assertFalse(output);
    }
}


