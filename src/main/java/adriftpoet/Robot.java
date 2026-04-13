package adriftpoet;

import java.util.ArrayList;
import java.util.List;

/**
 * The Robot — your character.
 *
 * JAVA CONCEPTS YOU'LL LEARN HERE:
 * - Fields (variables that belong to an object)
 * - Constructor (how objects are created)
 * - Methods (actions the robot can perform)
 * - Encapsulation (private fields + public methods)
 * - ArrayList (a growable list)
 */
public class Robot {

    // === FIELDS (the robot's state) ===
    private String name;
    private int energy;            // 0 = shutdown, 100 = full charge
    private int maxEnergy;
    private int worldsVisited;
    private List<String> collectedWords;   // word fragments picked up
    private List<String> completedPoems;   // poems that recharged the core

    // === CONSTRUCTOR (how a Robot is born) ===
    public Robot(String name) {
        this.name = name;
        this.energy = 15;           // barely alive — just enough to start
        this.maxEnergy = 100;
        this.worldsVisited = 0;
        this.collectedWords = new ArrayList<>();
        this.completedPoems = new ArrayList<>();
    }

    // === ENERGY METHODS ===

    /** Drain energy when the robot takes an action. Returns false if dead. */
    public boolean drainEnergy(int amount) {
        this.energy -= amount;
        if (this.energy < 0) {
            this.energy = 0;
        }
        return this.energy > 0;
    }

    /** Recharge the core when a poem is completed */
    public void recharge(int amount) {
        this.energy += amount;
        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
        }
    }

    /** Is the robot still functioning? */
    public boolean isAlive() {
        return this.energy > 0;
    }

    /** Get an energy bar visualization */
    public String getEnergyBar() {
        int filled = (int) ((this.energy / (double) this.maxEnergy) * 20);
        int empty = 20 - filled;

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < filled; i++) {
            bar.append("\u2588");
        }
        for (int i = 0; i < empty; i++) {
            bar.append("\u2591");
        }
        bar.append("] ").append(this.energy).append("/").append(this.maxEnergy);
        return bar.toString();
    }

    // === WORD COLLECTION ===

    public void collectWord(String word) {
        if (!collectedWords.contains(word)) {
            collectedWords.add(word);
        }
    }

    public boolean hasWord(String word) {
        return collectedWords.contains(word);
    }

    public void removeWords(List<String> words) {
        collectedWords.removeAll(words);
    }

    // === POEM TRACKING ===

    public void addCompletedPoem(String poem) {
        completedPoems.add(poem);
    }

    public void visitWorld() {
        worldsVisited++;
    }

    // === GETTERS ===

    public String getName()                { return name; }
    public int getEnergy()                 { return energy; }
    public int getWorldsVisited()          { return worldsVisited; }
    public List<String> getCollectedWords(){ return new ArrayList<>(collectedWords); }
    public List<String> getCompletedPoems(){ return new ArrayList<>(completedPoems); }

    // === STATUS DISPLAY ===

    public String getStatus() {
        String warning = "";
        if (energy <= 10) {
            warning = "  \u26a0 CRITICAL \u2014 find words, write a poem!";
        } else if (energy <= 30) {
            warning = "  \u26a1 Low power";
        }

        return String.format(
            "\n  \u250c\u2500\u2500\u2500 %s \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510\n" +
            "  \u2502 Energy: %-28s\u2502\n" +
            "  \u2502 Worlds visited: %-20d\u2502\n" +
            "  \u2502 Words collected: %-19d\u2502\n" +
            "  \u2502 Poems completed: %-19d\u2502\n" +
            "  \u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518%s\n",
            name, getEnergyBar(), worldsVisited,
            collectedWords.size(), completedPoems.size(), warning
        );
    }
}
