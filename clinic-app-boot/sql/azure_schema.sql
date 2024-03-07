IF OBJECT_ID('visits', 'U') IS NOT NULL DROP TABLE visits;
IF OBJECT_ID('statuses', 'U') IS NOT NULL DROP TABLE statuses;
IF OBJECT_ID('specializations', 'U') IS NOT NULL DROP TABLE specializations;
IF OBJECT_ID('users', 'U') IS NOT NULL DROP TABLE users;
IF OBJECT_ID('roles', 'U') IS NOT NULL DROP TABLE roles;

-- Create tables
CREATE TABLE roles
(
    id   INT IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE specializations
(
    id   INT IDENTITY(1,1) NOT NULL,
    name NVARCHAR(255)     NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE users
(
    id                INT IDENTITY(1,1) NOT NULL,
    name              NVARCHAR(255)     NOT NULL,
    email             NVARCHAR(255)     NOT NULL UNIQUE,
    password          NVARCHAR(255)     NOT NULL,
    role_id           INT                NOT NULL,
    specialization_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (specialization_id) REFERENCES specializations(id)
    );

CREATE TABLE statuses
(
    id   INT IDENTITY(1,1) NOT NULL,
    name NVARCHAR(255)     NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE visits
(
    id        INT IDENTITY(1,1) NOT NULL,
    user_id   INT                NOT NULL,
    doctor_id INT                NOT NULL,
    date      DATETIME           NOT NULL,
    status_id INT                NOT NULL,
    problem   NVARCHAR(MAX)      NOT NULL,
    diagnosis NVARCHAR(MAX),
    treatment NVARCHAR(MAX),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (doctor_id) REFERENCES users(id),
    FOREIGN KEY (status_id) REFERENCES statuses(id)
    );

-- Insert data
INSERT INTO roles (name) VALUES ('USER'), ('DOCTOR');

INSERT INTO specializations (name)
VALUES ('Cardiologist'), ('Dentist'), ('Dermatologist'), ('Endocrinologist'),
       ('Gastroenterologist'), ('Neurologist'), ('Ophthalmologist'),
       ('Otolaryngologies'), ('Pediatrician'), ('Psychiatrist'),
       ('Rheumatologist'), ('Surgeon');

INSERT INTO users (name, email, password, role_id, specialization_id)
VALUES
    ('Barak Obama', 'barak@gmail.com', '123', 1, NULL),
    ('Donald Trump', 'donald@gmail.com', '123', 1, NULL),
    ('Joe Biden', 'joe@gmail.com', '123', 1, NULL),
    ('John Smith', 'smith@gmail.com', '123', 1, NULL),
    ('Gregory House', 'house@gmail.com', '123', 2, 1),
    ('James Wilson', 'wilson@gmail.com', '123', 2, 2),
    ('Eric Foreman', 'foreman@gmail.com', '123', 2, 3),
    ('Robert Chase', 'chase@gmail.com', '123', 2, 4),
    ('Allison Cameron', 'alison@gmail.com', '123', 2, 5),
    ('Lisa Cuddy', 'cuddy@gmail.com', '123', 2, 6),
    ('Chris Taub', 'taub@gmail.com', '123', 2, 7),
    ('Lawrence Kutner', 'kutner@gmail.com', '123', 2, 8),
    ('Remy Hadley', 'hadley@gmail.com', '123', 2, 9),
    ('Amber Volakis', 'volkatis@gmail.com', '123', 2, 10),
    ('Martha Masters', 'masters@gmail.com', '123', 2, 11),
    ('Jessica Adams', 'adams@gmail.com', '123', 2, 12),
    ('Chi Park', 'park@gmail.com', '123', 2, 1),
    ('Henry Dobson', 'dobson@gmail.com', '123', 2, 2),
    ('Wendy Lee', 'lee@gmail.com', '123', 2, 3),
    ('Peter Treiber', 'treiber@gmail.com', '123', 2, 4),
    ('Karl Neumann', 'naumann@gmail.com', '123', 2, 5),
    ('Hans Mueller', 'maeller@gmail.com', '123', 2, 6),
    ('Klaus Schmidt', 'schmidt@gmail.com', '123', 2, 7),
    ('Johann Fischer', 'fischer@gmail.com', '123', 2, 8),
    ('Michael Weber', 'weber@gmail.com', '123', 2, 9),
    ('Thomas Wagner', 'wagner@gmail.com', '123', 2, 10),
    ('Uwe Becker', 'becker@gmail.com', '123', 2, 11),
    ('Klaus Hoffmann', 'hoffmann@gmail.com', '123', 2, 12);

INSERT INTO statuses (name) VALUES ('Pending'), ('Done'), ('Canceled'), ('In progress');

INSERT INTO visits (user_id, doctor_id, date, status_id, problem)
VALUES
    (1, 5, '2020-01-01', 1, 'I have a problem with my heart'),
    (2, 6, '2020-01-02', 1, 'I have a problem with my teeth'),
    (3, 7, '2020-01-03', 1, 'I have a problem with my stomach'),
    (4, 8, '2020-01-04', 1, 'I have a problem with my eyes'),
    (1, 5, '2021-05-16', 1, 'I have a problem with my heart'),
    (2, 6, '2021-05-17', 1, 'I have a problem with my teeth'),
    (3, 7, '2021-05-18', 1, 'I have a problem with my stomach'),
    (4, 8, '2021-05-19', 1, 'I have a problem with my eyes'),
    (2, 5, '2021-05-20', 1, 'I have a problem with my heart'),
    (3, 5, '2022-06-21', 1, 'I have pain in my heart');
