package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;
import bankband.bank.models.User;
import bankband.bank.services.Auth;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionTypeRepository {

    private Connection conn = Database.getInstance().getConnection();

    public Integer create(TransactionType transactionType) {
        String sql = "INSERT INTO transaction_types (name) VALUES(?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, transactionType.getName());
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

    public TransactionType findBy(String column, Object value) {
        String sql = "SELECT * FROM transaction_types WHERE " + column + " = ?";


        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, value);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                TransactionType transactionType = new TransactionType();
                transactionType.setId(rs.getInt("id"));
                transactionType.setName(rs.getString("name"));
                return transactionType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TransactionType> findAll() {
        List<TransactionType> list = new ArrayList<>();

        String sql = "SELECT * FROM transaction_types";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TransactionType transactionType = new TransactionType();
                transactionType.setId(rs.getInt("id"));
                transactionType.setName(rs.getString("name"));
                list.add(transactionType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
