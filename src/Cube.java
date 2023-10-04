
//Imports
import java.util.ArrayList;
import java.util.List;

public class Cube {
    // Default parameters
    private int size = 3;

    // Cube
    private String[][][] block; // Cube

    // Constructor
    public Cube() {
        // Colors
        List<String> colors = new ArrayList<>();
        // White
        for (int i = 0; i < size * size; i++) {
            colors.add("W");
        }
        // Green
        for (int i = 0; i < size * size; i++) {
            colors.add("G");
        }
        // Red
        for (int i = 0; i < size * size; i++) {
            colors.add("R");
        }
        // Blue
        for (int i = 0; i < size * size; i++) {
            colors.add("B");
        }
        // Orange
        for (int i = 0; i < size * size; i++) {
            colors.add("O");
        }
        // Yellow
        for (int i = 0; i < size * size; i++) {
            colors.add("Y");
        }

        // Create cube
        this.block = new String[6][size][size];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {
                    this.block[i][j][z] = colors.remove(0);
                }
            }
        }

        // Randomize cube
        int randMoves = 1 + (int) (Math.random() * (6)); // Number of moves
        for (int i = 0; i < randMoves; i++) {

        }
    }

    // Copy contrsuctor
    public Cube(Cube copy) {
        this.setBlock(copy.getBlock());
    }

    // Getters
    public String[][][] getBlock() {
        return this.block;
    }

    // Setters
    public void setBlock(String[][][] block) {
        // Create new cube
        this.block = new String[6][size][size];

        // Hard copy the given cube
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {
                    this.block[i][j][z] = block[i][j][z];
                }
            }
        }
    }

    // Print cube
    public void print() {
        for (int i = 0; i < 6; i++) {
            System.out.printf("%s %d \n", "Side", (i + 1));
            System.out.printf("%s \n", "-------");

            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {
                    System.out.printf("%s", "|");
                    System.out.printf("%s", block[i][j][z]);
                }

                System.out.printf("%s \n", "|");
            }

            System.out.printf("%s \n", "-------");
        }
    }

    // Check if cube is in final state
    public boolean isFinal() {
        int counter = 6;

        // For every side of the cube
        for (int i = 0; i < 6; i++) {
            String tester = this.block[i][0][0];
            outLoop: for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {
                    // If a piece has different color from the side's 1st piece
                    if (this.block[i][j][z] != tester) {
                        // This side is not in final state
                        counter = counter - 1;
                        break outLoop;
                    }
                }
            }
        }

        // Compare cube's sides in final state
        if (counter == 6) {
            return true;
        }
        return false;
    }
}