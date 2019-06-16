package bankband.bank.repositories;

import bankband.bank.BaseTest;
import bankband.bank.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserRepositoryTest extends BaseTest {

    private UserRepository repo;

    @Before
    public void before() {
        repo = new UserRepository();
    }

    @Test
    public void itStoresUserIntoDatabase() {
        User user = new User();
        user.setName("Franta");
        user.setSurname("Sídlo");
        user.setEmail("franta@sadlo.cz");
        user.setPassword("password");

        Integer id = repo.create(user);

        assertNotNull(id);
    }
}
