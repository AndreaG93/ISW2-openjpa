package andrea.SimpleRegex;

import org.apache.openjpa.lib.util.SimpleRegex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class TestSimpleRegex extends TestUtil{

    public TestSimpleRegex(boolean input) {
        super(input);
    }

    @Test
    public void validTestCase_1() {

        try {

            SimpleRegex simpleRegex = new SimpleRegex("a.*b", caseInsensitive);
            boolean output = simpleRegex.matches("a andrea graziani b");

            assertTrue(output);


        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void invalidTestCase_1() {

        try {

            new SimpleRegex("", caseInsensitive);
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_2() {

        try {

            SimpleRegex simpleRegex = new SimpleRegex("a.*(r|t)b\t", caseInsensitive);
            boolean output = simpleRegex.matches("a andrea rb    ");    // Correct match according to RegEx specification...
            assertFalse(output);                                              // Because not supported
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_3() {

        try {

            SimpleRegex simpleRegex = new SimpleRegex("a*", caseInsensitive);
            boolean output = simpleRegex.matches("aaaaaaaa");    // Correct match according to RegEx specification...
            assertFalse(output);                                       // Because not supported
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_4() {

        try {

            SimpleRegex simpleRegex = new SimpleRegex("\t", caseInsensitive);
            boolean output = simpleRegex.matches("  ");    // Correct match according to RegEx specification...
            assertFalse(output);                                 // Because not supported
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_5() {

        try {

            new SimpleRegex(null, caseInsensitive);
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }
}
