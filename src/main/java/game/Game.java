package game;


import dto.Door;
import dto.Prize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private static final Logger log = LogManager.getLogger(Game.class);

    private final Random random = new Random();
    int numberOfDoors = 3;
    int numberOfCarsWon = 0;
    int numberOfCarsWonByNotSwitching = 0;

    public Game(int numberOfRounds) {
        playGame(numberOfRounds);
    }

    private void playGame(int numberOfRounds) {
        log.info("Number of rounds to be played: {}", numberOfRounds);

        for (int i = 0; i < numberOfRounds; i++) {

            int doorWithCarBehind = random.nextInt(numberOfDoors);
            List<Door> doorsList = createDoorsWithPrizes(doorWithCarBehind);

            //Player chooses door
            int chooseInitialDoor = random.nextInt(numberOfDoors);
            openDoors(doorsList, chooseInitialDoor, doorWithCarBehind);
        }
            logResults(numberOfRounds);
    }

    private List<Door> createDoorsWithPrizes(int doorWithCarBehind) {
        List<Door> doorsList = IntStream.range(0, numberOfDoors)
                .mapToObj(id -> new Door(id, new Prize(true, false)))
                .collect(Collectors.toList());

        doorsList.stream()
                .filter(d -> d.getId() == doorWithCarBehind)
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .setPrize(new Prize(false, true));

        return doorsList;
    }

    private void openDoors(List<Door> doorsList, int chooseInitialDoor, int doorWithCarBehind) {

        //Remove from list since Player always switches in this simulation
        doorsList.removeIf(door -> door.getId() == chooseInitialDoor);

        if (chooseInitialDoor == doorWithCarBehind) {
            numberOfCarsWonByNotSwitching++;
            Door doorWithGoat = doorsList.get(random.nextInt(doorsList.size()));
            doorsList.remove(doorWithGoat);
        } else {
            Door door = doorsList.stream()
                    .filter(d -> d.getId() != doorWithCarBehind)
                    .findAny()
                    .orElse(null);
            doorsList.remove(door);
        }

        //Only the door the Player chooses to switch to remains
        if (doorsList.get(0).getId() == doorWithCarBehind && doorsList.get(0).getPrize().isCar()) {
            numberOfCarsWon++;
        }
    }

    private void logResults(int numberOfRounds) {
        log.info("---");
        log.info("By switching doors after the host showed you one door with a goat, you won the car: " +
                "{} times out of a {} rounds played!", numberOfCarsWon, numberOfRounds);
        log.info("---");
        log.info("Number of times you would have won the car if not switching doors: " +
                "{}", numberOfCarsWonByNotSwitching);
    }


}
