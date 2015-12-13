package org.kbiscanic.minibattle.dice.implementations;

import org.kbiscanic.minibattle.dice.Die;
import org.kbiscanic.minibattle.dice.DieResult;

import java.util.Random;

public class SetbackDie implements Die {

    private static final Random RANDOM = new Random();

    private static final SetbackDie instance = new SetbackDie();

    private SetbackDie() {

    }

    public static SetbackDie getInstance() {
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
            case 3:
                result = new DieResult(0, 0, 0, 1, 0, 0);
                break;
            case 4:
            case 5:
                result = new DieResult(0, 0, 0, 0, 1, 0);
        }

        return result;
    }
}
