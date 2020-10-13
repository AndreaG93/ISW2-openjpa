package andrea.ClassUtil;

import org.apache.openjpa.lib.util.ClassUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class TestToClass {

    private boolean resolve;
    private ClassLoader loader;

    public TestToClass(boolean input1, ClassLoader input2) {
        this.resolve = input1;
        this.loader = input2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        ClassLoader myLoader = null;

        try {

            File file = new File("./src/test/java/andrea/ClassUtil");  // A directory where is present a class not previously loaded...

            URL url = file.toURI().toURL();

            myLoader = new URLClassLoader(new URL[]{url});

        } catch (Exception e) {
            fail(e.getMessage());
        }

        return Arrays.asList(new Object[][]{
                {true, null}, {false, null}, {true, myLoader}, {false, myLoader}
        });
    }

    @Test
    public void validTestCase_1() {

        try {

            Class<?> output = ClassUtil.toClass("javax.swing.JSpinner$DefaultEditor", resolve, loader);
            System.out.println(output.getName());
            Assert.assertNotNull(output);

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void validTestCase_2() {

        try {

            Class<?> output = ClassUtil.toClass("java.lang.String", resolve, loader);
            System.out.println(output.getName());
            Assert.assertNotNull(output);

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void validTestCase_3() {

        try {

            Class<?> output = ClassUtil.toClass("java.awt.Point[][]", resolve, loader);
            System.out.println(output.getName());
            Assert.assertNotNull(output);

        } catch (Exception exception) {
            fail();
        }
    }

    /**
     * To test previous unloaded class with custom ClassLoader
     */
    @Test
    public void validTestCase_4() {

        try {

            if (loader == null)
                return;

            Class<?> output = ClassUtil.toClass("andrea.Test", resolve, loader);
            System.out.println(output.getName());
            Assert.assertNotNull(output);

        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void invalidTestCase_1() {

        try {

            ClassUtil.toClass("", resolve, loader);
            fail();


        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_2() {

        try {

            ClassUtil.toClass("$%&andrea..invalid..binary*-+", resolve, loader);
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_3() {

        try {

            ClassUtil.toClass("andrea.Test", resolve, null);        // Existent .class file but loader cannot load it...
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }

    @Test
    public void invalidTestCase_4() {

        try {

            ClassUtil.toClass(null, resolve, loader);
            fail();

        } catch (Exception exception) {
            // Expected
        }
    }
}
