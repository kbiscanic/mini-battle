package org.kbiscanic.minibattle;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import org.kbiscanic.minibattle.armies.*;
import org.kbiscanic.minibattle.armies.Character;
import org.kbiscanic.minibattle.dice.DieResult;
import org.kbiscanic.minibattle.dice.DieSymbol;

import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Battle {

    private static final Random RANDOM = new Random();

    private Map<Team, Army> armies = Maps.newEnumMap(Team.class);

    public Battle(Army red, Army blue) {
        armies.put(Team.RED, red);
        armies.put(Team.BLUE, blue);
    }

    public void run() {
        Map<Team, Queue<Character>> initiatives = Maps.newEnumMap(Team.class);
        initiatives.put(Team.RED, Queues.newLinkedBlockingQueue(armies.get(Team.RED).determineInitiative()));
        initiatives.put(Team.BLUE, Queues.newLinkedBlockingQueue(armies.get(Team.BLUE).determineInitiative()));

        Map<Team, Queue<Character>> threats = Maps.newEnumMap(Team.class);
        threats.put(Team.BLUE, Queues.newLinkedBlockingQueue(armies.get(Team.RED).determineThreat()));
        threats.put(Team.RED, Queues.newLinkedBlockingQueue(armies.get(Team.BLUE).determineThreat()));

        System.out.println();

        Team onTurn = RANDOM.nextBoolean() ? Team.RED : Team.BLUE;

        int counter = 0;

        while (armies.get(Team.RED).alive() > 0 && armies.get(Team.BLUE).alive() > 0) {
            System.out.println("=== ROUND " + ++counter + " BEGINS ===");
            System.out.println("RED: " + armies.get(Team.RED).alive());
            System.out.println("BLUE: " + armies.get(Team.BLUE).alive());
            System.out.println("Aggressor: " + onTurn);
            System.out.println("------------------------------");
            Character current = initiatives.get(onTurn).poll();
            while (!armies.get(onTurn).isAlive(current)) {
                current = initiatives.get(onTurn).poll();
            }

            System.out.println("Attacker: " + current);

            if (current.isStrained()) {
                System.out.println("Attacker is too strained and rests for this turn.");

                current.recoverStrain(1);
                initiatives.get(onTurn).add(current);

                onTurn = other(onTurn);
                continue;
            }


            Character target = threats.get(onTurn).poll();
            while (!armies.get(other(onTurn)).isAlive(target)) {
                target = threats.get(onTurn).poll();
            }

            System.out.println("Target: " + target);

            boolean isMelee = RANDOM.nextBoolean();

            if (current.getWeapon().equals(Weapon.MELEE) && !isMelee) { // check if character is melee only
                initiatives.get(onTurn).add(current);
                threats.get(onTurn).add(target);

                System.out.println("Attacker cannot attack target (out of range).");

                onTurn = other(onTurn);
                continue;
            }

            int difficulty = 0; // difficulty for attacking with ranged weapon in melee combat
            if (isMelee && current.getWeapon().equals(Weapon.RANGED_L)) {
                difficulty = 1;
            } else if (isMelee && current.getWeapon().equals(Weapon.RANGED_H)) {
                difficulty = 2;
            }

            int boost = 0; // boost for attacking ranged opponent in melee combat
            if (isMelee && !target.getWeapon().equals(Weapon.MELEE)) {
                boost = 1;
            }

            Characteristic primary; // characteristic used for primary skill check, depends on weapon type
            if (current.getWeapon().equals(Weapon.MELEE)) {
                primary = Characteristic.BRAWN;
            } else {
                primary = Characteristic.AGILITY;
            }

            DieResult attack = DieResult.rollAll(boost, current.getCharacteristic(primary) - 1, 1, 0, difficulty, 1);

            System.out.println("Attacker rolls for an attack:" + attack);

            boolean kill = false;
            if (attack.getResult(DieSymbol.TRIUMPH) > 0) {
                kill = true;
                System.out.println("Attacker critically hits and kills target.");
            }
            if (attack.getResult(DieSymbol.DESPAIR) > 0) {
                armies.get(onTurn).kill(current);
                System.out.println("Attacker got killed due to weapon malfunction.");
            }

            if (attack.getResult(DieSymbol.ADVANTAGE) > 0) {
                System.out.println("Attacker recovers " + attack.getResult(DieSymbol.ADVANTAGE) + " strain.");
                current.recoverStrain(attack.getResult(DieSymbol.ADVANTAGE));
            } else if (attack.getResult(DieSymbol.THREAT) > 0) {
                System.out.println("Attacker receives " + attack.getResult(DieSymbol.THREAT) + " strain.");
                current.receiveStrain(attack.getResult(DieSymbol.ADVANTAGE));
            }

            if (!kill && attack.getResult(DieSymbol.SUCCESS) > 0) {
                int damage = 10;

                if (current.getWeapon().equals(Weapon.RANGED_L)) {
                    damage = 5;
                }

                System.out.println("Attack succeeds and deals " + damage + " damage.");

                kill = target.receiveDamage(damage);
            } else if (!kill) {
                System.out.println("Attack misses.");
            }

            if (kill) {
                System.out.println("Target dies.");
                armies.get(other(onTurn)).kill(target);

                current.gainExperience(target.getLevel() * 10); // award experience for kill
            }

            initiatives.get(onTurn).add(current);
            if (!kill) {
                threats.get(onTurn).add(target);
            }

            onTurn = other(onTurn);
        }

        System.out.println();
        System.out.println("=== BATTLE ENDS ===");
        System.out.println("RED: " + armies.get(Team.RED).alive());
        System.out.println("BLUE: " + armies.get(Team.BLUE).alive());
        System.out.println("===================");

        Team winner = armies.get(Team.RED).alive() != 0 ? Team.RED : armies.get(Team.BLUE).alive() != 0 ? Team.BLUE : null;

        System.out.println("WINNER: " + (winner != null ? winner : "DRAW"));
    }

    private Team other(Team onTurn) {
        return onTurn == Team.RED ? Team.BLUE : Team.RED;
    }

}
