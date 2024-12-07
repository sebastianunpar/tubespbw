CREATE TABLE users (
    userId SERIAL PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(70),
    password VARCHAR(50),
    role VARCHAR(10) CHECK (role IN ('user', 'admin'))


)

CREATE TABLE film (
    filmId SERIAL PRIMARY KEY,
    title VARCHAR(30),
    price NUMERIC

)

CREATE TABLE actor (
    actorId SERIAL PRIMARY KEY,
    name VARCHAR(50)
)

CREATE TABLE genre (
    genreId SERIAL PRIMARY KEY,
    name VARCHAR(20)
)


CREATE TABLE rental (
    rentalId SERIAL PRIMARY KEY,
    dateRented DATE,
    
)