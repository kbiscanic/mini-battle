package org.kbiscanic.minibattle;

import org.kbiscanic.minibattle.armies.Army;
import org.kbiscanic.minibattle.armies.Team;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Wrong number of arguments. Expected: 1");
            System.exit(-1);
        }

        Battle battle = new Battle(new Army(Team.RED, Integer.parseInt(args[0]), 5), new Army(Team.BLUE, Integer.parseInt(args[0]), 5));

        battle.run();
    }
}
