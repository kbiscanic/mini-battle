package org.kbiscanic.dice;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.kbiscanic.dice.implementations.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DieResult {

    private Map<DieSymbol, Integer> results = Maps.newEnumMap(DieSymbol.class);

    public DieResult() {
        this(0, 0, 0, 0, 0, 0);
    }

    public DieResult(int success, int advantage, int triumph, int failure, int threat, int despair) {
        success = success - failure;

        if (success > 0) {
            results.put(DieSymbol.SUCCESS, success);
        } else if (success < 0) {
            results.put(DieSymbol.FAILURE, -success);
        }

        advantage = advantage - threat;
        if (advantage > 0) {
            results.put(DieSymbol.ADVANTAGE, advantage);
        } else if (advantage < 0) {
            results.put(DieSymbol.THREAT, -advantage);
        }

        if (triumph > 0) {
            results.put(DieSymbol.TRIUMPH, triumph);
        }

        if (despair > 0) {
            results.put(DieSymbol.DESPAIR, despair);
        }
    }

    public static DieResult combine(DieResult r1, DieResult r2) {
        return new DieResult(r1.getResult(DieSymbol.SUCCESS) + r2.getResult(DieSymbol.SUCCESS),
                r1.getResult(DieSymbol.ADVANTAGE) + r2.getResult(DieSymbol.ADVANTAGE),
                r1.getResult(DieSymbol.TRIUMPH) + r2.getResult(DieSymbol.TRIUMPH),
                r1.getResult(DieSymbol.FAILURE) + r2.getResult(DieSymbol.FAILURE),
                r1.getResult(DieSymbol.THREAT) + r2.getResult(DieSymbol.THREAT),
                r1.getResult(DieSymbol.DESPAIR) + r2.getResult(DieSymbol.DESPAIR));
    }

    public static DieResult rollAll(List<Die> dice) {
        return dice.stream().map(Die::roll).reduce(new DieResult(), DieResult::combine);
    }


    public static DieResult rollAll(int boost, int ability, int proficiency, int setback, int difficulty, int challenge) {
        List<Die> dice = Lists.newArrayListWithCapacity(boost + ability + proficiency + setback + difficulty + challenge);

        while (boost > 0) {
            dice.add(BoostDie.getInstance());
            boost--;
        }
        while (ability > 0) {
            dice.add(AbilityDie.getInstance());
            ability--;
        }
        while (proficiency > 0) {
            dice.add(ProficiencyDie.getInstance());
            proficiency--;
        }
        while (setback > 0) {
            dice.add(SetbackDie.getInstance());
            setback--;
        }
        while (difficulty > 0) {
            dice.add(DifficultyDie.getInstance());
            difficulty--;
        }
        while (challenge > 0) {
            dice.add(ChallengeDie.getInstance());
            challenge--;
        }

        return rollAll(dice);
    }

    public int getResult(DieSymbol symbol) {
        Optional<Integer> result = Optional.ofNullable(results.get(symbol));

        return result.orElse(0);
    }

    @Override
    public String toString() {
        return "DieResult" + results;
    }
}
