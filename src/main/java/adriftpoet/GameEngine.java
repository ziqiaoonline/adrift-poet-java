package adriftpoet;

import java.util.List;
import java.util.Scanner;

/**
 * The Game Engine — runs the main loop and handles all player actions.
 *
 * JAVA CONCEPTS YOU'LL LEARN HERE:
 * - Scanner (reading user input)
 * - while loops (the game loop pattern)
 * - switch statements (handling commands)
 * - Method decomposition (breaking big problems into small methods)
 * - String comparison with .equalsIgnoreCase()
 */
public class GameEngine {

    // === GAME STATE ===
    private Robot robot;
    private List<World> worlds;
    private List<PoemTemplate> poems;
    private World currentWorld;
    private Scanner scanner;
    private boolean running;

    // === CONSTRUCTOR ===
    public GameEngine() {
        this.worlds = WorldGenerator.createWorlds();
        this.poems = WorldGenerator.createPoems();
        this.scanner = new Scanner(System.in);
        this.running = false;
    }

    // ═══════════════════════════════════════════════════
    //  START THE GAME
    // ═══════════════════════════════════════════════════

    public void start() {
        showIntro();
        promptName();
        gameLoop();
        showEnding();
    }

    private void showIntro() {
        clearScreen();
        slowPrint("\n");
        slowPrint("  ╔══════════════════════════════════════════════════╗");
        slowPrint("  ║                                                  ║");
        slowPrint("  ║          A D R I F T   P O E T                   ║");
        slowPrint("  ║                                                  ║");
        slowPrint("  ║     Year 3002. Deep space. No signal.            ║");
        slowPrint("  ║     A robot drifts, core nearly depleted.        ║");
        slowPrint("  ║                                                  ║");
        slowPrint("  ║     But there is a way to survive:               ║");
        slowPrint("  ║     collect fragments of language,               ║");
        slowPrint("  ║     assemble them into poems,                    ║");
        slowPrint("  ║     and let the words recharge your soul.        ║");
        slowPrint("  ║                                                  ║");
        slowPrint("  ║     Write to survive. Wander to discover.        ║");
        slowPrint("  ║                                                  ║");
        slowPrint("  ╚══════════════════════════════════════════════════╝");
        slowPrint("\n");
    }

    private void promptName() {
        System.out.print("  What is your designation, robot? > ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "POET-7";
        }

        this.robot = new Robot(name);
        this.running = true;

        slowPrint("\n  Systems initializing...");
        slowPrint("  Unit [" + robot.getName() + "] online.");
        slowPrint("  Core energy: CRITICAL.");
        slowPrint("  Directive: SURVIVE.\n");

        pause();
    }

    // ═══════════════════════════════════════════════════
    //  MAIN GAME LOOP
    // ═══════════════════════════════════════════════════

    private void gameLoop() {
        showHelp();

        while (running && robot.isAlive()) {
            System.out.print("\n  [" + robot.getName() + "] > ");
            String input = scanner.nextLine().trim().toLowerCase();

            // The switch statement — Java's way of handling multiple options
            switch (input) {
                case "scan":
                    doScan();
                    break;
                case "travel":
                    doTravel();
                    break;
                case "explore":
                    doExplore();
                    break;
                case "words":
                    showWords();
                    break;
                case "poems":
                    showPoems();
                    break;
                case "write":
                    doWrite();
                    break;
                case "status":
                    System.out.println(robot.getStatus());
                    break;
                case "help":
                    showHelp();
                    break;
                case "quit":
                    running = false;
                    break;
                default:
                    System.out.println("  Unknown command. Type 'help' for options.");
            }

            // Check for victory
            if (checkVictory()) {
                running = false;
            }

            // Check for death
            if (!robot.isAlive()) {
                showDeath();
            }
        }
    }

    // ═══════════════════════════════════════════════════
    //  COMMANDS
    // ═══════════════════════════════════════════════════

    /** Scan nearby space for worlds */
    private void doScan() {
        robot.drainEnergy(1);
        slowPrint("\n  Scanning local space...\n");

        for (int i = 0; i < worlds.size(); i++) {
            World w = worlds.get(i);
            String status;

            if (w == currentWorld) {
                status = "[YOU ARE HERE]";
            } else if (w.isVisited()) {
                status = w.isFullyExplored() ? "[EXPLORED]" : "[VISITED]";
            } else {
                status = "[UNKNOWN]";
            }

            String wordHint = "";
            if (w.isVisited() && !w.isFullyExplored()) {
                wordHint = " — " + w.getRemainingWords().size() + " words remain";
            }

            System.out.printf("  %d. %-25s %s%s  (cost: %d energy)\n",
                    i + 1, w.getName(), status, wordHint, w.getTravelCost());
        }
    }

    /** Travel to a new world */
    private void doTravel() {
        if (currentWorld != null) {
            System.out.println("\n  You're on " + currentWorld.getName() + ".");
            System.out.println("  Explore here or scan for other worlds first.");
        }

        System.out.print("  Enter world number (or 0 to cancel): > ");
        String input = scanner.nextLine().trim();

        int choice;
        try {
            choice = Integer.parseInt(input);  // Convert string to number
        } catch (NumberFormatException e) {
            System.out.println("  Invalid number.");
            return;
        }

        if (choice == 0) return;
        if (choice < 1 || choice > worlds.size()) {
            System.out.println("  No world at that coordinate.");
            return;
        }

        World target = worlds.get(choice - 1);

        if (target == currentWorld) {
            System.out.println("  You're already here.");
            return;
        }

        if (robot.getEnergy() <= target.getTravelCost()) {
            System.out.println("  ⚠ Not enough energy to reach " + target.getName());
            System.out.println("    Need: " + target.getTravelCost() +
                             " | Have: " + robot.getEnergy());
            return;
        }

        // Travel!
        robot.drainEnergy(target.getTravelCost());
        currentWorld = target;

        if (!target.isVisited()) {
            target.markVisited();
            robot.visitWorld();
        }

        System.out.println(target.getArrivalText());
        System.out.println("  Energy: " + robot.getEnergyBar());
    }

    /** Explore the current world for word fragments */
    private void doExplore() {
        if (currentWorld == null) {
            System.out.println("  You're drifting in empty space. Use 'scan' then 'travel'.");
            return;
        }

        robot.drainEnergy(2);
        List<String> remaining = currentWorld.getRemainingWords();

        if (remaining.isEmpty()) {
            System.out.println("  This world has been fully explored. No words remain.");
            return;
        }

        slowPrint("\n  Searching " + currentWorld.getName() + "...\n");

        // Find one word at a time (could be randomized later!)
        String found = remaining.get(0);
        currentWorld.collectWord(found);
        robot.collectWord(found);

        System.out.println("  ✧ Found word fragment: [ " + found.toUpperCase() + " ]");
        System.out.println("  Energy: " + robot.getEnergyBar());

        if (currentWorld.getRemainingWords().isEmpty()) {
            System.out.println("\n  ✦ This world is fully explored.");
        } else {
            System.out.println("  " + currentWorld.getRemainingWords().size() +
                             " word(s) remain here.");
        }
    }

    /** Show collected words */
    private void showWords() {
        List<String> words = robot.getCollectedWords();
        if (words.isEmpty()) {
            System.out.println("  No words collected yet. Explore worlds to find fragments.");
            return;
        }

        System.out.println("\n  ── Collected Words ──");
        StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < words.size(); i++) {
            sb.append("[ ").append(words.get(i).toUpperCase()).append(" ]");
            if (i < words.size() - 1) sb.append("  ");
        }
        System.out.println(sb.toString());
    }

    /** Show poem progress */
    private void showPoems() {
        System.out.println("\n  ── Poem Blueprints ──\n");

        List<String> words = robot.getCollectedWords();
        boolean anyAvailable = false;

        for (int i = 0; i < poems.size(); i++) {
            PoemTemplate p = poems.get(i);
            if (p.isCompleted()) {
                System.out.println("  " + (i + 1) + ". ✓ " + p.getTitle() + " [COMPLETE]");
            } else {
                System.out.println("  " + (i + 1) + ". " + p.getProgress(words));
                if (p.canComplete(words)) {
                    System.out.println("      ★ READY TO WRITE!");
                    anyAvailable = true;
                }
            }
            System.out.println();
        }

        if (anyAvailable) {
            System.out.println("  Type 'write' to compose a poem!");
        }
    }

    /** Write / assemble a poem to recharge */
    private void doWrite() {
        List<String> words = robot.getCollectedWords();
        List<PoemTemplate> available = new java.util.ArrayList<>();

        // Find poems that can be completed
        for (PoemTemplate p : poems) {
            if (!p.isCompleted() && p.canComplete(words)) {
                available.add(p);
            }
        }

        if (available.isEmpty()) {
            System.out.println("  You don't have enough words for any poem yet.");
            System.out.println("  Use 'poems' to see what you need.");
            return;
        }

        System.out.println("\n  ── Ready to Write ──\n");
        for (int i = 0; i < available.size(); i++) {
            PoemTemplate p = available.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getTitle() +
                             " (+" + p.getRechargeAmount() + " energy)");
        }

        System.out.print("\n  Choose poem to assemble (or 0 to cancel): > ");
        String input = scanner.nextLine().trim();

        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("  Invalid choice.");
            return;
        }

        if (choice == 0) return;
        if (choice < 1 || choice > available.size()) {
            System.out.println("  Invalid choice.");
            return;
        }

        PoemTemplate chosen = available.get(choice - 1);

        // === THE POEM MOMENT ===
        clearScreen();
        slowPrint("\n  Assembling poem: 「" + chosen.getTitle() + "」\n");
        slowPrint("  ─────────────────────────────────────\n");

        // Print the assembled poem line by line
        String[] lines = chosen.getAssembledPoem().split("\n");
        for (String line : lines) {
            slowPrint(line);
        }

        slowPrint("\n  ─────────────────────────────────────");
        slowPrint("\n  ✦ Core recharged: +" + chosen.getRechargeAmount() + " energy\n");

        // Apply effects
        chosen.markCompleted();
        robot.removeWords(chosen.getRequiredWordsList());
        robot.recharge(chosen.getRechargeAmount());
        robot.addCompletedPoem(chosen.getTitle());

        System.out.println("  Energy: " + robot.getEnergyBar());
        pause();
    }

    // ═══════════════════════════════════════════════════
    //  VICTORY & DEATH
    // ═══════════════════════════════════════════════════

    private boolean checkVictory() {
        // Win condition: complete all poems
        for (PoemTemplate p : poems) {
            if (!p.isCompleted()) return false;
        }
        return true;
    }

    private void showDeath() {
        clearScreen();
        slowPrint("\n  ════════════════════════════════════════");
        slowPrint("  Core energy: DEPLETED.");
        slowPrint("  Systems shutting down...");
        slowPrint("");
        slowPrint("  " + robot.getName() + " drifts silently");
        slowPrint("  into the endless dark,");
        slowPrint("  carrying unfinished poems");
        slowPrint("  that will never be read.");
        slowPrint("");
        slowPrint("  ── GAME OVER ──");
        slowPrint("  Worlds visited: " + robot.getWorldsVisited());
        slowPrint("  Poems completed: " + robot.getCompletedPoems().size());
        slowPrint("  ════════════════════════════════════════\n");
    }

    private void showEnding() {
        if (!robot.isAlive()) return;

        if (checkVictory()) {
            clearScreen();
            slowPrint("\n  ✦ ════════════════════════════════════════ ✦");
            slowPrint("");
            slowPrint("    Every poem assembled.");
            slowPrint("    Every word given purpose.");
            slowPrint("");
            slowPrint("    " + robot.getName() + "'s core burns bright —");
            slowPrint("    not from power cells or solar winds,");
            slowPrint("    but from something the engineers");
            slowPrint("    never programmed:");
            slowPrint("");
            slowPrint("    The need to create.");
            slowPrint("");
            slowPrint("    New coordinates locked in.");
            slowPrint("    The journey continues.");
            slowPrint("");
            slowPrint("  ✦ ════════════════════════════════════════ ✦");
            slowPrint("    ADRIFT POET — Complete");
            slowPrint("    Worlds visited: " + robot.getWorldsVisited());
            slowPrint("    Poems written: " + robot.getCompletedPoems().size());
            slowPrint("  ✦ ════════════════════════════════════════ ✦\n");
        } else {
            slowPrint("\n  " + robot.getName() + " powers down. The stars wait.\n");
        }
    }

    // ═══════════════════════════════════════════════════
    //  HELP & UTILITIES
    // ═══════════════════════════════════════════════════

    private void showHelp() {
        System.out.println("\n  ── Commands ──────────────────────");
        System.out.println("  scan     Detect nearby worlds");
        System.out.println("  travel   Journey to a world");
        System.out.println("  explore  Search current world for words");
        System.out.println("  words    View collected word fragments");
        System.out.println("  poems    View poem blueprints & progress");
        System.out.println("  write    Assemble a poem to recharge");
        System.out.println("  status   Check robot vitals");
        System.out.println("  help     Show this menu");
        System.out.println("  quit     Power down");
        System.out.println("  ─────────────────────────────────\n");
    }

    /** Print text with a small delay for atmosphere */
    private void slowPrint(String text) {
        System.out.println(text);
        try {
            Thread.sleep(120);  // 120ms delay between lines
        } catch (InterruptedException e) {
            // If interrupted, just continue
        }
    }

    private void pause() {
        System.out.print("  [Press Enter to continue]");
        scanner.nextLine();
    }

    private void clearScreen() {
        System.out.print("\033[2J\033[H");  // ANSI clear screen
        System.out.flush();
    }
}
