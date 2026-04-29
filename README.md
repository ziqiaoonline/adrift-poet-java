# 🤖 Adrift Poet (Java)

**Year 4721. Deep space. No signal. A robot drifts, core nearly depleted.**

A text-based word puzzle game built to learn Java through narrative design. Collect word fragments across alien worlds, assemble them into collage poems, and recharge your core to survive.

🎮 **[Play the web version →](https://ziqiaoonline.github.io/adrift-poet.html)**

---

## How to Run

```bash
# Compile
javac -d out src/main/java/adriftpoet/*.java

# Play
java -cp out adriftpoet.Main
```

Requires Java 17+.

## Commands

| Command | Action | Energy Cost |
|---|---|---|
| `scan` | Detect nearby worlds | -1 |
| `travel` | Journey to a world | varies |
| `explore` | Search current world for words | -2 |
| `words` | View collected fragments | free |
| `poems` | View poem blueprints | free |
| `write` | Assemble a poem → recharge | free |
| `status` | Check robot vitals | free |
| `help` | Show commands | free |
| `quit` | Power down | — |

## Project Structure

```
src/main/java/adriftpoet/
├── Main.java            ← Entry point
├── GameEngine.java      ← Game loop, commands, UI
├── Robot.java           ← Player (energy, inventory)
├── World.java           ← Locations with discoverable words
├── PoemTemplate.java    ← Poem blueprints with word requirements
└── WorldGenerator.java  ← Creates all worlds and poems
```

## Java Concepts Covered

Each class teaches specific Java fundamentals:

- **Robot.java** — Fields, constructors, encapsulation, ArrayList
- **World.java** — Arrays vs ArrayLists, boolean tracking, `this` keyword
- **PoemTemplate.java** — `Arrays.asList()`, `containsAll()`, `String.format()`
- **WorldGenerator.java** — Static methods, object creation, complex data structures
- **GameEngine.java** — Scanner input, while loops, switch statements, method decomposition

## The Story → Architecture Connection

The game concept contained the entire object model:

| Story Element | Java Class |
|---|---|
| A robot with energy and inventory | `Robot` |
| Alien worlds with hidden words | `World` |
| Poems that require specific words | `PoemTemplate` |
| The universe of worlds and poems | `WorldGenerator` |
| The experience of playing | `GameEngine` |

The narrative was the architecture.

---

Built by [Qiao](https://ziqiaoonline.github.io) — learning Java by building games.
