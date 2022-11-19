# My Personal Project

## Proposal

***What will the application do?***

The application will be a turn based Pokemon battling game.
It will include at least one of two battling modes: person vs person or 
person vs computer.
The battles will be 3 on 3 battles and the trainer(s) will get to choose 
the Pokemon they use.
There will also be some function that allows users to create Pokemon of their 
own, which they can then use in the battles.

***Who will use it?***

Anyone can use it. It's just a matter of if they want to play the game or not. 

***Why is this project of interest?***

Since I was young I have enjoyed playing Pokemon and I feel as though 
this project will give me a chance to make something based on the game 
I have enjoyed for so long.

## User Stories

- As a user, I want to be able to create Pokemon and have those Pokemon added 
to the Pokedex (the list of Pokemon usable for battle)
- As a user, I want to be able to battle a computer controlled opponent in a 3 v 3 Pokemon battle
- As a user, I want to be able to select the 3 Pokemon that I use for battle
- As a user, during the battle I want to be able to select the move my Pokemon will use to attack the opponent's
Pokemon
- As a user, during the battle I want to be able to swap the Pokemon that I currently have in the battle for 
another Pokemon on my team
- As a user, upon selecting the quit option in the Main Menu, I want to be given the option to save
the current Pokedex and the user's trainer info to file
- As a user, on application start up, I want to be given the option to load the previous save of the 
Pokedex and user's trainer info from file
- As a user, after selecting my Pokemon team once, I want to be able to use that same team again in 
future battles without having to choose my team again

## Instructions For Grader

***To generate the first required event related to adding Xs to a Y***
1. after start up, click the create Pokemon button in the main menu
2. fill out all the empty fields above the Move Set Header
3. fill out at least one move set field
4. click the create button (all the fields should clear when the Pokemon gets added, if it doesn't just
click the button again)
5. this will add a Pokemon (X) to the Pokedex (Y)

***To generate the second required event related to adding Xs to a Y***
1. after start up, click the Battle button in the main menu
2. select a Pokemon in the "Available Pokemon" list (represents the list of Pokemon in the Pokedex) and click add 
3. this will add a Pokemon from the Pokedex to the user's team (if you don't have a team of 3 already)

***To generate a third event related to adding Xs to a Y***
1. after start up, click the Battle button in the main menu
2. click the JComboBox and select which type you want to sort the Pokedex by
3. after selecting a type, click the filter button
4. this will sort the display of the Pokedex by the selected Pokemon typing

***To locate visual component 1***
1. after getting through the startup, the image should appear on the main menu

***To locate visual component 2***
1. after start up, click the Battle button in the main menu
2. click the battle button in the Team Select Menu after filling your team with 3 Pokemon
3. the image should then appear

***To save the state of my application***
1. after clicking the quit button in the main menu, you will be prompted with the option to save your data
2. click the "yes" button to save your data

***To reload the state of my application***
1. on startup, you will be prompted with the option to load the previously saved data
2. click the "yes" button to reload the saved data