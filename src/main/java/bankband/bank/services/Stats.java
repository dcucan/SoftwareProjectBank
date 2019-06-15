package bankband.bank.services;

import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;

import java.util.*;
import java.util.stream.Collectors;

public class Stats {

    public Map<TransactionType, Integer> getSpendingsPerType() {
        Map<TransactionType, Integer> stats = new HashMap<>();

        //Get all accounts of a user
        List<Account> accounts = Auth.get().getUser().getAccounts();

        // Transform the list into stream for further manipulation
        List<Transaction> outgoing = accounts.stream()
                //transforms the list of accounts into a list of all transactions
                .flatMap(account -> account.getOutgoingTransactions().stream())
                // And finally collect the stream into a list of transactions
                .collect(Collectors.toList());

        // Loop through all of the outgoing transactions
        for (Transaction transaction : outgoing) {

            // Get already calculated sum of the given transactionType from the map or return 0 if there is no yet
            int sum = stats.getOrDefault(transaction.getType(), 0);
            // Increment the sum by the amount from the given transaction and put it back into the map
            stats.put(transaction.getType(), sum + transaction.getAmount());
        }

        return stats;
    }

    public Map<TransactionType, Integer> getNumberOfTransactionPerType() {
        Map<TransactionType, Integer> stats = new HashMap<>();

        //Get all accounts of a user
        List<Account> accounts = Auth.get().getUser().getAccounts();

        // Transform the list into stream for further manipulation
        List<Transaction> outgoing = accounts.stream()
                //transforms the list of accounts into a list of all transactions
                .flatMap(account -> account.getOutgoingTransactions().stream())
                // And finally collect the stream into a list of transactions
                .collect(Collectors.toList());
        Integer count = 1;

        Set<String> keywordSet = new HashSet<>();
        for (Transaction transaction : outgoing) {
            keywordSet.add(transaction.getType().getType());

        }

        for (Transaction transaction : outgoing) {
            if (keywordSet.contains(transaction.getType().getType())) {
                stats.put(transaction.getType(), count++);
            } else {
                stats.put(transaction.getType(), count);
            }

            System.out.println(count);

        }


        return stats;
    }
}
