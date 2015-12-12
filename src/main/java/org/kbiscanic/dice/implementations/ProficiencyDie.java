package org.kbiscanic.dice.implementations;

import org.kbiscanic.dice.Die;
import org.kbiscanic.dice.DieResult;

import java.util.Random;

public class ProficiencyDie implements Die {

    private static final Random RANDOM = new Random();

    private static final ProficiencyDie instance = new ProficiencyDie();

    private ProficiencyDie() {

    }

    public static ProficiencyDie getInstance() {
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
                result = new DieResult(1, 0, 0, 0, 0, 0);
                break;
            case 3:
            case 4:
                result = new DieResult(2, 0, 0, 0, 0, 0);
                break;
            case 5:
                result = new DieResult(0, 1, 0, 0, 0, 0);
                break;
            case 6:
            case 7:
            case 8:
                result = new DieResult(1, 1, 0, 0, 0, 0);
                break;
            case 9:
            case 10:
                result = new DieResult(0, 2, 0, 0, 0, 0);
                break;
            case 11:
                result = new DieResult(0, 0, 1, 0, 0, 0);
        }

        return result;
    }
}
