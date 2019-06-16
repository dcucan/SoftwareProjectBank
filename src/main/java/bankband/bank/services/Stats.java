package bankband.bank.services;


import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {

    public Map<TransactionType, Integer> getSpendingsPerType() {
        Map<TransactionType, Integer> stats = new HashMap<>();

        List<Account> accounts = Auth.get().getUser().getAccounts();

        List<Transaction> transactions = new ArrayList<>();

        for (Account account : accounts) {
            transactions.addAll(account.getOutgoingTransactions());
        }

        for (Transaction transaction : transactions) {
            int sum = stats.getOrDefault(transaction.getTransactionType(), 0);
            stats.put(transaction.getTransactionType(), sum + transaction.getAmount());
        }

        return stats;
    }

}
