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

    private Team team;

    private String name;

    private int experience = 0;

    private int level = 1;

    private int wound = 0;

    private int strain = 0;

    private Weapon weapon;

    public Character(Team team, String name, int skillPoints) {
        this.team = team;
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

        weapon = Weapon.values()[RANDOM.nextInt(Weapon.values().length)];
    }

    public Weapon getWeapon() {
        return weapon;
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

        System.out.println(this + " receives " + (damage - getSoak()) + " damage.");

        return wound >= getWoundTreshold();
    }

    public boolean receiveStrain(int strain) {
        this.strain += strain;

        return strain >= getStrainTreshold();
    }

    public void recoverStrain(int strain) {
        this.strain = Math.max(this.strain - strain, 0);
    }

    public void gainExperience(int experience) {
        System.out.println(this + " gains " + experience + " experience.");
        this.experience += experience;

        if (this.experience > (this.level + 1) * 10) {
            levelUp();
        }
    }

    public int getInitiative() {
        DieResult dieResult = DieResult.rollAll(0, getCharacteristic(Characteristic.WILLPOWER), 0, 0, 0, 0);

//        System.out.println(this + " rolls for initiative: " + dieResult.toString());

        return dieResult.getResult(DieSymbol.SUCCESS) + 2 * dieResult.getResult(DieSymbol.ADVANTAGE);
    }

    public int getThreat() {
        DieResult dieResult = DieResult.rollAll(0, getCharacteristic(Characteristic.PRESENCE), 0, 0, 0, 0);

//        System.out.println(this + " rolls for threat: " + dieResult.toString());

        return dieResult.getResult(DieSymbol.SUCCESS) + 2 * dieResult.getResult(DieSymbol.ADVANTAGE);
    }

    public boolean isStrained() {
        return strain >= getStrainTreshold();
    }

    private void levelUp() {
        System.out.println(this + " gains additional level.");
        level++;
        experience = 0;

        Characteristic characteristic = Characteristic.values()[RANDOM.nextInt(Characteristic.values().length)];

        characteristics.put(characteristic, getCharacteristic(characteristic) + 1);
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return team + " " + name + " {" +
                "characteristics=" + characteristics +
                ", experience=" + experience +
                ", level=" + level +
                ", wound=" + wound +
                ", strain=" + strain +
                ", weapon=" + weapon +
                '}';
    }
}
