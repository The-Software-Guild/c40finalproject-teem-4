use pokemonDB;


INSERT INTO type (`name`) VALUES ("grass");
INSERT INTO type (`name`) VALUES ("fire");
INSERT INTO type (`name`) VALUES ("water");
INSERT INTO type (`name`) VALUES ("electric");
INSERT INTO type (`name`) VALUES ("flying");


INSERT INTO Ability (`name`, AP, Attack) VALUES ("chlorophyll", 3, 3);
INSERT INTO Ability (`name`, AP, Attack) VALUES ("overgrow", 2, 3);

INSERT INTO Ability (`name`, AP, Attack) VALUES ("blaze", 3, 3);
INSERT INTO Ability (`name`, AP, Attack) VALUES ("solar-power", 2, 3);

INSERT INTO Ability (`name`, AP, Attack) VALUES ("torrent", 3, 3);
INSERT INTO Ability (`name`, AP, Attack) VALUES ("rain", 2, 3);

INSERT INTO Ability (`name`, AP, Attack) VALUES ("static", 3, 3);
INSERT INTO Ability (`name`, AP, Attack) VALUES ("lightning-rod", 2, 3);

INSERT INTO Ability (`name`, AP, Attack) VALUES ("big-pecks", 3, 4);
INSERT INTO Ability (`name`, AP, Attack) VALUES ("keen-eye", 4, 6);


-- new Pokemon 1
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("bulbasaur", 69 ,7, 1,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",10 , 10);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (1,1);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (1,2);

-- new Pokemon 2
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("bulbasaur", 1000 ,20, 1,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png", 28 , 24);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (2,1);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (2,2);

-- new Pokemon 3
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("charmander", 85 , 6, 2,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png", 15 , 12);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (3,3);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (3,4);

-- new Pokemon 4
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("charizard", 905 , 17, 2,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png", 30 , 25);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (4,3);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (4,4);

-- new Pokemon 5
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("squirtle", 90 , 5, 3,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png", 12 , 10);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (5,5);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (5,6);

-- new Pokemon 6
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("charizard", 855 , 16, 3,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/9.png", 28 , 23);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (6,5);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (6,6);

-- new Pokemon 7
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("pikachu", 60 , 4, 4,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png", 8 , 10);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (7,7);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (7,8);

-- new Pokemon 8
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("raichu", 300 , 8, 2,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/26.png", 16 , 20);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (8,7);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (8,8);

-- new Pokemon 9
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("pidgey", 18 , 3, 5,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/16.png", 15 , 12);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (9,9);

-- new Pokemon 10
INSERT INTO pokemon (`name`, weight, height, typeid, image, price, health) 
VALUES ("charizard", 395 , 15, 5,"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/18.png", 30 , 25);

INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (10,9);
INSERT INTO poke_ability (Pokemonid, Abilityid) VALUES (10,10);


