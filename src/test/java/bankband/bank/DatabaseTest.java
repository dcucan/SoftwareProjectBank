package bankband.bank;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {

    @Before
    public void before() {
        Config.DB_NAME = ":memory:";
    }

    @Test
    public void itWorks() throws SQLException {
        Connection c = Database.getInstance().getConnection();

        assertNotNull(c);

        assertFalse(c.isClosed());
    }


    @Test
    public void installationCanBeExecutedTwoTimes() throws Exception {
        assertTrue(Database.getInstance().install());
        assertTrue(Database.getInstance().install());
    }

}
