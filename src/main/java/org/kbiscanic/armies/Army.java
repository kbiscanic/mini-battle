package org.kbiscanic.armies;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Army {

    private List<Character> characters = Lists.newArrayList();

    private Team team;

    public Army(Team team, int numSoldiers, int skillPoints) {
        this.team = team;

        for (int i = 1; i <= numSoldiers; i++) {
            characters.add(new Character(team, Integer.toString(i), skillPoints));
        }
    }

    public List<Character> determineInitiative() {
        System.out.println("Team " + team + " rolls for initiative...");

        PriorityQueue<Character> initiative = new PriorityQueue<>();

        return characters.stream().map(c -> new CharacterValue(c, c.getInitiative())).sorted().map(cv -> cv.character).collect(Collectors.toList());
    }

    public List<Character> determineThreat() {
        System.out.println("Team " + team + " rolls for threat...");
        PriorityQueue<Character> initiative = new PriorityQueue<>();

        return characters.stream().map(c -> new CharacterValue(c, c.getThreat())).sorted().map(cv -> cv.character).collect(Collectors.toList());
    }

    public boolean isAlive(Character character) {
        return characters.contains(character);
    }

    public int alive() {
        return characters.size();
    }

    public void kill(Character character) {
        characters.remove(character);
    }

    @Override
    public String toString() {
        return "Army " + team + " {" +
                "characters=\n " + characters +
                '}';
    }

    private class CharacterValue implements Comparable<CharacterValue> {
        Character character;
        int value = 0;

        public CharacterValue(Character character, int value) {
            this.character = character;
            this.value = value;
        }

        @Override
        public int compareTo(@Nonnull CharacterValue o) {
            return Integer.compare(o.value, value);
        }

        @Override
        public String toString() {
            return "CharacterValue{" +
                    "character=" + character.getName() +
                    ", value=" + value +
                    '}';
        }
    }
}
