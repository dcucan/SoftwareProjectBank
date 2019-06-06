package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Transaction;

import java.sql.*;

public class TransactionRepository {


    private Connection conn = Database.getInstance().getConnection();

    public Integer create(Transaction transaction){
        String sql = "INSERT INTO transactions (ammount, date_time, from_account_id, to_account_id ) "
                + "VALUES (?, ?, ?, ?);";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,transaction.getAmount());
            stmt.setDate(2,new Date(transaction.getDateTime().getTime()));
            stmt.setInt(3, transaction.getFromAccount().getId());
            stmt.setInt(4, transaction.getToAccount().getId());
            stmt.execute();

            sql = "SELECT last_insert_rowid();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            transaction.setId(rs.getInt(1));
            return transaction.getId();

        }  catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
