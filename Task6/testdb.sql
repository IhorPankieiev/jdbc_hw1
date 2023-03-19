DROP DATABASE IF EXISTS testdb;

CREATE DATABASE testdb;

USE testdb;

CREATE TABLE testtable(
	id INT AUTO_INCREMENT NOT NULL primary key,
    FName VARCHAR(30),
    LName VARCHAR(30),
    age INT(10)
)