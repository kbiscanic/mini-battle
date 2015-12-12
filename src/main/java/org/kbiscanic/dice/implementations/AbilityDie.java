package org.kbiscanic.dice.implementations;

import org.kbiscanic.dice.Die;
import org.kbiscanic.dice.DieResult;

import java.util.Random;

public class AbilityDie implements Die {

    private static final Random RANDOM = new Random();

    private static final AbilityDie instance = new AbilityDie();

    private AbilityDie() {

    }

    public static AbilityDie getInstance() {
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
            case 2:
                result = new DieResult(1, 0, 0, 0, 0, 0);
                break;
            case 3:
                result = new DieResult(2, 0, 0, 0, 0, 0);
                break;
            case 4:
            case 5:
                result = new DieResult(0, 1, 0, 0, 0, 0);
                break;
            case 6:
                result = new DieResult(1, 1, 0, 0, 0, 0);
                break;
            case 7:
                result = new DieResult(0, 2, 0, 0, 0, 0);
        }

        return result;
    }
}
