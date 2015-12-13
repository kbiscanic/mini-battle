package org.kbiscanic.minibattle.dice.implementations;

import org.kbiscanic.minibattle.dice.Die;
import org.kbiscanic.minibattle.dice.DieResult;

import java.util.Random;

public class ChallengeDie implements Die {

    private static final Random RANDOM = new Random();

    private static final ChallengeDie instance = new ChallengeDie();

    private ChallengeDie() {

    }

    public static ChallengeDie getInstance() {
        return instance;
    }

    @Override
    public DieResult roll() {
        DieResult result = null;

        switch (RANDOM.nextInt(12)) {
            case 0:
                result = new DieResult(0, 0, 0, 0, 0, 0);
                break;
            case 1:
            case 2:
                result = new DieResult(0, 0, 0, 1, 0, 0);
                break;
            case 3:
            case 4:
                result = new DieResult(0, 0, 0, 2, 0, 0);
                break;
            case 5:
            case 6:
                result = new DieResult(0, 0, 0, 0, 1, 0);
                break;
            case 7:
            case 8:
                result = new DieResult(0, 0, 0, 1, 1, 0);
                break;
            case 9:
            case 10:
                result = new DieResult(0, 0, 0, 0, 2, 0);
                break;
            case 11:
                result = new DieResult(0, 0, 0, 0, 0, 1);
        }

        return result;
    }
}
