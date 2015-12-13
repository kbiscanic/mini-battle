# mini-battle
### Description
This project simulates mini battle of 2 armies, each consisting of N soldiers.
Soldiers are generated and battle is simulated using**very**simplified version
of [Star Wars: Age of Rebellion RPG (FFG)](https://www.fantasyflightgames.com/en/products/star-wars-age-of-rebellion/)
core rules.

Implementation is entirely written in Java 8 and has no user interface - only
textual output of results.

### Usage
```bash
git clone https://github.com/kbiscanic/mini-battle.git

mvn package

# N = number of soldiers in each army
java -jar target/mini-battle-1.0-SNAPSHOT-jar-with-dependencies.jar N
```