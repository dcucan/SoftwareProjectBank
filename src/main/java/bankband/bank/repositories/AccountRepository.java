package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Account;
import bankband.bank.models.User;
import bankband.bank.services.Auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

    private Connection conn = Database.getInstance().getConnection();


    public void findAllByUser(){

    }

    public Integer create(Account account){
        String sql = "INSERT INTO accounts (number, type, balance, postNumber, user_id)" +
                " VALUES (?, ?, ?, ?, ?);";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, account.getNumber());
            stmt.setString(2, account.getType());
            stmt.setInt(3,account.getBalance());
            stmt.setInt(4, account.getPostNumber());
            stmt.setInt(5, Auth.get().getUser().getId());
            stmt.execute();

            sql = "SELECT last_insert_rowid();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            account.setId(rs.getInt(1));
            return account.getId();

        } catch (SQLException e){
            e.printStackTrace();

        }


        return null;
    }
}
