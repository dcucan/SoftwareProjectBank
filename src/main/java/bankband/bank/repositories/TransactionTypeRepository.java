package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;
import bankband.bank.models.User;
import bankband.bank.services.Auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionTypeRepository {


    private Connection conn = Database.getInstance().getConnection();

    private int alcohol;
    private int food;


    public Integer create(TransactionType transactionType) {
        String sql = "INSERT INTO transaction_type(type, transaction_id)" +
                "VALUES(?,?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, transactionType.getType());
            stmt.setInt(2, transactionType.getTransactionId().getId());
            stmt.execute();

            sql = "SELECT last_insert_rowid();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            transactionType.setId(rs.getInt(1));
            return transactionType.getId();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TransactionType findByTransaction(Transaction transaction) {
        String sql = "SELECT * FROM transaction_type WHERE transaction_id = ?";


        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getId());
            ResultSet rs = stmt.executeQuery();

            TransactionType transactionType = new TransactionType();
            transactionType.setId(rs.getInt(1));
            transactionType.setType(rs.getString(2));
            transactionType.setTransactionId(transaction);
            return transactionType;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



}
