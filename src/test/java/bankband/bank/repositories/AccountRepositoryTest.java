package bankband.bank.repositories;

import bankband.bank.BaseTest;
import bankband.bank.models.Account;
import bankband.bank.models.User;
import bankband.bank.services.Auth;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryTest extends BaseTest {

    private AccountRepository repo;

    private User user;

    @Before
    public void before() throws Exception {
        super.before();

        repo = new AccountRepository();

        user = new User();
        user.setName("Franta");
        user.setSurname("Sídlo");
        user.setEmail("franta@sadlo.cz");
        user.setPassword("password");

        UserRepository userRepository = new UserRepository();
        userRepository.create(user);

        Auth.get().setUser(user);
    }

    @Test
    public void itFindsByNumber() {
        Account account = new Account();
        account.setBalance(0);
        account.setNumber(123566789);
        account.setType("Type");
        account.setPostNumber(6400);
        account.setUserId(user.getId());

        repo.create(account);

        assertEquals(account, repo.findByNumber(account.getNumber()));

    }

    @Test
    public void itFindsById() {
        Account account = new Account();
        account.setBalance(0);
        account.setNumber(123566789);
        account.setType("Type");
        account.setPostNumber(6400);
        account.setUserId(user.getId());

        repo.create(account);

        assertEquals(account, repo.findById(account.getId()));

    }

    @Test
    public void itFindsAllForUser(){
        Account account1 = new Account();
        account1.setBalance(0);
        account1.setNumber(123566789);
        account1.setType("Type");
        account1.setPostNumber(6400);
        account1.setUserId(user.getId());

        repo.create(account1);

        Account account2 = new Account();
        account2.setBalance(0);
        account2.setNumber(123566789);
        account2.setType("Type");
        account2.setPostNumber(6400);
        account2.setUserId(user.getId());

        repo.create(account2);

        ArrayList<Account> list = new ArrayList<>();
        list.add(account1);
        list.add(account2);

        assertEquals(list, repo.findAllForUser(user));

    }

    @Test
    public void itCreates(){
        Account account1 = new Account();
        account1.setBalance(0);
        account1.setNumber(123566789);
        account1.setType("Type");
        account1.setPostNumber(6400);
        account1.setUserId(user.getId());

        Integer id1 = repo.create(account1);
        assertNotNull(id1);

        Account account2 = new Account();
        account2.setBalance(0);
        account2.setNumber(123566789);
        account2.setType("Type");
        account2.setPostNumber(6400);
        account2.setUserId(user.getId());

        repo.create(account2);

        assertNotEquals(account1, account2);

        Account account1fresh = repo.findById(id1);
        assertEquals(account1.getBalance(), account1fresh.getBalance());
        assertEquals(account1.getNumber(), account1fresh.getNumber());
        assertEquals(account1.getType(), account1fresh.getType());
        assertEquals(account1.getPostNumber(), account1fresh.getPostNumber());
        assertEquals(account1.getUserId(), account1fresh.getUserId());
    }

    @Test
    public void itUpdates(){
        Account account1 = new Account();
        account1.setBalance(0);
        account1.setNumber(123566789);
        account1.setType("Type");
        account1.setPostNumber(6400);
        account1.setUserId(user.getId());

        Integer id1 = repo.create(account1);
        assertNotNull(id1);

        Account account2 = new Account();
        account2.setBalance(0);
        account2.setNumber(123566789);
        account2.setType("Type");
        account2.setPostNumber(6400);
        account2.setUserId(user.getId());

        Integer id2 = repo.create(account2);
        assertNotNull(id2);

        account1.setBalance(20);
        assertTrue(repo.update(account1));

        Account account1fresh = repo.findById(id1);
        Account account2fresh = repo.findById(id2);

        assertEquals(account1.getBalance(), account1fresh.getBalance());
        assertEquals(account2.getBalance(), account2fresh.getBalance());

        assertNotEquals(account1.getBalance(), account2fresh.getBalance());
        assertNotEquals(account2.getBalance(), account1fresh.getBalance());
    }

}
