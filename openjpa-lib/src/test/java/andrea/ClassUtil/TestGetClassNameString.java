package andrea.ClassUtil;

import org.apache.openjpa.lib.util.ClassUtil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestGetClassNameString {

    @Test
    public void validTestCase_1() {

        try {

            String output = ClassUtil.getClassName("javax.swing.JSpinner$DefaultEditor");
            Assert.assertEquals(output, "DefaultEditor");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void validTestCase_2() {

        try {

            String output = ClassUtil.getClassName("java.lang.String");
            Assert.assertEquals(output, "String");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void validTestCase_3() {

        try {

            String output = ClassUtil.getClassName("java.awt.Point[][]");
            Assert.assertEquals(output, "Point[][]");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void invalidTestCase_1() {

        try {

            String output = ClassUtil.getClassName("");
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_2() {

        try {

            String output = ClassUtil.getClassName("$%&andrea..invalid..binary*-+");
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_3() {

        try {

            String output = ClassUtil.getClassName((String) null);
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }
}
