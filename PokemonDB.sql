Drop database if exists pokemonDB;
Create database pokemonDB;
Use pokemonDB;

CREATE TABLE `User` (
  `Userid` int auto_increment Not Null,
  `FirstName` VARCHAR(30) NOT NULL,
  `LastName` VARCHAR(30) NOT NULL,
  `Money` DOUBLE NOT NULL,
  `Email` VARCHAR(50) NOT NULL,
  `password`  VARCHAR(50) NOT NULL,
  primary key(`Userid`)
);

CREATE TABLE `Type` (
  `Typeid` INT auto_increment Not Null,
  `Name` VARCHAR(50) NOT NULL,
   primary key(`Typeid`)
);

CREATE TABLE `Ability` (
  `Abilityid` INT auto_increment Not Null,
  `Name` VARCHAR(50) NOT NULL,
  `AP` int NOT NULL,
  `Attack` int NOT NULL,
  primary key(`Abilityid`)
);

CREATE TABLE `Pokemon` (
  `Pokemonid` INT auto_increment Not Null,
  `Name` VARCHAR(50) NOT NULL,
  `Health` INT NOT NULL,
  `Weight` DOUBLE,
  `Height` DOUBLE ,
  `Typeid` INT,
  `Image`  VARCHAR(200),
  `Price` DOUBLE NOT NULL,
  foreign key(`Typeid`) REFERENCES `Type`(`Typeid`),
  primary key(`Pokemonid`)
);

CREATE TABLE `User_Pokemon` (
  `Pokemonid` INT ,
  `Userid` INT,
  `Health` INT,
  PRIMARY KEY(`Pokemonid`, `Userid`),
  foreign key(`Pokemonid`) REFERENCES Pokemon(`Pokemonid`),
  foreign key(`Userid`) REFERENCES `User`(`Userid`)
);


CREATE TABLE `Poke_Ability` (
  `Pokemonid` INT,
  `Abilityid` INT,
  PRIMARY KEY(`Pokemonid`, `Abilityid`),
  foreign key(`Pokemonid`) REFERENCES Pokemon(`Pokemonid`),
  foreign key(`Abilityid`) REFERENCES `Ability`(`Abilityid`)
);

CREATE TABLE `Battle` (
	`Battleid` int auto_increment Not Null,
	`Userid` INT,
    `Opponentid` INT,
    `UserPokemonid` INT,
    primary key(`Battleid`)
);

