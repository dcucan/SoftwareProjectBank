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

    public ArrayList<String> getTypesString() {
        ArrayList<TransactionType> types = getAllTransactionTypesForUser();
        ArrayList<String> list = new ArrayList<>();

        for (TransactionType type : types) {
            list.add(type.getType());
        }

        return list;

    }

    public ArrayList<TransactionType> getAllTransactionTypesForUser() {
        ArrayList<TransactionType> list = new ArrayList<>();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        User user = Auth.get().getUser();
        List<Account> allAccounts = accountRepository.findAllForUser(user);
        List<Transaction> allTransactions;

        for (Account account : allAccounts) {

            allTransactions = transactionRepository.findAllForAccount(account);
            for (Transaction transaction : allTransactions) {
                if (!transactionRepository.isIncoming(transaction, account)) {
                    list.add(findByTransaction(transaction));
                }
            }
        }
        return list;
    }

    public HashMap getSpendsAlcohol() {
        User user = Auth.get().getUser();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        List<TransactionType> types = getAllTransactionTypesForUser();
        List<Account> allAccounts = accountRepository.findAllForUser(user);
        List<Transaction> allTransactions;
        HashMap<String, Integer> typesMap = new HashMap<>();
        alcohol = 0;
        food = 0;

        for (Account account : allAccounts) {

            allTransactions = transactionRepository.findAllForAccount(account);
            for (Transaction transaction : allTransactions) {
                for (TransactionType type : types) {
                    if (transaction.getId() == type.getTransactionId().getId()) {
                        if (type.getType().contains("Alcohol")) {
                            alcohol = alcohol + transaction.getAmount();
                        }
                        if (type.getType().contains("Food")) {
                            food = food + transaction.getAmount();
                        }
                    }
                }
            }
        }
        typesMap.put("Alcohol", alcohol);
        typesMap.put("Food", food);
        return typesMap;
    }

    public int getAlcohol() {
        ArrayList<String> list = getTypesString();
        alcohol = 0;

        for (String string : list) {
            if (string.contains("Alcohol")) {
                alcohol++;
            }
        }

        return alcohol;
    }

    public int getFood() {
        ArrayList<String> list = getTypesString();
        food = 0;

        for (String string : list) {
            if (string.contains("Food")) {
                food++;
            }
        }

        return food;
    }

}
