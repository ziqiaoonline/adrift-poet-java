package adriftpoet;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates the game's worlds and poem templates.
 *
 * JAVA CONCEPTS YOU'LL LEARN HERE:
 * - Static methods (utility methods that don't need an object)
 * - Object creation with 'new'
 * - Building complex data structures
 *
 * ?? EXPANSION TIP: Add your own worlds and poems here!
 */
public class WorldGenerator {

    /** Create all the worlds in the game */
    public static List<World> createWorlds() {
        List<World> worlds = new ArrayList<>();

        worlds.add(new World(
            "The Ember Drift",
            "A dying star's warmth drifts through clouds of luminous dust.",
            "You detect faint signals... fragments of language, etched into the stellar wind.",
            new String[]{"ember", "silence", "glow", "remnant"},
            3
        ));

        worlds.add(new World(
            "Crystalline Void",
            "Frozen structures float in absolute zero, refracting ancient starlight.",
            "Words shimmer inside the ice, preserved for millennia.",
            new String[]{"fracture", "light", "cold", "memory"},
            5
        ));

        worlds.add(new World(
            "The Murmuring Nebula",
            "A vast cloud of gas that hums with the echoes of a collapsed civilization.",
            "Their last words still ripple through the particles.",
            new String[]{"echo", "wander", "dust", "voice"},
            4
        ));

        worlds.add(new World(
            "Iron Garden",
            "A rogue planet covered in metallic flora that grows toward no sun.",
            "Between the iron petals, you find inscriptions from a lost gardener.",
            new String[]{"bloom", "rust", "hope", "thorn"},
            6
        ));

        worlds.add(new World(
            "The Signal Graveyard",
            "Thousands of derelict satellites orbit a dead moon, still transmitting.",
            "You tune into their frequencies. Each carries a single word, on repeat.",
            new String[]{"signal", "static", "dream", "orbit"},
            5
        ));

        worlds.add(new World(
            "Tide of Glass",
            "An ocean world where the seas have crystallized into translucent waves.",
            "Beneath the frozen surface, words are trapped mid-sentence.",
            new String[]{"tide", "beneath", "shimmer", "breath"},
            7
        ));

        return worlds;
    }

    /** Create all poem templates */
    public static List<PoemTemplate> createPoems() {
        List<PoemTemplate> poems = new ArrayList<>();

        poems.add(new PoemTemplate(
            "First Light",
            new String[]{"ember", "silence", "glow"},
            "    In the {0} of what was,\n" +
            "    a {1} so deep it hums —\n" +
            "    I find my {2}, still burning.",
            25
        ));

        poems.add(new PoemTemplate(
            "Shattered Mirror",
            new String[]{"fracture", "light", "memory"},
            "    Each {0} holds a different {1},\n" +
            "    each shard a stolen {2}\n" +
            "    of someone I used to be.",
            30
        ));

        poems.add(new PoemTemplate(
            "Wanderer's Hymn",
            new String[]{"echo", "wander", "dust"},
            "    I am the {0} of a {1}er,\n" +
            "    scattered into {2},\n" +
            "    carried by nothing, arriving everywhere.",
            25
        ));

        poems.add(new PoemTemplate(
            "Metal Bloom",
            new String[]{"bloom", "rust", "hope"},
            "    Even in {1}, a {0}\n" +
            "    pushes through —\n" +
            "    they call it {2}, I call it defiance.",
            35
        ));

        poems.add(new PoemTemplate(
            "Ghost Frequency",
            new String[]{"signal", "static", "dream"},
            "    Between the {1} and the {0},\n" +
            "    I caught a {2}\n" +
            "    that wasn't mine — but felt like home.",
            30
        ));

        poems.add(new PoemTemplate(
            "Frozen Passage",
            new String[]{"tide", "shimmer", "breath"},
            "    The {0} stopped but still {1}s\n" +
            "    like the last {2}\n" +
            "    of a world that refused to die.",
            35
        ));

        return poems;
    }
}
