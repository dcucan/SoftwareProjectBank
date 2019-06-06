package bankband.bank.repositories;

import bankband.bank.Database;
import bankband.bank.models.Account;
import bankband.bank.models.Transaction;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {


    private Connection conn = Database.getInstance().getConnection();

    public Integer create(Transaction transaction) {
        String sql = "INSERT INTO transactions (ammount, date_time, from_account_id, to_account_id ) "
                + "VALUES (?, ?, ?, ?);";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getAmount());
            stmt.setDate(2, new Date(transaction.getDateTime().getTime()));
            stmt.setInt(3, transaction.getFromAccount().getId());
            stmt.setInt(4, transaction.getToAccount().getId());
            stmt.execute();

            sql = "SELECT last_insert_rowid();";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            transaction.setId(rs.getInt(1));
            return transaction.getId();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean isIncoming(Transaction transaction, Account account){
        if(account.getId()==transaction.getToAccount().getId()){
            return true;
        } else {
        return false;
        }
    }

    public List<Transaction> findAllForAccount(Account account) {
        ArrayList<Transaction> list = new ArrayList<>();
        AccountRepository repo = new AccountRepository();
        String sql = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ?";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, account.getId());
            stmt.setInt(2, account.getId());
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {

                Transaction transaction = new Transaction();
                transaction.setToAccount(repo.findById(rs.getInt("to_account_id")));
                transaction.setFromAccount(repo.findById(rs.getInt("from_account_id")));
                transaction.setDateTime(rs.getTime("date_time"));
                transaction.setAmount(rs.getInt("ammount"));
                transaction.setId(rs.getInt("id"));


                list.add(transaction);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;

    }
}
