INSERT INTO users (admin, online, username, email, password) 
SELECT true, false, 'marlon', 'marlon@gmail.com', 1234
WHERE NOT EXISTS (SELECT username FROM users WHERE username = 'marlon');


INSERT INTO deck ( name, user_id)
SELECT 'Englisch', 1
WHERE NOT EXISTS (SELECT name FROM deck WHERE name = 'Englisch');


INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Wie gehts?', 'How are you?', 1, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'How are you?');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Schön dich zu sehen', 'Nice to see you', 1, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'Nice to see you');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Jemanden treffen', 'To meet somebody/someone', 1, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'To meet somebody/someone');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Du siehst toll aus', 'You look amazing', 1, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'You look amazing');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Guten Morgen', 'Good morning', 1, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'Good morning');





INSERT INTO deck(name, user_id)
SELECT 'Spanisch', 1
WHERE NOT EXISTS (SELECT name FROM deck WHERE name = 'Spanisch');


INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Wie gehts?', 'Que tal?', 2, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'Que tal?');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Der Fußball', 'El futbol', 2, 1
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'El futbol');



INSERT INTO users (admin, online, username, email, password) 
SELECT false, false, 'max', 'mustermann@gmail.com', 1234
WHERE NOT EXISTS (SELECT username FROM users WHERE username = 'max');


INSERT INTO deck (name, user_id)
SELECT 'Geschichte', 2
WHERE NOT EXISTS (SELECT name FROM deck WHERE name = 'Geschichte');


INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Was geschah 1776?', 'Die amerikanische Unabhängigkeitserklärung', 3, 2
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'Die amerikanische Unabhängigkeitserklärung');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Wann war die französische Revolution', 'Die französische Revolution spielte 
sich zwischen 1789 und 1799 ab', 3, 2
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'Die französische Revolution spielte 
sich zwischen 1789 und 1799 ab');

INSERT INTO card (front, back, deck_id, user_id)
SELECT 'Wer erfand die Buchdruck Maschine', 'Johannes Gutenberg', 3, 2
WHERE NOT EXISTS (SELECT back FROM card WHERE back = 'Johannes Gutenberg');




