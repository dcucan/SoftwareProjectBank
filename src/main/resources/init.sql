PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE accounts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    number INTEGER NOT NULL,
    type TEXT NOT NULL,
    balance INTEGER NOT NULL,
    postNumber INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ammount INTEGER NOT NULL,
    date_time INTEGER NOT NULL,
    from_account_id INTEGER NOT NULL,
    to_account_id INTEGER NOT NULL,
    FOREIGN KEY(from_account_id) REFERENCES accounts(id),
    FOREIGN KEY(to_account_id) REFERENCES accounts(id)


);

CREATE TABLE transaction_type (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT NOT NULL,
    transaction_id INTEGER NOT NULL,
    FOREIGN KEY(transaction_id) REFERENCES transactions(id)
);

CREATE TABLE cards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    number INTEGER NOT NULL,
    expiration INTEGER NOT NULL,
    ccv INTEGER NOT NULL,
    pin TEXT NOT NULL,
    image TEXT NOT NULL,
    limit INTEGER NOT NULL,
    account_id INTEGER NOT NULL,
    FOREIGN KEY(account_id) REFERENCES accounts(id)
);



