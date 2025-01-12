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
    email VARCHAR(70),
    password VARCHAR(255),
    role VARCHAR(10) CHECK (role IN ('user', 'admin'))
);

CREATE TABLE film (
    filmId SERIAL PRIMARY KEY,
    title VARCHAR(30),
    synopsis VARCHAR(500),
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
    returnDate DATE,
    filmId INT NOT NULL REFERENCES film(filmId),
    userId INT NOT NULL REFERENCES users(userId),
    metodePembayaran VARCHAR(50) NOT NULL
);

INSERT INTO users (name, email, password, role) VALUES
('Seba Stian', 'sebastian@gmail.com', '$2a$10$eqkeu1M9IGSqvbU1M0LRsek35ouMeh146sSdm1SK8B1Fvdmgne3bu', 'admin'),
('Angie Gie','angie@gmail.com', '$2a$10$ZyeJIHau/QoaGKx3YzbEpe5N3m/u0FB4rCCIIeFoJgTD8MHjL4LPO', 'user'),
('Rozan Ramzy', 'rozan@gmail.com', '$2a$10$eeuCL6jWYRqV4SQZvrk9EeEC1sMYVmeeyt5uJH1yYXwz/BW96UhnK', 'user'),
('Hayya Vv', 'hayya@gmail.com', '$2a$10$1ZQU/PTPhtcNhCGLW2g2Ze3ilMI8pE1pdH2CNI3iZOk35zfy7VEL.', 'user'),
('Iger Tanks', 'iger@gmail.com', '$2a$10$G0h8VT9Y.GrxHhAkFjx/Ce2/0BuniDcFKQ9sDNzLtF1UokuAtmuTi', 'user'),
('John Doe', 'john@gmail.com', '$2a$10$8ZSoXXloIJ78bRllM2ckOuzpbv1IfjOH4T0a9u43ks.4dN7qQGhLe', 'user');


INSERT INTO film (title, synopsis, poster, stock, price, valid)
VALUES
    ('Avengers: Endgame', 'The Avengers assemble once again to undo the destruction caused by Thanos and save the universe.', NULL, 12, 60000, 'true'),
    ('The Dark Knight', 'Batman faces off against the Joker, a criminal mastermind who wants to create chaos in Gotham City.', NULL, 18, 55000, 'true'),
    ('Inception', 'A thief who enters the dreams of others must perform an impossible task: planting an idea into someones mind.', NULL, 5, 65000, 'true'),
    ('The Lion King', 'A young lion prince overcomes adversity to reclaim his place as the king of the Pride Lands.', NULL, 20, 60000, 'true'),
    ('Jurassic Park', 'A theme park with genetically engineered dinosaurs goes horribly wrong when the creatures break free.', NULL, 8, 55000, 'true'),
    ('Titanic', 'A love story set against the backdrop of the ill-fated maiden voyage of the RMS Titanic.', NULL, 3, 65000, 'true'),
    ('The Matrix', 'A computer hacker learns that reality is a simulation and must decide whether to fight for freedom or stay in the illusion.', NULL, 10, 60000, 'true'),
    ('Avatar', 'A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.', NULL, 15, 70000, 'true'),
    ('Frozen', 'Two sisters in a kingdom where winter lasts forever must find a way to break the curse and restore summer.', NULL, 4, 65000, 'true'),
    ('Star Wars: The Force Awakens', 'A scavenger, a former stormtrooper, and a rogue pilot join the Resistance to fight against the First Order and search for the missing Luke Skywalker.', NULL, 7, 70000, 'true'),
    ('Spider-Man: No Way Home', 'Spider-Man faces the multiverse as villains from alternate dimensions cross into his world, and he must team up with other versions of himself.', NULL, 13, 65000, 'true'),
    ('Black Panther', 'TChalla, the king of Wakanda, must step up to defend his people and the throne against a formidable new enemy.', NULL, 6, 60000, 'true'),
    ('The Avengers', 'Earths mightiest heroes must come together to defeat Loki and his alien army that threatens to destroy Earth.', NULL, 19, 60000, 'true'),
    ('Guardians of the Galaxy', 'A group of intergalactic misfits must band together to stop a fanatical warrior from taking control of the universe.', NULL, 14, 55000, 'true'),
    ('Wonder Woman', 'A warrior princess leaves her sheltered island to fight in the world of man and stop a global conflict.', NULL, 9, 60000, 'true'),
    ('Shrek', 'A reclusive ogre teams up with a talkative donkey to rescue a princess and restore peace to the kingdom.', NULL, 16, 55000, 'true'),
    ('Pirates of the Caribbean: The Curse of the Black Pearl', 'Captain Jack Sparrow teams up with a blacksmith to retrieve his stolen ship and defeat an undead pirate crew.', NULL, 2, 60000, 'true'),
    ('Deadpool', 'A former special forces operative turned mercenary becomes the antihero Deadpool after undergoing an experiment that leaves him with accelerated healing powers.', NULL, 11, 65000, 'true'),
    ('Mission: Impossible - Fallout', 'Ethan Hunt and his team must go on a mission to recover stolen plutonium and prevent a global disaster.', NULL, 17, 60000, 'true'),
    ('The Hobbit: An Unexpected Journey', 'A hobbit, along with a group of dwarves, embarks on a dangerous journey to reclaim a stolen treasure from a dragon.', NULL, 1, 55000, 'true'),
    ('Fast & Furious 7', 'The team faces their most dangerous adversary yet, and must stop a criminal mastermind while dealing with their own personal lives.', NULL, 20, 70000, 'true'),
    ('Interstellar', 'A group of astronauts travel through a wormhole in search of a new habitable planet to save humanity from extinction.', NULL, 12, 65000, 'true'),
    ('The Hunger Games', 'In a dystopian future, teenagers are selected to participate in a deadly televised survival game as a form of punishment for rebellion.', NULL, 5, 60000, 'true'),
    ('Avatar: The Way of Water', 'Jake and Neytiri set out to explore the oceans of Pandora as they face new challenges and new enemies.', NULL, 18, 65000, 'true'),
    ('Iron Man', 'A billionaire industrialist is kidnapped and forced to build a weapon, but instead he creates an armored suit to escape and protect the world.', NULL, 7, 60000, 'true'),
    ('Thor: Ragnarok', 'Thor teams up with the Hulk, Valkyrie, and his brother Loki to stop the villain Hela from destroying Asgard and ruling the universe.', NULL, 14, 65000, 'true'),
    ('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', NULL, 9, 70000, 'true'),
    ('The Shawshank Redemption', 'Two imprisoned men form a deep bond over the years, finding solace and eventual redemption through acts of common decency.', NULL, 3, 60000, 'true'),
    ('The Wolf of Wall Street', 'The rise and fall of Jordan Belfort, a stockbroker who defrauds investors and lives a life of excess and corruption.', NULL, 16, 65000, 'true'),
    ('Forrest Gump', 'A simple man with a low IQ achieves great things and inadvertently influences major historical events, all while telling his life story.', NULL, 13, 60000, 'true');
	
INSERT INTO actor (name,valid)
VALUES
    ('Robert Downey Jr.', 'true'),
    ('Chris Hemsworth', 'true'),
    ('Mark Ruffalo', 'true'),
    ('Scarlett Johansson', 'true'),
    ('Chris Evans', 'true'),
    ('Tom Hiddleston', 'true'),
    ('Samuel L. Jackson', 'true'),
    ('Tom Holland', 'true'),
    ('Jeremy Renner', 'true'),
    ('Paul Rudd', 'true'),
    ('Zoe Saldana', 'true'),
    ('Dave Bautista', 'true'),
    ('Vin Diesel', 'true'),
    ('Bradley Cooper', 'true'),
    ('Elizabeth Olsen', 'true'),
    ('Daniel Craig', 'true'),
    ('Joaquin Phoenix', 'true'),
    ('Nicole Kidman', 'true'),
    ('Emma Stone', 'true'),
    ('Ryan Gosling', 'true'),
    ('Dwayne Johnson', 'true'),
    ('Gal Gadot', 'true'),
    ('Mark Wahlberg', 'true'),
    ('Meryl Streep', 'true'),
    ('Angelina Jolie', 'true'),
    ('Matthew McConaughey', 'true'),
    ('Anne Hathaway', 'true'),
    ('Tom Cruise', 'true'),
    ('Nicole Kidman', 'true'),
    ('Will Smith', 'true'),
    ('Jada Pinkett Smith', 'true'),
    ('Brad Pitt', 'true'),
    ('Leonardo DiCaprio', 'true'),
    ('Helen Mirren', 'true'),
    ('Keanu Reeves', 'true'),
    ('Hugh Jackman', 'true'),
    ('Will Ferrell', 'true'),
    ('Harrison Ford', 'true'),
    ('Johnny Depp', 'true'),
    ('Ryan Reynolds', 'true'),
    ('Chris Pratt', 'true'),
    ('Javier Bardem', 'true'),
    ('Sean Connery', 'true'),
    ('Samuel L. Jackson', 'true'),
    ('Hugh Grant', 'true'),
    ('Chris Pine', 'true'),
    ('Idris Elba', 'true'),
    ('John C. Reilly', 'true'),
    ('Maggie Smith', 'true'),
    ('Emily Blunt', 'true');

INSERT INTO genre (name,valid)
VALUES
    ('Action', 'true'),
    ('Comedy', 'true'),
    ('Drama', 'true'),
    ('Horror', 'true'),
    ('Sci-Fi', 'true'),
    ('Romance', 'true'),
    ('Thriller', 'true'),
    ('Fantasy', 'true'),
    ('Mystery', 'true'),
    ('Adventure', 'true'),
    ('Animation', 'true'),
    ('Documentary', 'true');

INSERT INTO filmActor (filmId, actorId)
VALUES
    (1, 1),  -- Robert Downey Jr. in 'Avengers: Endgame'
    (1, 2),  -- Chris Hemsworth in 'Avengers: Endgame'
    (1, 3),  -- Mark Ruffalo in 'Avengers: Endgame'
    (1, 4),  -- Scarlett Johansson in 'Avengers: Endgame'
    (1, 5),  -- Chris Evans in 'Avengers: Endgame'
    (2, 6),  -- Tom Hiddleston in 'The Dark Knight'
    (2, 7),  -- Samuel L. Jackson in 'The Dark Knight'
    (3, 8),  -- Tom Holland in 'Inception'
    (3, 9),  -- Jeremy Renner in 'Inception'
    (3, 10), -- Paul Rudd in 'Inception'
    (4, 11), -- Zoe Saldana in 'The Lion King'
    (4, 12), -- Dave Bautista in 'The Lion King'
    (4, 13), -- Vin Diesel in 'The Lion King'
    (5, 14), -- Bradley Cooper in 'Jurassic Park'
    (5, 15), -- Elizabeth Olsen in 'Jurassic Park'
    (6, 16), -- Daniel Craig in 'Titanic'
    (6, 17), -- Joaquin Phoenix in 'Titanic'
    (7, 18), -- Nicole Kidman in 'The Matrix'
    (7, 19), -- Emma Stone in 'The Matrix'
    (8, 20), -- Ryan Gosling in 'Avatar'
    (8, 21), -- Dwayne Johnson in 'Avatar'
    (9, 22), -- Gal Gadot in 'Frozen'
    (9, 23), -- Mark Wahlberg in 'Frozen'
    (10, 24), -- Meryl Streep in 'Star Wars: The Force Awakens'
    (10, 25), -- Angelina Jolie in 'Star Wars: The Force Awakens'
    (11, 26), -- Matthew McConaughey in 'Spider-Man: No Way Home'
    (11, 27), -- Anne Hathaway in 'Spider-Man: No Way Home'
    (12, 28), -- Tom Cruise in 'Black Panther'
    (12, 29), -- Nicole Kidman in 'Black Panther'
    (13, 30), -- Will Smith in 'The Avengers'
    (14, 1),  -- Robert Downey Jr. in 'Guardians of the Galaxy'
    (14, 2),  -- Chris Hemsworth in 'Guardians of the Galaxy'
    (15, 3),  -- Mark Ruffalo in 'Wonder Woman'
    (15, 4),  -- Scarlett Johansson in 'Wonder Woman'
    (16, 5),  -- Chris Evans in 'Shrek'
    (17, 6),  -- Tom Hiddleston in 'Pirates of the Caribbean: The Curse of the Black Pearl'
    (18, 7),  -- Samuel L. Jackson in 'Deadpool'
    (19, 8),  -- Tom Holland in 'Mission: Impossible - Fallout'
    (20, 9),  -- Jeremy Renner in 'The Hobbit: An Unexpected Journey'
    (21, 10), -- Paul Rudd in 'Fast & Furious 7'
    (22, 11), -- Zoe Saldana in 'Interstellar'
    (22, 12), -- Dave Bautista in 'Interstellar'
    (23, 13), -- Vin Diesel in 'The Hunger Games'
    (24, 14), -- Bradley Cooper in 'Avatar: The Way of Water'
    (25, 15), -- Elizabeth Olsen in 'Iron Man'
    (26, 16), -- Daniel Craig in 'Thor: Ragnarok'
    (27, 17), -- Joaquin Phoenix in 'The Godfather'
    (28, 18), -- Nicole Kidman in 'The Shawshank Redemption'
    (29, 19), -- Emma Stone in 'The Wolf of Wall Street'
    (30, 20), -- Ryan Gosling in 'Forrest Gump';
    
INSERT INTO filmGenre (filmId, genreId)
VALUES
    (1, 1),  -- 'Avengers: Endgame' is Action
    (1, 7),  -- 'Avengers: Endgame' is Sci-Fi
    (1, 10), -- 'Avengers: Endgame' is Adventure
    (2, 1),  -- 'The Dark Knight' is Action
    (2, 3),  -- 'The Dark Knight' is Drama
    (2, 6),  -- 'The Dark Knight' is Thriller
    (3, 6),  -- 'Inception' is Thriller
    (3, 7),  -- 'Inception' is Sci-Fi
    (3, 3),  -- 'Inception' is Drama
    (4, 5),  -- 'The Lion King' is Animation
    (4, 10), -- 'The Lion King' is Adventure
    (4, 1),  -- 'The Lion King' is Action
    (5, 7),  -- 'Jurassic Park' is Sci-Fi
    (5, 10), -- 'Jurassic Park' is Adventure
    (5, 1),  -- 'Jurassic Park' is Action
    (6, 5),  -- 'Titanic' is Romance
    (6, 3),  -- 'Titanic' is Drama
    (6, 10), -- 'Titanic' is Adventure
    (7, 7),  -- 'The Matrix' is Sci-Fi
    (7, 1),  -- 'The Matrix' is Action
    (7, 6),  -- 'The Matrix' is Thriller
    (8, 7),  -- 'Avatar' is Sci-Fi
    (8, 5),  -- 'Avatar' is Romance
    (8, 10), -- 'Avatar' is Adventure
    (9, 5),  -- 'Frozen' is Animation
    (9, 10), -- 'Frozen' is Adventure
    (9, 1),  -- 'Frozen' is Action
    (10, 7), -- 'Star Wars: The Force Awakens' is Sci-Fi
    (10, 10), -- 'Star Wars: The Force Awakens' is Adventure
    (10, 1), -- 'Star Wars: The Force Awakens' is Action
    (11, 1), -- 'Spider-Man: No Way Home' is Action
    (11, 7), -- 'Spider-Man: No Way Home' is Sci-Fi
    (11, 10), -- 'Spider-Man: No Way Home' is Adventure
    (12, 5), -- 'Black Panther' is Action
    (12, 7), -- 'Black Panther' is Sci-Fi
    (12, 10), -- 'Black Panther' is Adventure
    (13, 1), -- 'The Avengers' is Action
    (13, 7), -- 'The Avengers' is Sci-Fi
    (13, 10), -- 'The Avengers' is Adventure
    (14, 1), -- 'Guardians of the Galaxy' is Action
    (14, 7), -- 'Guardians of the Galaxy' is Sci-Fi
    (14, 10), -- 'Guardians of the Galaxy' is Adventure
    (15, 5), -- 'Wonder Woman' is Action
    (15, 7), -- 'Wonder Woman' is Sci-Fi
    (15, 10), -- 'Wonder Woman' is Adventure
    (16, 5), -- 'Shrek' is Animation
    (16, 10), -- 'Shrek' is Adventure
    (16, 2), -- 'Shrek' is Comedy
    (17, 1), -- 'Pirates of the Caribbean: The Curse of the Black Pearl' is Action
    (17, 10), -- 'Pirates of the Caribbean: The Curse of the Black Pearl' is Adventure
    (17, 4), -- 'Pirates of the Caribbean: The Curse of the Black Pearl' is Fantasy
    (18, 1), -- 'Deadpool' is Action
    (18, 2), -- 'Deadpool' is Comedy
    (18, 7), -- 'Deadpool' is Sci-Fi
    (19, 1), -- 'Mission: Impossible - Fallout' is Action
    (19, 6), -- 'Mission: Impossible - Fallout' is Thriller
    (19, 10), -- 'Mission: Impossible - Fallout' is Adventure
    (20, 10), -- 'The Hobbit: An Unexpected Journey' is Adventure
    (20, 7), -- 'The Hobbit: An Unexpected Journey' is Sci-Fi
    (20, 1), -- 'The Hobbit: An Unexpected Journey' is Action
    (21, 1), -- 'Fast & Furious 7' is Action
    (21, 7), -- 'Fast & Furious 7' is Sci-Fi
    (21, 10), -- 'Fast & Furious 7' is Adventure
    (22, 7), -- 'Interstellar' is Sci-Fi
    (22, 10), -- 'Interstellar' is Adventure
    (22, 5), -- 'Interstellar' is Drama
    (23, 3), -- 'The Hunger Games' is Drama
    (23, 10), -- 'The Hunger Games' is Adventure
    (23, 7), -- 'The Hunger Games' is Sci-Fi
    (24, 7), -- 'Avatar: The Way of Water' is Sci-Fi
    (24, 10), -- 'Avatar: The Way of Water' is Adventure
    (24, 1), -- 'Avatar: The Way of Water' is Action
    (25, 7), -- 'Iron Man' is Sci-Fi
    (25, 1), -- 'Iron Man' is Action
    (25, 10), -- 'Iron Man' is Adventure
    (26, 7), -- 'Thor: Ragnarok' is Sci-Fi
    (26, 1), -- 'Thor: Ragnarok' is Action
    (26, 10), -- 'Thor: Ragnarok' is Adventure
    (27, 7), -- 'The Godfather' is Drama
    (27, 10), -- 'The Godfather' is Crime
    (27, 1), -- 'The Godfather' is Action
    (28, 3), -- 'The Shawshank Redemption' is Drama
    (28, 5), -- 'The Shawshank Redemption' is Crime
    (28, 10), -- 'The Shawshank Redemption' is Thriller
    (29, 1), -- 'The Wolf of Wall Street' is Action
    (29, 7), -- 'The Wolf of Wall Street' is Sci-Fi
    (29, 10), -- 'The Wolf of Wall Street' is Thriller
    (30, 10); -- 'The Pursuit of Happyness' is Drama
    

INSERT INTO rental (rentalDate, returnDate, filmId, userId, metodePembayaran)
VALUES
    ('2024-01-01', '2024-01-05', 3, 2, 'Mandiri'),
    ('2024-01-02', '2024-01-06', 5, 3, 'BCA'),
    ('2024-01-03', '2024-01-07', 4, 4, 'BRI'),
    ('2024-01-04', '2024-01-08', 2, 5, 'Mandiri'),
    ('2024-01-05', '2024-01-09', 1, 6, 'BCA'),
    ('2024-01-06', '2024-01-10', 6, 2, 'BRI'),
    ('2024-01-07', '2024-01-11', 7, 3, 'Mandiri'),

    ('2024-02-01', '2024-02-05', 11, 2, 'BRI'),
    ('2024-02-02', '2024-02-06', 12, 3, 'Mandiri'),
    ('2024-02-03', '2024-02-07', 13, 4, 'BCA'),
    ('2024-02-04', '2024-02-08', 14, 5, 'Mandiri'),
    ('2024-02-05', '2024-02-09', 15, 6, 'BCA'),
    ('2024-02-06', '2024-02-10', 16, 2, 'BRI'),
    ('2024-02-07', '2024-02-11', 17, 3, 'Mandiri'),
    ('2024-02-08', '2024-02-12', 18, 4, 'BCA'),
    ('2024-02-09', '2024-02-13', 19, 5, 'BRI'),
    ('2024-02-10', '2024-02-14', 20, 6, 'BCA'),

    ('2024-03-01', '2024-03-05', 21, 2, 'Mandiri'),
    ('2024-03-02', '2024-03-06', 22, 3, 'BCA'),
    ('2024-03-09', '2024-03-13', 29, 5, 'BRI'),
    ('2024-03-10', '2024-03-14', 30, 6, 'BCA'),

    ('2024-04-01', '2024-04-05', 1, 2, 'BRI'),
    ('2024-04-02', '2024-04-06', 2, 3, 'Mandiri'),
    ('2024-04-03', '2024-04-07', 3, 4, 'BCA'),
    ('2024-04-04', '2024-04-08', 4, 5, 'Mandiri'),
    ('2024-04-05', '2024-04-09', 5, 6, 'BCA'),
    ('2024-04-06', '2024-04-10', 6, 2, 'BRI'),
    ('2024-04-07', '2024-04-11', 7, 3, 'Mandiri'),
    ('2024-04-08', '2024-04-12', 8, 4, 'BCA'),
    ('2024-04-09', '2024-04-13', 9, 5, 'BRI'),
    ('2024-04-10', '2024-04-14', 10, 6, 'BCA'),

    ('2024-05-01', '2024-05-05', 11, 2, 'Mandiri'),
    ('2024-05-02', '2024-05-06', 12, 3, 'BCA'),
    ('2024-05-03', '2024-05-07', 13, 4, 'Mandiri'),
    ('2024-05-06', '2024-05-10', 16, 2, 'BRI'),
    ('2024-05-07', '2024-05-11', 17, 3, 'Mandiri'),
    ('2024-05-08', '2024-05-12', 18, 4, 'BCA'),
    ('2024-05-09', '2024-05-13', 19, 5, 'BRI'),
    ('2024-05-10', '2024-05-14', 20, 6, 'BCA'),

    ('2024-06-01', '2024-06-05', 21, 2, 'Mandiri'),
    ('2024-06-02', '2024-06-06', 22, 3, 'BCA'),

    ('2024-07-01', '2024-07-05', 1, 2, 'BRI'),
    ('2024-07-02', '2024-07-06', 2, 3, 'Mandiri'),
    ('2024-07-03', '2024-07-07', 3, 4, 'BCA'),
    ('2024-07-04', '2024-07-08', 4, 5, 'Mandiri'),
    ('2024-07-05', '2024-07-09', 5, 6, 'BCA'),
    ('2024-07-06', '2024-07-10', 6, 2, 'BRI'),
    ('2024-07-07', '2024-07-11', 7, 3, 'Mandiri'),
    ('2024-07-10', '2024-07-14', 10, 6, 'BCA'),

    ('2024-08-01', '2024-08-05', 11, 2, 'Mandiri'),
    ('2024-08-02', '2024-08-06', 12, 3, 'BCA'),
    ('2024-08-03', '2024-08-07', 13, 4, 'Mandiri'),
    ('2024-08-04', '2024-08-08', 14, 5, 'BCA'),
    ('2024-08-05', '2024-08-09', 15, 6, 'Mandiri'),
    ('2024-08-06', '2024-08-10', 16, 2, 'BRI'),
    ('2024-08-07', '2024-08-11', 17, 3, 'Mandiri'),
    ('2024-08-08', '2024-08-12', 18, 4, 'BCA'),
    ('2024-08-09', '2024-08-13', 19, 5, 'BRI'),
    ('2024-08-10', '2024-08-14', 20, 6, 'BCA'),
    
    ('2024-09-01', '2024-09-05', 21, 2, 'BRI'),
    ('2024-09-02', '2024-09-06', 22, 3, 'Mandiri'),
    ('2024-09-03', '2024-09-07', 23, 4, 'BCA'),
    ('2024-09-04', '2024-09-08', 24, 5, 'Mandiri'),
    ('2024-09-10', '2024-09-14', 30, 6, 'BCA'),

    ('2024-10-01', '2024-10-05', 1, 2, 'BRI'),
    ('2024-10-02', '2024-10-06', 2, 3, 'Mandiri'),
    ('2024-10-03', '2024-10-07', 3, 4, 'BCA'),
    ('2024-10-04', '2024-10-08', 4, 5, 'Mandiri'),
    ('2024-10-05', '2024-10-09', 5, 6, 'BCA'),
    ('2024-10-06', '2024-10-10', 6, 2, 'BRI'),
    ('2024-10-07', '2024-10-11', 7, 3, 'Mandiri'),
    ('2024-10-08', '2024-10-12', 8, 4, 'BCA'),

    ('2024-11-01', '2024-11-05', 11, 2, 'Mandiri'),
    ('2024-11-02', '2024-11-06', 12, 3, 'BCA'),
    ('2024-11-03', '2024-11-07', 13, 4, 'Mandiri'),
    ('2024-11-09', '2024-11-13', 19, 5, 'BRI'),
    ('2024-11-10', '2024-11-14', 20, 6, 'BCA'),

    ('2024-12-01', '2024-12-05', 21, 2, 'BRI'),
    ('2024-12-02', '2024-12-06', 22, 3, 'Mandiri'),
    ('2024-12-03', '2024-12-07', 23, 4, 'BCA'),
    ('2024-12-04', '2024-12-08', 24, 5, 'Mandiri'),
    ('2024-12-05', '2024-12-09', 25, 6, 'BCA'),
    ('2024-12-06', '2024-12-10', 26, 2, 'BRI'),
    ('2024-12-07', '2024-12-11', 27, 3, 'Mandiri'),
    ('2024-12-08', '2024-12-12', 28, 4, 'BCA'),
    ('2024-12-10', '2024-12-14', 30, 6, 'BCA'),

    ('2025-01-01', '2025-01-05', 1, 2, 'BRI'),
    ('2025-01-02', '2025-01-06', 2, 3, 'Mandiri'),
    ('2025-01-03', '2025-01-07', 3, 4, 'BCA'),
    ('2025-01-04', '2025-01-08', 4, 5, 'Mandiri'),
    ('2025-01-05', '2025-01-09', 5, 6, 'BCA'),
    ('2025-01-06', NULL, 6, 2, 'BRI'),
    ('2025-01-07', NULL, 7, 3, 'Mandiri'),
    ('2025-01-08', NULL, 8, 4, 'BCA'),
    ('2025-01-09', NULL, 9, 5, 'BRI'),
    ('2025-01-10', NULL, 10, 6, 'BCA'),
    ('2025-01-06', NULL, 1, 2, 'BRI'),
    ('2025-01-07', NULL, 2, 3, 'Mandiri'),
    ('2025-01-08', NULL, 3, 4, 'BCA'),
    ('2025-01-09', NULL, 4, 5, 'BRI'),
    ('2025-01-10', NULL, 5, 6, 'BCA'),
    ('2025-01-06', NULL, 6, 2, 'BRI'),
    ('2025-01-07', NULL, 7, 3, 'Mandiri'),
    ('2025-01-08', NULL, 8, 4, 'BCA'),
    ('2025-01-09', NULL, 9, 5, 'BRI'),
    ('2025-01-10', NULL, 10, 6, 'BCA');
