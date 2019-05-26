package bankband.bank;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class DatabaseTest {

    @Test
    public void itWorks() throws SQLException {
        Connection c = Database.getInstance().getConnection();

        assertNotNull(c);

        assertFalse(c.isClosed());
    }

}
