CREATE TABLE users (
    userId SERIAL PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(70),
    password VARCHAR(50),
    role VARCHAR(10) CHECK (role IN ('user', 'admin'))
);

CREATE TABLE film (
    filmId SERIAL PRIMARY KEY,
    title VARCHAR(30),
    synopsis VARCHAR(500),
    image BYTEA,
    stock INT CHECK (stock >= 0),
    sold INT,
    price NUMERIC
);

CREATE TABLE actor (
    actorId SERIAL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE genre (
    genreId SERIAL PRIMARY KEY,
    name VARCHAR(20)
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
    returnDate DATE
);