package bankband.bank;

import org.junit.Before;

public abstract class BaseTest {

    @Before
    public void before() throws Exception {
        Config.DB_NAME = ":memory:";
        Database.getInstance().install();
    }
}
