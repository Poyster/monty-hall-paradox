package game;


import dto.Door;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private final Random random = new Random();

    public Game(int numberOfRounds) {
        playGame(numberOfRounds);
    }

    private void playGame(int rounds) {
        int numberOfDoors = 3;
        log.info("Number of rounds to be played: {}", rounds);

        int numberOfCarsWon = 0;
        int numberOfCarsWonByNotSwitching = 0;

        for (int i = 0; i < rounds; i++) {

            int doorWithCarBehind = random.nextInt(numberOfDoors);

            List<Door> doorsList = IntStream.range(0, numberOfDoors)
                    .mapToObj(Door::new)
                    .collect(Collectors.toList());

            //Player chooses door
            int chooseInitialDoor = random.nextInt(numberOfDoors);

            //Remove from list since Player always switches in this simulation
            doorsList.removeIf(door -> door.getId() == chooseInitialDoor);

            if (chooseInitialDoor == doorWithCarBehind) {
                numberOfCarsWonByNotSwitching++;
                Door doorWithGoat = doorsList.get(random.nextInt(doorsList.size()));
                doorsList.remove(doorWithGoat);
            } else {
                Door door = doorsList.stream().filter(d -> d.getId() != doorWithCarBehind).findAny().orElse(null);
                doorsList.remove(door);
            }

            //Only the door the Player chooses to switch to remains
            if (doorsList.get(0).getId() == doorWithCarBehind) {
                numberOfCarsWon++;
            }
        }
        log.info("---");
        log.info("By switching doors after the host showed you one door with a goat, you won the car: {} times out of a {} rounds played!", numberOfCarsWon, rounds);
        log.info("---");
        log.info("Number of times you would have won the car if not switching doors: {}", numberOfCarsWonByNotSwitching);
    }




}
