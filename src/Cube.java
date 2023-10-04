
//Imports
import java.util.ArrayList;
import java.util.List;

public class Cube {
    // Default parameters
    private int size = 3;

    // Cube
    private String[][][] block; // Cube

    // Constructor
    public Cube(int occasion) {
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
        String moves = ""; // Moves made to randomize the cube

        if (occasion == 0) {
            int randMoves = 1 + (int) (Math.random() * (6)); // Number of randomizing moves

            int move;
            for (int i = 0; i < randMoves; i++) {
                // Choose 1 of the 8 available moves
                move = (int) (Math.random() * (8));

                switch (move) {
                    case 0:
                        this.moveU(true);
                        moves = moves + "U ";
                        break;
                    case 1:
                        this.moveE(true);
                        moves = moves + "E ";
                        break;
                    case 2:
                        this.moveD(true);
                        moves = moves + "D ";
                        break;
                    case 3:
                        this.moveF(true);
                        moves = moves + "F ";
                        break;
                    case 4:
                        this.moveS(true);
                        moves = moves + "S ";
                        break;
                    case 5:
                        this.moveB(true);
                        moves = moves + "B ";
                        break;
                    case 6:
                        this.moveR(true);
                        moves = moves + "R ";
                        break;
                    case 7:
                        this.moveM(true);
                        moves = moves + "M ";
                        break;
                    case 8:
                        this.moveL(true);
                        moves = moves + "L ";
                        break;
                }
            }
        } else if (occasion == 1) {
            // Testing cube 1
            this.moveM(true);
            this.moveU(true);
            this.moveS(false);
            this.moveD(true);

            moves = "M U S' D";
        } else if (occasion == 2) {
            // Testing cube 2
            this.moveU(false);
            this.moveE(true);
            this.moveS(false);

            moves = "U' E S'";
        } else if (occasion == 3) {
            // Testing cube 3
            this.moveF(true);
            this.moveE(true);
            this.moveU(true);
            this.moveM(false);
            this.moveR(true);

            moves = "F E U M' R";
        } else if (occasion == 4) {
            // Testing cube 4
            this.moveF(false);
            this.moveD(true);
            this.moveS(true);
            this.moveF(true);

            moves = "F' D S F";
        } else if (occasion == 5) {
            // Testing cube 5
            this.moveU(false);
            this.moveE(true);
            this.moveS(false);
            this.moveF(false);
            this.moveD(true);

            moves = "U' E S' F' D";
        } else {
            // Testing cube 6
            this.moveU(false);
            this.moveE(true);
            this.moveS(false);
            this.moveF(false);
            this.moveD(true);
            this.moveF(true);

            moves = "U' E S' F' D F";
        }

        // Print the randomizing moves
        System.out.println("Moves made to randomize: " + moves);
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

    // Moves
    private void moveU(boolean clockwise) {
        if (clockwise) {
            // this.addMove("U");

            // Rotate the row
            String[] tempArray = new String[size];

            for (int z = 0; z < size; z++) {
                tempArray[z] = this.block[0][0][z];
            }

            for (int i = 0; i < size; i++) {
                for (int z = 0; z < size; z++) {
                    this.block[i][0][z] = this.block[i + 1][0][z];
                }
            }

            for (int z = 0; z < size; z++) {
                this.block[3][0][z] = tempArray[z];
            }

            // Rotate the dependent side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[4][i][j];
                    this.block[4][i][j] = this.block[4][j][size - i - 1];
                    this.block[4][j][size - i - 1] = this.block[4][size - i - 1][size - j - 1];
                    this.block[4][size - i - 1][size - j - 1] = this.block[4][size - j - 1][i];
                    this.block[4][size - j - 1][i] = temp;
                }
            }
        } else {
            // this.addMove("U'");

            // Rotate the row
            String[] tempArray = new String[size];

            for (int z = 0; z < size; z++) {
                tempArray[z] = this.block[3][0][z];
            }

            for (int i = size; i > 0; i--) {
                for (int z = 0; z < size; z++) {
                    this.block[i][0][z] = this.block[i - 1][0][z];
                }
            }

            for (int z = 0; z < size; z++) {
                this.block[0][0][z] = tempArray[z];
            }

            // Rotate the dependent side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[4][i][j];
                    this.block[4][i][j] = this.block[4][size - j - 1][i];
                    this.block[4][size - j - 1][i] = this.block[4][size - i - 1][size - j - 1];
                    this.block[4][size - i - 1][size - j - 1] = this.block[4][j][size - i - 1];
                    this.block[4][j][size - i - 1] = temp;
                }
            }
        }
    }

    private void moveE(boolean clockwise) {
        if (clockwise) {
            // this.addMove("E");

            // Rotate the row
            String[] tempArray = new String[size];

            for (int z = 0; z < size; z++) {
                tempArray[z] = this.block[3][1][z];
            }

            for (int i = size; i > 0; i--) {
                for (int z = 0; z < size; z++) {
                    this.block[i][1][z] = this.block[i - 1][1][z];
                }
            }

            for (int z = 0; z < size; z++) {
                this.block[0][1][z] = tempArray[z];
            }
        } else {
            // this.addMove("E'");

            // Rotate the row
            String[] tempArray = new String[size];

            for (int z = 0; z < size; z++) {
                tempArray[z] = this.block[0][1][z];
            }

            for (int i = 0; i < size; i++) {
                for (int z = 0; z < size; z++) {
                    this.block[i][1][z] = this.block[i + 1][1][z];
                }
            }

            for (int z = 0; z < size; z++) {
                this.block[3][1][z] = tempArray[z];
            }
        }
    }

    private void moveD(boolean clockwise) {
        if (clockwise) {
            // this.addMove("D");

            // Rotate the row
            String[] tempArray = new String[size];

            for (int z = 0; z < size; z++) {
                tempArray[z] = this.block[3][2][z];
            }

            for (int i = size; i > 0; i--) {
                for (int z = 0; z < size; z++) {
                    this.block[i][2][z] = this.block[i - 1][2][z];
                }
            }

            for (int z = 0; z < size; z++) {
                this.block[0][2][z] = tempArray[z];
            }

            // Rotate the dependent side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[5][i][j];
                    this.block[5][i][j] = this.block[5][j][size - i - 1];
                    this.block[5][j][size - i - 1] = this.block[5][size - i - 1][size - j - 1];
                    this.block[5][size - i - 1][size - j - 1] = this.block[5][size - j - 1][i];
                    this.block[5][size - j - 1][i] = temp;
                }
            }
        } else {
            // this.addMove("D'");

            // Rotate the row
            String[] tempArray = new String[size];

            for (int z = 0; z < size; z++) {
                tempArray[z] = this.block[0][2][z];
            }

            for (int i = 0; i < size; i++) {
                for (int z = 0; z < size; z++) {
                    this.block[i][2][z] = this.block[i + 1][2][z];
                }
            }

            for (int z = 0; z < size; z++) {
                this.block[3][2][z] = tempArray[z];
            }

            // Rotate the dependent side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[5][i][j];
                    this.block[5][i][j] = this.block[5][size - j - 1][i];
                    this.block[5][size - j - 1][i] = this.block[5][size - i - 1][size - j - 1];
                    this.block[5][size - i - 1][size - j - 1] = this.block[5][j][size - i - 1];
                    this.block[5][j][size - i - 1] = temp;
                }
            }
        }
    }

    private void moveR(boolean clockwise) {
        if (clockwise) {
            // this.addMove("R");

            // Rotate the column
            String[] tempArray = new String[size];

            for (int i = 0; i < size; i++) {
                tempArray[i] = this.block[2][2 - i][0];
            }

            this.block[2][0][0] = this.block[4][2][2];
            this.block[2][1][0] = this.block[4][1][2];
            this.block[2][2][0] = this.block[4][0][2];

            int[] nums = { 4, 0, 5 };
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < size; j++) {
                    this.block[nums[i]][j][2] = this.block[nums[i + 1]][j][2];
                }
            }

            for (int i = 0; i < size; i++) {
                this.block[5][i][2] = tempArray[i];
            }

            // Rotate the dependent side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[1][i][j];
                    this.block[1][i][j] = this.block[1][size - j - 1][i];
                    this.block[1][size - j - 1][i] = this.block[1][size - i - 1][size - j - 1];
                    this.block[1][size - i - 1][size - j - 1] = this.block[1][j][size - i - 1];
                    this.block[1][j][size - i - 1] = temp;
                }
            }
        } else {
            // this.addMove("R'");
            this.moveR(true);
            this.moveR(true);
            this.moveR(true);
        }
    }

    private void moveM(boolean clockwise) {
        if (clockwise) {
            // this.addMove("M");

            // Rotate the column
            String[] tempArray = new String[size];

            for (int i = 0; i < size; i++) {
                tempArray[i] = this.block[2][2 - i][1];
            }

            this.block[2][0][1] = this.block[5][2][1];
            this.block[2][1][1] = this.block[5][1][1];
            this.block[2][2][1] = this.block[5][0][1];

            int[] nums = { 5, 0, 4 };
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < size; j++) {
                    this.block[nums[i]][j][1] = this.block[nums[i + 1]][j][1];
                }
            }

            for (int i = 0; i < size; i++) {
                this.block[4][i][1] = tempArray[i];
            }
        } else {
            // this.addMove("M'");
            this.moveM(true);
            this.moveM(true);
            this.moveM(true);
        }
    }

    private void moveL(boolean clockwise) {
        if (clockwise) {
            // this.addMove("L");

            // Rotate the column
            String[] tempArray = new String[size];

            for (int i = 0; i < size; i++) {
                tempArray[i] = this.block[2][2 - i][2];
            }

            this.block[2][0][2] = this.block[5][2][0];
            this.block[2][1][2] = this.block[5][1][0];
            this.block[2][2][2] = this.block[5][0][0];

            int[] nums = { 5, 0, 4 };
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < size; j++) {
                    this.block[nums[i]][j][0] = this.block[nums[i + 1]][j][0];
                }
            }

            for (int i = 0; i < size; i++) {
                this.block[4][i][0] = tempArray[i];
            }

            // Rotate the dependent side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[3][i][j];
                    this.block[3][i][j] = this.block[3][size - j - 1][i];
                    this.block[3][size - j - 1][i] = this.block[3][size - i - 1][size - j - 1];
                    this.block[3][size - i - 1][size - j - 1] = this.block[3][j][size - i - 1];
                    this.block[3][j][size - i - 1] = temp;
                }
            }

        } else {
            // this.addMove("L'");
            this.moveL(true);
            this.moveL(true);
            this.moveL(true);
        }
    }

    private void moveF(boolean clockwise) {
        if (clockwise) {
            // this.addMove("F");

            // Rotate the side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[0][i][j];
                    this.block[0][i][j] = this.block[0][size - j - 1][i];
                    this.block[0][size - j - 1][i] = this.block[0][size - i - 1][size - j - 1];
                    this.block[0][size - i - 1][size - j - 1] = this.block[0][j][size - i - 1];
                    this.block[0][j][size - i - 1] = temp;
                }
            }

            // Rotate the depended column
            String[] tempArray = new String[size];

            for (int i = 0; i < size; i++) {
                tempArray[i] = this.block[1][2 - i][0];
            }

            this.block[1][0][0] = this.block[4][2][0];
            this.block[1][1][0] = this.block[4][2][1];
            this.block[1][2][0] = this.block[4][2][2];

            this.block[4][2][0] = this.block[3][2][2];
            this.block[4][2][1] = this.block[3][1][2];
            this.block[4][2][2] = this.block[3][0][2];

            this.block[3][2][2] = this.block[5][0][2];
            this.block[3][1][2] = this.block[5][0][1];
            this.block[3][0][2] = this.block[5][0][0];

            for (int i = 0; i < size; i++) {
                this.block[5][0][i] = tempArray[i];
            }

        } else {
            // this.addMove("F'");
            this.moveF(true);
            this.moveF(true);
            this.moveF(true);
        }
    }

    private void moveS(boolean clockwise) {
        if (clockwise) {
            // this.addMove("S");

            // Rotate the column
            String[] tempArray = new String[size];

            for (int i = 0; i < size; i++) {
                tempArray[i] = this.block[1][2 - i][1];
            }

            this.block[1][0][1] = this.block[4][1][0];
            this.block[1][1][1] = this.block[4][1][1];
            this.block[1][2][1] = this.block[4][1][2];

            this.block[4][1][0] = this.block[3][2][1];
            this.block[4][1][1] = this.block[3][1][1];
            this.block[4][1][2] = this.block[3][0][1];

            this.block[3][2][1] = this.block[5][1][2];
            this.block[3][1][1] = this.block[5][1][1];
            this.block[3][0][1] = this.block[5][1][0];

            for (int i = 0; i < size; i++) {
                this.block[5][1][i] = tempArray[i];
            }

        } else {
            // this.addMove("S'");
            this.moveS(true);
            this.moveS(true);
            this.moveS(true);
        }
    }

    private void moveB(boolean clockwise) {
        if (clockwise) {
            // this.addMove("B");

            // Rotate the side
            for (int i = 0; i < size / 2; i++) {
                for (int j = i; j < size - i - 1; j++) {
                    String temp = this.block[2][i][j];
                    this.block[2][i][j] = this.block[2][size - j - 1][i];
                    this.block[2][size - j - 1][i] = this.block[2][size - i - 1][size - j - 1];
                    this.block[2][size - i - 1][size - j - 1] = this.block[2][j][size - i - 1];
                    this.block[2][j][size - i - 1] = temp;
                }
            }

            // Rotate the column
            String[] tempArray = new String[size];

            for (int i = 0; i < size; i++) {
                tempArray[i] = this.block[1][i][2];
            }

            this.block[1][0][2] = this.block[5][2][2];
            this.block[1][1][2] = this.block[5][2][1];
            this.block[1][2][2] = this.block[5][2][0];

            this.block[5][2][2] = this.block[3][2][0];
            this.block[5][2][1] = this.block[3][1][0];
            this.block[5][2][0] = this.block[3][0][0];

            this.block[3][2][0] = this.block[4][0][0];
            this.block[3][1][0] = this.block[4][0][1];
            this.block[3][0][0] = this.block[4][0][2];

            for (int i = 0; i < size; i++) {
                this.block[4][0][i] = tempArray[i];
            }

        } else {
            // this.addMove("B'");
            this.moveB(true);
            this.moveB(true);
            this.moveB(true);
        }
    }
}