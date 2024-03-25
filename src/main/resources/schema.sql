CREATE TABLE IF NOT EXISTS users(
    user_id identity PRIMARY KEY,
    admin boolean NOT NULL,
    online boolean NOT NULL,
    username varchar(30) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS deck(
    deck_id identity PRIMARY KEY,
    name varchar(30) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS card(
    card_id identity PRIMARY KEY,
    front varchar(100) NOT NULL,
    back varchar(100) NOT NULL,
    deck_id int,
    user_id int,
    FOREIGN KEY (deck_id) REFERENCES deck(deck_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

