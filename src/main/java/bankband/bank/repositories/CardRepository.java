package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Account;
import bankband.bank.models.Card;
import bankband.bank.services.Auth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    private Connection conn = Database.getInstance().getConnection();


    public Integer create(Card card) {
        String sql = "INSERT INTO cards (number, expiration, ccv, pin, image, account_id, card_limit)" +
                "VALUES (?,?,?,?,?,?,?)";


        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, card.getNumber());
            stmt.setDate(2, new Date(card.getExpirationDate().getTime()));
            stmt.setInt(3, card.getCcv());
            stmt.setString(4, card.getPin());
            stmt.setString(5, card.getImage());
            stmt.setInt(6, card.getAccount().getId());
            stmt.setInt(7, card.getLimit());
            stmt.execute();

            sql = "SELECT last_insert_rowid();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            card.setId(rs.getInt(1));
            return card.getId();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Card> findAllForAccount(Account account) {
        ArrayList<Card> list = new ArrayList<>();
        AccountRepository repo = new AccountRepository();

        List<Account> accounts = Auth.get().getUser().getAccounts();

        String sql = "SELECT * FROM cards WHERE account_id = ?";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, account.getId());
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {

                Card card = new Card();
                card.setLimit(rs.getInt("card_limit"));
                card.setPin(rs.getString("pin"));
                card.setImage(rs.getString("image"));
                card.setExpirationDate(rs.getDate("expiration"));
                card.setCcv(rs.getInt("ccv"));
                card.setNumber(rs.getInt("number"));
                card.setAccount(account);
                card.setId(rs.getInt("id"));


                list.add(card);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;

    }

    public boolean update(Card card) {
        String sql = "UPDATE cards SET number = ?, expiration = ?, ccv = ? , pin = ?, image = ?, account_id = ?, card_limit =?" +
                " WHERE id = ? ";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, card.getNumber());
            stmt.setDate(2, new Date(card.getExpirationDate().getTime()));
            stmt.setInt(3, card.getCcv());
            stmt.setString(4, card.getPin());
            stmt.setString(5, card.getImage());
            stmt.setInt(6, card.getAccount().getId());
            stmt.setInt(7, card.getLimit());
            stmt.setInt(8, card.getId());
            stmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(Card card) {
        String sql = "DELETE FROM cards WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, card.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
