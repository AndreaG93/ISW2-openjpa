package andrea.ClassUtil;

import org.apache.openjpa.lib.util.ClassUtil;
import org.junit.Assert;
import org.junit.Test;

public class OtherTests {

    @Test
    public void additionalTest_1() {

        Class<?> output1 = ClassUtil.toClass("byte", false, this.getClass().getClassLoader());
        Assert.assertEquals(byte.class, output1);

        Class<?> output2 = ClassUtil.toClass("char", false, this.getClass().getClassLoader());
        Assert.assertEquals(char.class, output2);
    }

    @Test
    public void additionalTest_2() {

        Class<?> output1 = ClassUtil.toClass("long[][][]", false, this.getClass().getClassLoader());
        Assert.assertEquals(new long[0][0][0].getClass(), output1);
    }

    @Test
    public void additionalTest_3() {

        String output1 = ClassUtil.getClassName(byte.class);
        Assert.assertEquals("byte", output1);

        String output2 = ClassUtil.getClassName(char.class);
        Assert.assertEquals("char", output2);

        String output3 = ClassUtil.getClassName(MyInnerClass[].class);
        Assert.assertEquals("OtherTests$MyInnerClass[]", output3);

        String output4 = ClassUtil.getClassName(MyInnerClass[][].class);
        Assert.assertEquals("OtherTests$MyInnerClass[][]", output4);
    }

    @Test
    public void additionalTest_4() {

        String output = ClassUtil.getClassName(new long[0][0][0].getClass());
        Assert.assertEquals("float[][][]", output);
    }

    @Test
    public void additionalTest_5() {

        String output1 = ClassUtil.getPackageName(long.class);
        Assert.assertEquals("", output1);

        String output2 = ClassUtil.getPackageName(long[].class);
        Assert.assertEquals("", output2);

        String output3 = ClassUtil.getPackageName(MyInnerClass[].class);
        Assert.assertEquals("andrea.ClassUtil", output3);
    }

    @Test
    public void additionalTest_6() {

        String output = ClassUtil.getClassName("[[[Lndrea");

        Assert.assertEquals("ndrea[][][]", output);
    }

    private static abstract class MyInnerClass {
    }
}
