package org.kbiscanic.armies;

import com.google.common.collect.Maps;
import org.kbiscanic.dice.DieResult;
import org.kbiscanic.dice.DieSymbol;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Character {

    private static final Random RANDOM = new Random();

    private Map<Characteristic, Integer> characteristics = Maps.newEnumMap(Characteristic.class);

    private String name;

    private int experience = 0;

    private int level = 1;

    private int wound = 0;

    private int strain = 0;

    public Character(String name, int skillPoints) {
        this.name = name;

        characteristics.put(Characteristic.AGILITY, 1);
        characteristics.put(Characteristic.BRAWN, 1);
        characteristics.put(Characteristic.CUNNING, 1);
        characteristics.put(Characteristic.INTELLECT, 1);
        characteristics.put(Characteristic.PRESENCE, 1);
        characteristics.put(Characteristic.WILLPOWER, 1);

        while (skillPoints > 0) {
            Characteristic characteristic = Characteristic.values()[RANDOM.nextInt(Characteristic.values().length)];

            characteristics.put(characteristic, getCharacteristic(characteristic) + 1);

            skillPoints--;
        }
    }


    public String getName() {
        return name;
    }

    public int getCharacteristic(Characteristic characteristic) {
        Optional<Integer> result = Optional.ofNullable(characteristics.get(characteristic));

        return result.orElse(0);
    }

    public int getWoundTreshold() {
        return getCharacteristic(Characteristic.BRAWN) + 10;
    }

    public int getStrainTreshold() {
        return getCharacteristic(Characteristic.WILLPOWER) + 10;
    }

    public int getSoak() {
        return getCharacteristic(Characteristic.BRAWN);
    }

    public boolean receiveDamage(int damage) {
        if (damage < getSoak()) {
            return false;
        }
        wound += damage - getSoak();

        return wound >= getWoundTreshold();
    }

    public boolean receiveStrain(int strain) {
        this.strain += strain;

        return strain >= getStrainTreshold();
    }

    public void gainExperience(int experience) {
        this.experience += experience;

        if (this.experience > this.level * 10) {
            levelUp();
        }
    }

    public int getInitiative() {
        DieResult dieResult = DieResult.rollAll(0, getCharacteristic(Characteristic.WILLPOWER), 0, 0, 0, 0);

        System.out.println(name + " rolls for initiative: " + dieResult.toString());

        return dieResult.getResult(DieSymbol.SUCCESS) + 2 * dieResult.getResult(DieSymbol.ADVANTAGE);
    }

    public int getThreat() {
        DieResult dieResult = DieResult.rollAll(0, getCharacteristic(Characteristic.PRESENCE), 0, 0, 0, 0);

        System.out.println(name + " rolls for threat: " + dieResult.toString());

        return dieResult.getResult(DieSymbol.SUCCESS) + 2 * dieResult.getResult(DieSymbol.ADVANTAGE);
    }

    public void levelUp() {
        level++;
        experience = 0;

        Characteristic characteristic = Characteristic.values()[RANDOM.nextInt(Characteristic.values().length)];

        characteristics.put(characteristic, getCharacteristic(characteristic) + 1);
    }

    @Override
    public String toString() {
        return name + " {" +
                "characteristics=" + characteristics +
                ", experience=" + experience +
                ", level=" + level +
                ", wound=" + wound +
                ", strain=" + strain +
                "}\n";
    }
}
