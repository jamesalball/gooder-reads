INSERT INTO Books (id, title, author_id) VALUES (1, 'The Stand', 1);
INSERT INTO Books (id, title, author_id) VALUES (2, 'IT', 1);
INSERT INTO Books (id, title, author_id) VALUES (3, 'Salems Lot', 1);
INSERT INTO Books (id, title, author_id) VALUES (4, 'Carrie', 1);
INSERT INTO Books (id, title, author_id) VALUES (5, 'Skeleton Crew', 1);

INSERT INTO Users (id, email, display_name) VALUES (1, 'james.al.ball@gmail.com', 'James Ball');
INSERT INTO Users (id, email, display_name) VALUES (2, 'sadira.austin@gmail.com', 'Sadira Austin');

INSERT INTO Reviews (id, book_id, user_id, rating, review_text) VALUES (1, 1, 1, 5.0, 'This book is so good!');
INSERT INTO Reviews (id, book_id, user_id, rating, review_text) VALUES (2, 1, 2, 1.5, 'This book sucks ass!');