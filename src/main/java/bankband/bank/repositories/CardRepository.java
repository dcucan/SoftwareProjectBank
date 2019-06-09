package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRepository {

    private Connection conn = Database.getInstance().getConnection();


    public Integer create(Card card) {
        String sql = "INSERT INTO cards (number, expiration, ccv, pin, image, account_id)" +
                "VALUES (?,?,?,?,?,?)";


        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, card.getNumber());
            stmt.setDate(2, card.getExpirationDate());
            stmt.setInt(3, card.getCcv());
            stmt.setString(4,card.getPin());
            stmt.setString(5, card.getImage());
            stmt.setInt(6, card.getAccountId().getId());
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


}
