
//Imports
import java.util.ArrayList;
import java.util.List;

public class Cube {
    // Default parameters
    private int size = 3;
    private static final String[][][] solvedCube = {
            { { "W", "W", "W" }, { "W", "W", "W" }, { "W", "W", "W" } },
            { { "G", "G", "G" }, { "G", "G", "G" }, { "G", "G", "G" } },
            { { "R", "R", "R" }, { "R", "R", "R" }, { "R", "R", "R" } },
            { { "B", "B", "B" }, { "B", "B", "B" }, { "B", "B", "B" } },
            { { "O", "O", "O" }, { "O", "O", "O" }, { "O", "O", "O" } },
            { { "Y", "Y", "Y" }, { "Y", "Y", "Y" }, { "Y", "Y", "Y" } }
    };

    // Cube
    private String[][][] block; // Cube
    private int sides; // Number of sides requested

    private String moves; // Moves made to solve the cube

    // Heuristic's parameters
    private Cube father;
    private int rootCost; // g(n)
    private int heuristicCost; // h(n)

    // Constructor
    public Cube(int sides, int occasion) {
        this.sides = sides;

        this.moves = "";

        // Default costs
        this.rootCost = 0;
        this.heuristicCost = 0;

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
        String randomizeMoves = ""; // Moves made to randomize the cube

        if (occasion == 0) {
            int randomizeCounter = 1 + (int) (Math.random() * (6)); // Number of randomizing moves

            int move;
            for (int i = 0; i < randomizeCounter; i++) {
                // Choose 1 of the 8 available moves
                move = (int) (Math.random() * (8));

                switch (move) {
                    case 0:
                        this.moveU(true);
                        randomizeMoves = randomizeMoves + "U ";
                        break;
                    case 1:
                        this.moveE(true);
                        randomizeMoves = randomizeMoves + "E ";
                        break;
                    case 2:
                        this.moveD(true);
                        randomizeMoves = randomizeMoves + "D ";
                        break;
                    case 3:
                        this.moveF(true);
                        randomizeMoves = randomizeMoves + "F ";
                        break;
                    case 4:
                        this.moveS(true);
                        randomizeMoves = randomizeMoves + "S ";
                        break;
                    case 5:
                        this.moveB(true);
                        randomizeMoves = randomizeMoves + "B ";
                        break;
                    case 6:
                        this.moveR(true);
                        randomizeMoves = randomizeMoves + "R ";
                        break;
                    case 7:
                        this.moveM(true);
                        randomizeMoves = randomizeMoves + "M ";
                        break;
                    case 8:
                        this.moveL(true);
                        randomizeMoves = randomizeMoves + "L ";
                        break;
                }
            }
        } else if (occasion == 1) {
            // Testing cube 1
            this.moveM(true);
            this.moveU(true);
            this.moveS(false);
            this.moveD(true);

            randomizeMoves = "M U S' D";
        } else if (occasion == 2) {
            // Testing cube 2
            this.moveU(false);
            this.moveE(true);
            this.moveS(false);

            randomizeMoves = "U' E S'";
        } else if (occasion == 3) {
            // Testing cube 3
            this.moveF(true);
            this.moveE(true);
            this.moveU(true);
            this.moveM(false);
            this.moveR(true);

            randomizeMoves = "F E U M' R";
        } else if (occasion == 4) {
            // Testing cube 4
            this.moveF(false);
            this.moveD(true);
            this.moveS(true);
            this.moveF(true);

            randomizeMoves = "F' D S F";
        } else if (occasion == 5) {
            // Testing cube 5
            this.moveU(false);
            this.moveE(true);
            this.moveS(false);
            this.moveF(false);
            this.moveD(true);

            randomizeMoves = "U' E S' F' D";
        } else {
            // Testing cube 6
            this.moveU(false);
            this.moveE(true);
            this.moveS(false);
            this.moveF(false);
            this.moveD(true);
            this.moveF(true);

            randomizeMoves = "U' E S' F' D F";
        }

        // Print the randomizing moves
        System.out.println("Moves made to randomize: " + randomizeMoves);
    }

    // Copy contrsuctor
    public Cube(Cube copy) {
        this.setSides(copy.getSides());
        this.setBlock(copy.getBlock());
        // Increase g(n)
        this.setRootCost(copy.getRootCost() + 1);
        this.setFather(copy);
    }

    // Getters
    public int getSides() {
        return this.sides;
    }

    public String[][][] getBlock() {
        return this.block;
    }

    public int getRootCost() {
        return this.rootCost;
    }

    public int getHeuristicCost() {
        return this.heuristicCost;
    }

    public Cube getFather() {
        return this.father;
    }

    public String getMoves() {
        return this.moves;
    }

    public ArrayList<Cube> getChildren() {
        ArrayList<Cube> children = new ArrayList<>(18);
        Cube child;

        // Move U
        child = new Cube(this);
        child.moveU(true);
        child.countHeuristicCost();
        children.add(child);

        // Move U'
        child = new Cube(this);
        child.moveU(false);
        child.countHeuristicCost();
        children.add(child);

        // Move E
        child = new Cube(this);
        child.moveE(true);
        child.countHeuristicCost();
        children.add(child);

        // Move E'
        child = new Cube(this);
        child.moveE(false);
        child.countHeuristicCost();
        children.add(child);

        // Move D
        child = new Cube(this);
        child.moveD(true);
        child.countHeuristicCost();
        children.add(child);

        // Move D'
        child = new Cube(this);
        child.moveD(false);
        child.countHeuristicCost();
        children.add(child);

        // Move F
        child = new Cube(this);
        child.moveF(true);
        child.countHeuristicCost();
        children.add(child);

        // Move F'
        child = new Cube(this);
        child.moveF(false);
        child.countHeuristicCost();
        children.add(child);

        // Move S
        child = new Cube(this);
        child.moveS(true);
        child.countHeuristicCost();
        children.add(child);

        // Move S'
        child = new Cube(this);
        child.moveS(false);
        child.countHeuristicCost();
        children.add(child);

        // Move B
        child = new Cube(this);
        child.moveB(true);
        child.countHeuristicCost();
        children.add(child);

        // Move B'
        child = new Cube(this);
        child.moveB(false);
        child.countHeuristicCost();
        children.add(child);

        // Move R
        child = new Cube(this);
        child.moveR(true);
        child.countHeuristicCost();
        children.add(child);

        // Move R'
        child = new Cube(this);
        child.moveR(false);
        child.countHeuristicCost();
        children.add(child);

        // Move M
        child = new Cube(this);
        child.moveM(true);
        child.countHeuristicCost();
        children.add(child);

        // Move M'
        child = new Cube(this);
        child.moveM(false);
        child.countHeuristicCost();
        children.add(child);

        // Move L
        child = new Cube(this);
        child.moveL(true);
        child.countHeuristicCost();
        children.add(child);

        // Move L'
        child = new Cube(this);
        child.moveL(false);
        child.countHeuristicCost();
        children.add(child);

        return children;
    }

    // Setters
    public void setSides(int sides) {
        this.sides = sides;
    }

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

    public void setFather(Cube father) {
        this.father = father;
    }

    public void setRootCost(int rootCost) {
        this.rootCost = rootCost;
    }

    public void addMove(String move) {
        if (this.getFather() != null) {
            this.moves = this.getFather().getMoves() + " " + move;
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

        // Compare cube's sides in final state with the requested number of sides
        if (counter >= this.sides)
            return true;
        return false;
    }

    // Moves
    private void moveU(boolean clockwise) {
        if (clockwise) {
            this.addMove("U");

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
            this.addMove("U'");

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
            this.addMove("E");

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
            this.addMove("E'");

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
            this.addMove("D");

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
            this.addMove("D'");

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
            this.addMove("R");

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
            this.addMove("R'");
            this.moveR(true);
            this.moveR(true);
            this.moveR(true);
        }
    }

    private void moveM(boolean clockwise) {
        if (clockwise) {
            this.addMove("M");

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
            this.addMove("M'");
            this.moveM(true);
            this.moveM(true);
            this.moveM(true);
        }
    }

    private void moveL(boolean clockwise) {
        if (clockwise) {
            this.addMove("L");

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
            this.addMove("L'");
            this.moveL(true);
            this.moveL(true);
            this.moveL(true);
        }
    }

    private void moveF(boolean clockwise) {
        if (clockwise) {
            this.addMove("F");

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
            this.addMove("F'");
            this.moveF(true);
            this.moveF(true);
            this.moveF(true);
        }
    }

    private void moveS(boolean clockwise) {
        if (clockwise) {
            this.addMove("S");

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
            this.addMove("S'");
            this.moveS(true);
            this.moveS(true);
            this.moveS(true);
        }
    }

    private void moveB(boolean clockwise) {
        if (clockwise) {
            this.addMove("B");

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
            this.addMove("B'");
            this.moveB(true);
            this.moveB(true);
            this.moveB(true);
        }
    }

    // Calculate h(n)
    private void countHeuristicCost() {
        this.heuristicCost = 0;

        for (int face = 0; face < 6; face++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    String currentColor = this.block[face][row][col];
                    if (currentColor != solvedCube[face][row][col]) {
                        // Find the target position
                        for (int targetFace = 0; targetFace < 6; targetFace++) {
                            for (int targetRow = 0; targetRow < 3; targetRow++) {
                                for (int targetCol = 0; targetCol < 3; targetCol++) {
                                    if (currentColor == solvedCube[targetFace][targetRow][targetCol]) {
                                        // Calculate Manhattan distance & add to the heuristic
                                        int distance = Math.abs(face - targetFace) + Math.abs(row - targetRow)
                                                + Math.abs(col - targetCol);
                                        this.heuristicCost += distance;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}