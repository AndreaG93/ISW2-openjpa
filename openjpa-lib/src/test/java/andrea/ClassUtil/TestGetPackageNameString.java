package andrea.ClassUtil;

import org.apache.openjpa.lib.util.ClassUtil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestGetPackageNameString {

    @Test
    public void validTestCase_1() {

        try {

            String output = ClassUtil.getPackageName("javax.swing.JSpinner$DefaultEditor");
            Assert.assertEquals(output, "javax.swing");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void validTestCase_2() {

        try {

            String output = ClassUtil.getPackageName("java.lang.String");
            Assert.assertEquals(output, "java.lang");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void validTestCase_3() {

        try {

            String output = ClassUtil.getPackageName("java.awt.Point[][]");
            Assert.assertEquals(output, "java.awt");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void invalidTestCase_1() {

        try {

            String output = ClassUtil.getPackageName("");
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_2() {

        try {

            String output = ClassUtil.getPackageName("$%&andrea..invalid..binary*-+");
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_3() {

        try {

            String output = ClassUtil.getPackageName((String) null);
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }
}
