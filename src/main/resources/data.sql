INSERT INTO Books (title, author_id, synopsis) VALUES ('The Stand', 1, 'It''s a post-apocalypse!');
INSERT INTO Books (title, author_id, synopsis) VALUES ('IT', 1, 'Scary clowns!');
INSERT INTO Books (title, author_id, synopsis) VALUES ('Salem''s Lot', 1, 'Resurrected pets!');
INSERT INTO Books (title, author_id, synopsis) VALUES ('Carrie', 1, 'Pig blood at prom!');
INSERT INTO Books (title, author_id, synopsis) VALUES ('Skeleton Crew', 1, 'Stories to keep you up at night!');

INSERT INTO Users (id, email, display_name) VALUES (1, 'james.al.ball@gmail.com', 'James Ball');
INSERT INTO Users (id, email, display_name) VALUES (2, 'sadira.austin@gmail.com', 'Sadira Austin');

INSERT INTO Reviews (id, book_id, user_id, rating, review_text) VALUES (1, 1, 1, 5.0, 'This book is so good!');
INSERT INTO Reviews (id, book_id, user_id, rating, review_text) VALUES (2, 1, 2, 1.5, 'This book sucks ass!');