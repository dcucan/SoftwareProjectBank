PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accounts;

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

