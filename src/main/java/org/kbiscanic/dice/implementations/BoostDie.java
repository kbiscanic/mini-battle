package org.kbiscanic.dice.implementations;

import org.kbiscanic.dice.Die;
import org.kbiscanic.dice.DieResult;

import java.util.Random;

public class BoostDie implements Die {

    private static final Random RANDOM = new Random();

    private static final BoostDie instance = new BoostDie();

    private BoostDie() {

    }

    public static BoostDie getInstance() {
        return instance;
    }

    @Override
    public DieResult roll() {
        DieResult result = null;

        switch (RANDOM.nextInt(6)) {
            case 0:
            case 1:
                result = new DieResult(0, 0, 0, 0, 0, 0);
                break;
            case 2:
                result = new DieResult(1, 0, 0, 0, 0, 0);
                break;
            case 3:
                result = new DieResult(1, 1, 0, 0, 0, 0);
                break;
            case 4:
                result = new DieResult(0, 2, 0, 0, 0, 0);
                break;
            case 5:
                result = new DieResult(0, 1, 0, 0, 0, 0);
        }

        return result;
    }
}
