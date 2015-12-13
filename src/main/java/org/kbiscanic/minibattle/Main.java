package org.kbiscanic.minibattle;

import org.kbiscanic.minibattle.armies.Army;
import org.kbiscanic.minibattle.armies.Team;

public class Main {

    public static void main(String[] args) {
        int n = 0;
        if (args.length != 1) {
            System.err.println("Wrong number of arguments. Expected: 1");
            System.exit(-1);
        } else {
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException nex) {
                System.err.println("Argument must be a number.");
                System.exit(-1);
            }
        }

        if (n < 1) {
            System.err.println("N must be > 0");
            System.exit(-1);
        }

        Battle battle = new Battle(new Army(Team.RED, n, 5), new Army(Team.BLUE, n, 5));

        battle.run();
    }
}
