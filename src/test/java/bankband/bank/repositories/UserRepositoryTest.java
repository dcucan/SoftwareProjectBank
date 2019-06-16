package bankband.bank.repositories;

import bankband.bank.BaseTest;
import bankband.bank.models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserRepositoryTest extends BaseTest {

    private UserRepository repo;

    @Before
    public void before() throws Exception {
        super.before();

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

    @Test
    public void itFindsById() {
        User user1 = new User();
        user1.setName("Franta");
        user1.setSurname("Sídlo");
        user1.setEmail("franta@sadlo.cz");
        user1.setPassword("password");

        User user2 = new User();
        user2.setName("Ana");
        user2.setSurname("Frankova");
        user2.setEmail("ffr@email.cz");
        user2.setPassword("pass");

        Integer id1 = repo.create(user1);
        Integer id2 = repo.create(user2);

        assertNotEquals(id1, id2);

        assertEquals(user1, repo.findById(id1));
        assertEquals(user2, repo.findById(id2));
        assertEquals(null, repo.findById(123));
    }

    @Test
    public void itFindsByEmail() {
        User user1 = new User();
        user1.setName("Franta");
        user1.setSurname("Sídlo");
        user1.setEmail("franta@sadlo.cz");
        user1.setPassword("password");

        User user2 = new User();
        user2.setName("Ana");
        user2.setSurname("Frankova");
        user2.setEmail("ffr@email.cz");
        user2.setPassword("pass");

        Integer id1 = repo.create(user1);
        Integer id2 = repo.create(user2);

        assertEquals(user1, repo.findByEmail(user1.getEmail()));
    }

    @Test
    public void itFindsAll() {
        User user1 = new User();
        user1.setName("Franta");
        user1.setSurname("Sídlo");
        user1.setEmail("franta@sadlo.cz");
        user1.setPassword("password");

        User user2 = new User();
        user2.setName("Ana");
        user2.setSurname("Frankova");
        user2.setEmail("ffr@email.cz");
        user2.setPassword("pass");

        ArrayList<User> list = new ArrayList<>();

        assertEquals(list, repo.findAll());


        Integer id1 = repo.create(user1);

        list.add(user1);
        assertEquals(list, repo.findAll());


        Integer id2 = repo.create(user2);


        list.add(user2);
        assertEquals(list, repo.findAll());
    }
}
