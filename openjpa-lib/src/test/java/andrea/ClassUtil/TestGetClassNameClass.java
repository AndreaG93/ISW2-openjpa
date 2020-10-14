package andrea.ClassUtil;

import org.apache.openjpa.lib.util.ClassUtil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestGetClassNameClass {

    @Test
    public void validTestCase_1() {

        try {

            Class<?> myClass = Class.forName("java.lang.String");

            String output = ClassUtil.getClassName(myClass);
            Assert.assertEquals(output, "String");

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void invalidTestCase_1() {

        try {

            String output = ClassUtil.getClassName((Class) null);
            Assert.assertNull(output);

        } catch (Exception exception) {
            // Expected
        }
    }
}
