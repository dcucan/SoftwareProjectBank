package bankband.bank.models;

public class TransactionType {

    private int id;

    private String type;

    private Transaction transactionId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transaction getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Transaction transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TransactionType)) return false;

        return ((TransactionType) o).getId() == this.getId();
    }

}
