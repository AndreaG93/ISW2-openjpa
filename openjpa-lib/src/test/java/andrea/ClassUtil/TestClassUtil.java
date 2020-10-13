package andrea.ClassUtil;

import andrea.SimpleRegex.TestUtil;
import org.apache.openjpa.lib.util.ClassUtil;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.SimpleRegex;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.Assert.assertTrue;

public class TestClassUtil {

    interface SomeInterface {
        void someInterfaceMethod(String s);
    }




    @Test
    public void validT43423423estCase_1() {

        try {

            SomeInterface ff = new SomeInterface() {
                @Override
                public void someInterfaceMethod(String s) {

                }
            };


            Class aaa = ff.getClass();

            String dd = ClassUtil.getPackageName("javax.swing.JSpinner$DefaultEditor");
            System.out.println(dd);


        } catch (Exception exception) {
            // Expected
        }
    }





    @Test
    public void validTestCase098_1() throws Exception {

        File file = new File("/Andrea/");

        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();          // file:/c:/myclasses/
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);

            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany
            Class cls = cl.loadClass("java.awt.Point[][]");

            System.out.println(cls.getName());

        } catch (MalformedURLException e) {
        } catch (ClassNotFoundException e) {
        }


            //System.out.println(loader.toString());




    }

    @Test
    public void validTestCas664e098_1() throws Exception {

        File file = new File("./src/test/java/andrea/ClassUtil");

        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();          // file:/c:/myclasses/
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);
            ClassLoader loader = Thread.currentThread().getContextClassLoader();


            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany
            Class cls = ClassUtil.toClass("andrea.Test", cl);
            //Class cls = ClassUtil.toClass("andrea.Test", loader);

            Object main = cls.newInstance();
            Method test = cls.getMethod("show");
            test.invoke(main);

            System.out.println("Classloader of this class:"
                    + cls.getClassLoader());

            System.out.println("Classloader of Logging:"
                    + TestUtil.class.getClassLoader());


        } catch (Exception e) {

        }
    }

    @Test
    public void fsadf() {

        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }

    }
}
