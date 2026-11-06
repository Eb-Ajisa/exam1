package game;

public class CombatRules {

    /**
     * Calculates the new hit points based on the current hit points, maximum hit points, and a change amount, ensuring it does not exceed the maximum.
     * @param currentHp the current hit points of the character
     * @param maxHp the maximum hit points the character can have
     * @param amount the amount to adjust the current hit points by
     * @return the new hit points value after adjustment, capped at maxHp
     */
    public static int calculateNewHp(int currentHp, int maxHp, int amount) {
        int newHp = currentHp + amount;

        if (newHp > maxHp)
            return maxHp;

        return newHp;
    }

    /**
     * Determines if a character is dead based on their hit points.
     * @param hp the current hit points of the character
     * @return true if hp is less than 1, indicating death; otherwise false
     */
    public static boolean isDead(int hp) {
        return hp < 1;
    }
}
