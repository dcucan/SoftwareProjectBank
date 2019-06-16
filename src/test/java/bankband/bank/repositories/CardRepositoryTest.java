package bankband.bank.repositories;

import bankband.bank.BaseTest;
import bankband.bank.models.Account;
import bankband.bank.models.Card;
import bankband.bank.models.User;
import bankband.bank.services.Auth;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CardRepositoryTest extends BaseTest {
    private CardRepository repo;

    private User user;

    private Account account;

    @Before
    public void before() throws Exception {
        super.before();

        repo = new CardRepository();

        user = new User();
        user.setName("Franta");
        user.setSurname("Sídlo");
        user.setEmail("franta@sadlo.cz");
        user.setPassword("password");

        UserRepository userRepository = new UserRepository();
        userRepository.create(user);

        Auth.get().setUser(user);

        account = new Account();
        account.setBalance(0);
        account.setNumber(123566789);
        account.setType("Type");
        account.setPostNumber(6400);
        account.setUserId(user.getId());

        AccountRepository accountRepository = new AccountRepository();
        accountRepository.create(account);

    }

    @Test
    public void itCreates() {


        Card card1 = new Card();
        Card card2 = new Card();

        card1.setAccount(account);
        card1.setNumber(123456789);
        card1.setCcv(123);
        card1.setExpirationDate(new Date());
        card1.setLimit(100);
        card1.setPin("1234");
        card1.setImage("Nature");

        Integer id1 = repo.create(card1);

        assertNotNull(id1);

    }

    @Test
    public void itFindsForAll(){
        Card card1 = new Card();
        Card card2 = new Card();

        card1.setAccount(account);
        card1.setNumber(123456789);
        card1.setCcv(123);
        card1.setExpirationDate(new Date());
        card1.setLimit(100);
        card1.setPin("1234");
        card1.setImage("Nature");

        card2.setAccount(account);
        card2.setNumber(333456789);
        card2.setCcv(123);
        card2.setExpirationDate(new Date());
        card2.setLimit(100);
        card2.setPin("1234");
        card2.setImage("Nature");

        repo.create(card1);
        repo.create(card2);

        ArrayList<Card> list = new ArrayList<>();
        list.add(card1);
        list.add(card2);

        assertEquals(list, repo.findAllForAccount(account));
    }


}
