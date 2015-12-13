package org.kbiscanic.minibattle.dice.implementations;

import org.kbiscanic.minibattle.dice.Die;
import org.kbiscanic.minibattle.dice.DieResult;

import java.util.Random;

public class DifficultyDie implements Die {

    private static final Random RANDOM = new Random();

    private static final DifficultyDie instance = new DifficultyDie();

    private DifficultyDie() {

    }

    public static DifficultyDie getInstance() {
        return instance;
    }

    @Override
    public DieResult roll() {
        DieResult result = null;

        switch (RANDOM.nextInt(8)) {
            case 0:
                result = new DieResult(0, 0, 0, 0, 0, 0);
                break;
            case 1:
                result = new DieResult(0, 0, 0, 1, 0, 0);
                break;
            case 2:
                result = new DieResult(0, 0, 0, 2, 0, 0);
                break;
            case 3:
            case 4:
            case 5:
                result = new DieResult(0, 0, 0, 0, 1, 0);
                break;
            case 6:
                result = new DieResult(0, 0, 0, 0, 2, 0);
                break;
            case 7:
                result = new DieResult(0, 0, 0, 1, 1, 0);
        }

        return result;
    }
}
