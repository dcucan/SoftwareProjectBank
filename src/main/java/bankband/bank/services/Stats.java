package bankband.bank.services;

import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;
import bankband.bank.models.User;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.TransactionRepository;
import bankband.bank.repositories.TransactionTypeRepository;

import java.util.*;


public class Stats {

    int alcohol;
    int food;

    AccountRepository accountRepository = new AccountRepository();
    TransactionRepository transactionRepository = new TransactionRepository();
    TransactionTypeRepository transactionTypeRepository = new TransactionTypeRepository();

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
        User user = Auth.get().getUser();
        List<Account> allAccounts = accountRepository.findAllForUser(user);
        List<Transaction> allTransactions;

        for (Account account : allAccounts) {

            allTransactions = transactionRepository.findAllForAccount(account);
            for (Transaction transaction : allTransactions) {
                if (!transactionRepository.isIncoming(transaction, account)) {
                    list.add(transactionTypeRepository.findByTransaction(transaction));
                }
            }
        }
        return list;
    }

    public HashMap getSpendsAlcohol() {
        User user = Auth.get().getUser();
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
