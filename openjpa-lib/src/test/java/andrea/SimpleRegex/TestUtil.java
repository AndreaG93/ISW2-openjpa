package andrea.SimpleRegex;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public abstract class TestUtil {

    protected boolean caseInsensitive;

    public TestUtil(boolean input) {
        this.caseInsensitive = input;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true}, {false}
        });
    }
}
