package adriftpoet;

import java.util.Arrays;
import java.util.List;

/**
 * A Poem Template — a pattern for a collage poem.
 *
 * The robot collects word fragments across worlds,
 * then assembles them into poems to recharge its core.
 *
 * JAVA CONCEPTS YOU'LL LEARN HERE:
 * - Arrays.asList() — converting arrays to lists
 * - List.containsAll() — checking if a list has all needed items
 * - String.format() — formatted text output
 */
public class PoemTemplate {

    private String title;
    private String[] requiredWords;   // words needed to complete this poem
    private String poemText;          // the poem with %s placeholders for words
    private int rechargeAmount;       // energy gained on completion
    private boolean completed;

    public PoemTemplate(String title, String[] requiredWords,
                        String poemText, int rechargeAmount) {
        this.title = title;
        this.requiredWords = requiredWords;
        this.poemText = poemText;
        this.rechargeAmount = rechargeAmount;
        this.completed = false;
    }

    /** Check if the robot has all the words needed */
    public boolean canComplete(List<String> collectedWords) {
        // Convert our array to a list so we can use containsAll
        List<String> needed = Arrays.asList(requiredWords);

        // Check: does the player's collection contain every word we need?
        for (String word : needed) {
            boolean found = false;
            for (String collected : collectedWords) {
                if (collected.equalsIgnoreCase(word)) {
                    found = true;
                    break;  // found this word, move to next needed word
                }
            }
            if (!found) return false;
        }
        return true;
    }

    /** Get the assembled poem text */
    public String getAssembledPoem() {
        // The poemText uses {0}, {1}, etc. as placeholders
        String result = poemText;
        for (int i = 0; i < requiredWords.length; i++) {
            result = result.replace("{" + i + "}", requiredWords[i].toUpperCase());
        }
        return result;
    }

    /** Show what words are still needed */
    public String getProgress(List<String> collectedWords) {
        StringBuilder sb = new StringBuilder();
        sb.append("  「").append(title).append("」\n");
        sb.append("   Words needed: ");

        for (int i = 0; i < requiredWords.length; i++) {
            boolean has = false;
            for (String w : collectedWords) {
                if (w.equalsIgnoreCase(requiredWords[i])) {
                    has = true;
                    break;
                }
            }

            if (has) {
                sb.append("✓ ").append(requiredWords[i]);
            } else {
                sb.append("? ").append("_".repeat(requiredWords[i].length()));
            }

            if (i < requiredWords.length - 1) sb.append("  ");
        }

        sb.append("\n   Recharge: +").append(rechargeAmount).append(" energy");
        return sb.toString();
    }

    // === GETTERS ===

    public String getTitle()             { return title; }
    public String[] getRequiredWords()   { return requiredWords; }
    public int getRechargeAmount()       { return rechargeAmount; }
    public boolean isCompleted()         { return completed; }

    public void markCompleted() { this.completed = true; }

    public List<String> getRequiredWordsList() {
        return Arrays.asList(requiredWords);
    }
}
