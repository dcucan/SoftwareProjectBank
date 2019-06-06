package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Account;
import bankband.bank.models.User;
import bankband.bank.services.Auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private Connection conn = Database.getInstance().getConnection();

    public Account findByNumber(int number) {
        String sql = "SELECT * FROM accounts WHERE number = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, number);
            ResultSet rs = stmt.executeQuery();

            Account account = new Account();
            account.setPostNumber(rs.getInt("postNumber"));
            account.setNumber(rs.getInt("number"));
            account.setBalance(rs.getInt("balance"));
            account.setType(rs.getString("type"));
            account.setId(rs.getInt("id"));
            account.setUserId(rs.getInt("user_id"));
            return account;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> findAllForUser(User user) {
        ArrayList<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                Account account = new Account();
                account.setPostNumber(rs.getInt("postNumber"));
                account.setNumber(rs.getInt("number"));
                account.setBalance(rs.getInt("balance"));
                account.setType(rs.getString("type"));
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getInt("user_id"));
                list.add(account);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;

    }

    public Integer create(Account account) {
        String sql = "INSERT INTO accounts (number, type, balance, postNumber, user_id)" +
                " VALUES (?, ?, ?, ?, ?);";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, account.getNumber());
            stmt.setString(2, account.getType());
            stmt.setInt(3, account.getBalance());
            stmt.setInt(4, account.getPostNumber());
            stmt.setInt(5, Auth.get().getUser().getId());
            stmt.execute();

            sql = "SELECT last_insert_rowid();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            account.setId(rs.getInt(1));
            return account.getId();

        } catch (SQLException e) {
            e.printStackTrace();

        }


        return null;
    }

    public boolean update(Account account) {
        String sql = "UPDATE accounts SET number = ?, type = ?, balance = ?, postNumber = ?, user_id = ?"
                + "WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, account.getNumber());
            stmt.setString(2, account.getType());
            stmt.setInt(3, account.getBalance());
            stmt.setInt(4, account.getPostNumber());
            stmt.setInt(5, account.getUserId());
            stmt.setInt(6, account.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
