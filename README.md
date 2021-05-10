# Small Projects

## Contents
1. [Introduction](#introduction)
2. [Python](#python)
3. [Java](#java)
4. [C](#c)
5. [SQL](#sql)

## Introduction
This repo contains small programs that demonstrate my knowledge of different progamming langauges, technologies and computer science. I used many of the python programs to teach at both University of Toronto and Stanford University. You are free to use anything in this repo. 

## Python
This folder contains various Python programs that I used to both learn and teach Python. Some files contained starter code provided by the University of Toronto. 

## Java 
This folder contains Java programs that I used to learn Java during my HBSc at the University of Toronto. Some files contained starter code provided mainly by Arnold Rosenbloom.

#### Othello Game
Othello game written in Java. Starter code was provided by Arnold Rosenbloom. You can play the game by running one of the OthelloControllerHumanVS**** files. To play against another human run OthelloControllerHumanVSHuman. To play against a computer run OthelloControllerHumanVSGreedy or OthelloControllerHumanVSRandom. A random computer makes decisions to place tokens randomnly whereas a Greedy computer always places a token that will lead it to flip the most tokens of the opposing player.

## C
This folder contains various C programs that I used to learn how to program in C. Some files contain starter code provided by the University of Toronto.

#### Fading Sounds
This folder contains a C program, effects.c, that I wrote from scratch that takes an audio file and applies effects to the audio. There are 3 different effects which are fin, fout and pan. fin fades in the audio, causing the volume to slowly increase as the audio plays. fout fades out the audio, casuing the audio to slowly decrease. pan shifts the volume level from low to high to low and also causes the volume to start of by playing on the left speakers and slowly move to the right speakers. The folder contains 3 different audio files with the different effects applied to them. effects.c takes 2 arguments. The first is the type of effect you would like to apply and the second is the time in milliseconds you would like to apply it for(ideally the length of the audio).<br>
Example commands for Linux:<br>
•xxd -g1 rain.wav|./effects -fin 6000|xxd -r >rain_fin.wav <br>
•xxd -g1 rain.wav|./effects -fout 6000|xxd -r >rain_fout.wav <br>
•xxd -g1 rain.wav|./effects -pan 6000|xxd -r >rain_pan.wav <br>

## SQL
This folder contains some work I did using SQL and PostgreSQL. lahman-postgres-dump.sql was provided by the Univeristy of Toronto. It will construct the tables for the open-source Lahman Baseball Database.
