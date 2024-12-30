DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS film CASCADE;
DROP TABLE IF EXISTS actor CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS filmActor CASCADE;
DROP TABLE IF EXISTS filmGenre CASCADE;
DROP TABLE IF EXISTS rental CASCADE;

CREATE TABLE users (
    userId SERIAL PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(70) UNIQUE,
    -- password VARCHAR(50),
    password VARCHAR(255),
    role VARCHAR(10) CHECK (role IN ('user', 'admin'))
);

CREATE TABLE film (
    filmId SERIAL PRIMARY KEY,
    title VARCHAR(30),
    synopsis VARCHAR(500),
    -- ganti dari image jadi poster
    poster BYTEA, 
    stock INT CHECK (stock >= 0),
    price NUMERIC,
	valid BOOLEAN
);

CREATE TABLE actor (
    actorId SERIAL PRIMARY KEY,
    name VARCHAR(50),
	valid BOOLEAN
);

CREATE TABLE genre (
    genreId SERIAL PRIMARY KEY,
    name VARCHAR(20),
	valid BOOLEAN
);

CREATE TABLE filmActor (
    filmId INT NOT NULL REFERENCES film(filmId),
    actorId INT NOT NULL REFERENCES actor(actorId),
    PRIMARY KEY (filmId, actorId)
);

CREATE TABLE filmGenre (
    filmId INT NOT NULL REFERENCES film(filmId),
    genreId INT NOT NULL REFERENCES genre(genreId),
    PRIMARY KEY (filmId, genreId)
);

CREATE TABLE rental (
    rentalId SERIAL PRIMARY KEY,
    rentalDate DATE NOT NULL,
    dueDate DATE NOT NULL,
    returnDate DATE,
    filmId INT NOT NULL REFERENCES film(filmId),
    userId INT NOT NULL REFERENCES users(userId),
    metodePembayaran VARCHAR(50) NOT NULL,
	noPembayaran VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO users (name, email, password, role) VALUES
('Seba Stian', 'sebastian@gmail.com', '$2a$10$eqkeu1M9IGSqvbU1M0LRsek35ouMeh146sSdm1SK8B1Fvdmgne3bu', 'admin'),
('angie gie','angie@gmail.com', '$2a$10$ZyeJIHau/QoaGKx3YzbEpe5N3m/u0FB4rCCIIeFoJgTD8MHjL4LPO', 'user'),
('rozan ramzy', 'rozan@gmail.com', '$2a$10$eeuCL6jWYRqV4SQZvrk9EeEC1sMYVmeeyt5uJH1yYXwz/BW96UhnK', 'user'),
('hayya vv', 'hayya@gmail.com', '$2a$10$1ZQU/PTPhtcNhCGLW2g2Ze3ilMI8pE1pdH2CNI3iZOk35zfy7VEL.', 'user'),
('iger tanks', 'iger@gmail.com', '$2a$10$G0h8VT9Y.GrxHhAkFjx/Ce2/0BuniDcFKQ9sDNzLtF1UokuAtmuTi', 'user'),
('john doe', 'john@gmail.com', '$2a$10$8ZSoXXloIJ78bRllM2ckOuzpbv1IfjOH4T0a9u43ks.4dN7qQGhLe', 'user');

INSERT INTO film (title, synopsis, poster, stock, price, valid)
VALUES
    ('The Adventure Begins', 'A young adventurer sets off on a perilous journey to find the lost city of gold, facing dangers and discovering secrets along the way.', NULL, 50, 12.99, 'true'),
    ('Mystery at Midnight', 'A detective is called to solve a series of mysterious disappearances that seem to be connected to a supernatural entity.', NULL, 30, 15.49, 'true'),
    ('Love in the City', 'Two people from different worlds find themselves falling in love while navigating the challenges of living in a bustling city.', NULL, 100, 9.99, 'true'),
    ('The Last Hero', 'In a dystopian future, a former soldier must become the last hope for humanity after an AI uprising.', NULL, 75, 20.00, 'true'),
    ('Space Odyssey', 'A crew of astronauts embarks on a mission to explore a distant galaxy, but things take a dangerous turn when they encounter an alien race.', NULL, 40, 18.99, 'true'),
    ('The Haunted House', 'A group of friends decides to spend the weekend in a supposedly haunted mansion, only to discover the horrifying truth.', NULL, 60, 10.49, 'true');
	
INSERT INTO actor (name,valid)
VALUES
    ('John Doe','true'),
    ('Jane Smith','true'),
    ('Mike Johnson','true'),
    ('Emily Stone','true'),
    ('Chris Evans','true'),
    ('Scarlett Johansson','true'),
    ('Tom Hanks','true'),
    ('Sandra Bullock','true'),
    ('Will Smith','true'),
    ('Angelina Jolie','true');

INSERT INTO genre (name,valid)
VALUES
    ('Action','true'),
    ('Comedy','true'),
    ('Drama','true'),
    ('Horror','true'),
    ('Romance','true'),
    ('Thriller','true'),
    ('Sci-Fi','true'),
    ('Fantasy','true'),
    ('Mystery','true'),
    ('Adventure','true');

INSERT INTO filmActor (filmId, actorId)
VALUES
    (1, 1),  -- John Doe in 'The Adventure Begins'
    (1, 2),  -- Jane Smith in 'The Adventure Begins'
    (2, 3),  -- Mike Johnson in 'Mystery at Midnight'
    (2, 4),  -- Emily Stone in 'Mystery at Midnight'
    (3, 5),  -- Chris Evans in 'Love in the City'
    (3, 6),  -- Scarlett Johansson in 'Love in the City'
    (4, 7),  -- Tom Hanks in 'The Last Hero'
    (4, 8),  -- Sandra Bullock in 'The Last Hero'
    (5, 9),  -- Will Smith in 'Space Odyssey'
    (5, 10); -- Angelina Jolie in 'Space Odyssey'
    


INSERT INTO filmGenre (filmId, genreId)
VALUES
    (1, 10), -- 'The Adventure Begins' is Adventure
    (1, 7),  -- 'The Adventure Begins' is also Sci-Fi
    (2, 9),  -- 'Mystery at Midnight' is Mystery
    (2, 3),  -- 'Mystery at Midnight' is also Drama
    (3, 5),  -- 'Love in the City' is Romance
    (3, 2),  -- 'Love in the City' is also Comedy
    (4, 1),  -- 'The Last Hero' is Action
    (4, 7),  -- 'The Last Hero' is also Sci-Fi
    (5, 7),  -- 'Space Odyssey' is Sci-Fi
    (5, 10); -- 'Space Odyssey' is also Adventure
    

INSERT INTO rental (rentalDate, dueDate, returnDate, filmId, userId, metodePembayaran,noPembayaran)
VALUES
    ('2024-12-01', '2024-12-15', '2024-12-12', 1, 1, 'BCA','123400022341'),  -- User 1 rented film 1
    ('2024-12-02', '2024-12-16', '2024-12-14', 2, 2, 'BCA','123400022342'),         -- User 2 rented film 2
    ('2024-12-03', '2024-12-17', '2024-12-16', 3, 3, 'Mandiri','123400022331'),    -- User 3 rented film 3
    ('2024-12-04', '2024-12-18', NULL, 4, 4, 'BCA','123400022362'),          -- User 4 rented film 4 (not returned yet)
    ('2024-12-05', '2024-12-19', '2024-12-18', 5, 5, 'Mandiri','123400022345'),        -- User 5 rented film 5
	('2024-12-01', '2024-12-15', '2024-12-12', 1, 5, 'BRI','123400022347'),  -- User 1 rented film 1
    ('2024-12-02', '2024-12-16', '2024-12-14', 2, 4, 'BRI','123400022348'),         -- User 2 rented film 2
    ('2024-12-03', '2024-12-17', '2024-12-16', 3, 3, 'BCA','123400022349'),    -- User 3 rented film 3
    ('2024-12-04', '2024-12-18', NULL, 4, 2, 'Mandiri','123400022350'),          -- User 4 rented film 4 (not returned yet)
    ('2024-12-05', '2024-12-19', '2024-12-18', 5, 1, 'BRI','123400022361');













