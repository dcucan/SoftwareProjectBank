PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS transaction_types;
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
    transaction_type_id INTEGER NOT NULL,

    FOREIGN KEY(from_account_id) REFERENCES accounts(id),
    FOREIGN KEY(to_account_id) REFERENCES accounts(id),
    FOREIGN KEY(transaction_type_id) REFERENCES transaction_types(id)
);

CREATE TABLE transaction_types (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE cards (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    number INTEGER NOT NULL,
    expiration INTEGER NOT NULL,
    ccv INTEGER NOT NULL,
    pin TEXT NOT NULL,
    image TEXT NOT NULL,
    card_limit INTEGER NOT NULL,
    account_id INTEGER NOT NULL,
    FOREIGN KEY(account_id) REFERENCES accounts(id)
);

INSERT INTO transaction_types (name)
    VALUES
        ("Food"),
        ("Alcohol"),
        ("Gas"),
        ("Clothes"),
        ("Entertainment"),
        ("Accommodation"),
        ("Electronics"),
        ("Education"),
        ("Family");

