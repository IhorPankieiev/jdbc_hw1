DROP DATABASE MyJoinsDB;
CREATE DATABASE MyJoinsDB;


USE MyJoinsDB;

CREATE TABLE Contact_Info
(
	id INT AUTO_INCREMENT NOT NULL,
	FName VARCHAR(15),
    LName VARCHAR(15),
    PhoneNo VARCHAR (15),
    PRIMARY KEY (id)
);


CREATE TABLE Payment_Info
(
    id INT NOT NULL,
    Salary INT NOT NULL,
    Title VARCHAR (30),
    PRIMARY KEY (id)
);


CREATE TABLE Personal_Info
(
	id INT NOT NULL UNIQUE,
    FamilyStatus VARCHAR(30),
    Birthday VARCHAR(30),
    Address VARCHAR (30),
    PRIMARY KEY (id)
)