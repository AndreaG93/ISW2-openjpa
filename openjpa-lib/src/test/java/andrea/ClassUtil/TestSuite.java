package andrea.ClassUtil;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

    @RunWith(Suite.class)
    @Suite.SuiteClasses({
            TestGetPackageNameClass.class,
            TestGetPackageNameString.class,
            TestGetClassNameClass.class,
            TestGetClassNameString.class,
            TestToClass.class,
            TestToClassFalseResolve.class,
    })
    public class TestSuite {
    }

