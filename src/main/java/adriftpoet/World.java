package adriftpoet;

import java.util.ArrayList;
import java.util.List;

/**
 * A World — a place the robot can drift to.
 *
 * JAVA CONCEPTS YOU'LL LEARN HERE:
 * - Arrays vs ArrayLists
 * - String arrays
 * - Boolean state tracking
 * - The 'this' keyword
 */
public class World {

    private String name;
    private String description;
    private String atmosphere;       // flavor text when you arrive
    private String[] availableWords; // word fragments found here
    private boolean[] wordCollected; // tracks which words have been picked up
    private boolean visited;
    private int travelCost;          // energy to reach this world

    public World(String name, String description, String atmosphere,
                 String[] availableWords, int travelCost) {
        this.name = name;
        this.description = description;
        this.atmosphere = atmosphere;
        this.availableWords = availableWords;
        this.wordCollected = new boolean[availableWords.length]; // all false by default
        this.visited = false;
        this.travelCost = travelCost;
    }

    /** Get words that haven't been collected yet */
    public List<String> getRemainingWords() {
        List<String> remaining = new ArrayList<>();
        for (int i = 0; i < availableWords.length; i++) {
            if (!wordCollected[i]) {
                remaining.add(availableWords[i]);
            }
        }
        return remaining;
    }

    /** Collect a specific word from this world */
    public boolean collectWord(String word) {
        for (int i = 0; i < availableWords.length; i++) {
            if (availableWords[i].equalsIgnoreCase(word) && !wordCollected[i]) {
                wordCollected[i] = true;
                return true;
            }
        }
        return false;
    }

    /** Check if all words have been gathered */
    public boolean isFullyExplored() {
        for (boolean collected : wordCollected) {
            if (!collected) return false;
        }
        return true;
    }

    // === GETTERS ===

    public String getName()        { return name; }
    public String getDescription() { return description; }
    public String getAtmosphere()  { return atmosphere; }
    public int getTravelCost()     { return travelCost; }
    public boolean isVisited()     { return visited; }

    public void markVisited() { this.visited = true; }

    /** Display the world's arrival text */
    public String getArrivalText() {
        return String.format(
            "\n  ✦ ═══════════════════════════════════ ✦\n" +
            "    %s\n" +
            "  ✦ ═══════════════════════════════════ ✦\n\n" +
            "    %s\n\n" +
            "    %s\n",
            name.toUpperCase(), description, atmosphere
        );
    }
}
